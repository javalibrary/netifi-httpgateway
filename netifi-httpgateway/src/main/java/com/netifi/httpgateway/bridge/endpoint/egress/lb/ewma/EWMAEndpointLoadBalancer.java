package com.netifi.httpgateway.bridge.endpoint.egress.lb.ewma;

import com.netifi.common.stats.Ewma;
import com.netifi.common.stats.FrugalQuantile;
import com.netifi.common.stats.Quantile;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.EgressEndpointLoadBalancer;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.pool.EgressEndpointFactoryPool;
import io.rsocket.util.Clock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class EWMAEndpointLoadBalancer extends AtomicBoolean implements EgressEndpointLoadBalancer {
  private static final Logger logger = LogManager.getLogger(EWMAEndpointLoadBalancer.class);
  private static final double DEFAULT_EXP_FACTOR = 4.0;
  private static final double DEFAULT_LOWER_QUANTILE = 0.5;
  private static final double DEFAULT_HIGHER_QUANTILE = 0.8;
  private static final int minAperture = 3;
  private static final int maxAperture = 100;
  private static final int EFFORT = 5;
  private static final long APERTURE_REFRESH_PERIOD = Clock.unit().convert(15, TimeUnit.SECONDS);
  private final double minPendings = 1.0;
  private final double maxPendings = 2.0;
  private final double expFactor = DEFAULT_EXP_FACTOR;
  private final Quantile lowerQuantile = new FrugalQuantile(DEFAULT_LOWER_QUANTILE);
  private final Quantile higherQuantile = new FrugalQuantile(DEFAULT_HIGHER_QUANTILE);
  private final MonoProcessor<Void> onClose;
  private final List<WeightedEgressEndpoint> activeEndpoints;
  private final EgressEndpointFactoryPool<WeightedEgressEndpoint, WeightedEgressEndpointFactory>
      egressEndpointFactoryPool;
  private final Ewma pendings;
  private final long maxRefreshPeriod = TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES);
  private int pendingSockets;
  private long lastApertureRefresh;
  private long refreshPeriod;
  private volatile int targetAperture;
  private volatile long lastRefresh;

  @SuppressWarnings("unchecked")
  public EWMAEndpointLoadBalancer(
      EgressEndpointFactoryPool<WeightedEgressEndpoint, WeightedEgressEndpointFactory>
          egressEndpointFactoryPool) {
    this.activeEndpoints = new ArrayList<>();
    this.onClose = MonoProcessor.create();
    this.egressEndpointFactoryPool = egressEndpointFactoryPool;
    this.pendings = new Ewma(15, TimeUnit.SECONDS, (minPendings + maxPendings) / 2.0);
    this.refreshPeriod = Clock.unit().convert(15L, TimeUnit.SECONDS);
    this.lastRefresh = Clock.now();

    egressEndpointFactoryPool.onClose().doFinally(signalType -> dispose()).subscribe();
  }

  @Override
  public boolean isDisposed() {
    return get();
  }

  @Override
  public Mono<Void> onClose() {
    return onClose;
  }

  @Override
  public void dispose() {
    if (compareAndSet(false, true)) {
      onClose.onComplete();
    }
  }

  @Override
  public EgressEndpoint select() {
    List<WeightedEgressEndpoint> _e = refreshEndpoints();
    WeightedEgressEndpoint e1 = null;
    WeightedEgressEndpoint e2 = null;
    int size = _e.size();
    switch (size) {
      case 0:
        throw new IllegalStateException("the load balancer is empty");
      case 1:
        return _e.get(0);
      case 2:
        e1 = _e.get(0);
        e2 = _e.get(1);
        break;
      default:
        for (int i = 0; i < EFFORT; i++) {
          int i1 = ThreadLocalRandom.current().nextInt(size);
          int i2 = ThreadLocalRandom.current().nextInt(size - 1);

          if (i2 >= i1) {
            i2++;
          }

          e1 = _e.get(i1);
          e2 = _e.get(i2);
        }
    }

    double w1 = algorithmicWeight(e1);
    double w2 = algorithmicWeight(e1);

    if (w1 < w2) {
      return e2;
    } else {
      return e1;
    }
  }

  private synchronized List<WeightedEgressEndpoint> refreshEndpoints() {
    refreshAperture();
    int n = activeEndpoints.size();
    if (n < targetAperture && !egressEndpointFactoryPool.isEmpty()) {
      logger.debug(
          "aperture {} is below target {}, adding {} endpoints",
          n,
          targetAperture,
          targetAperture - n);
      addEndpoints(targetAperture - n);
    } else if (targetAperture < n) {
      logger.debug("aperture {} is above target {}, quicking 1 endpoints", n, targetAperture);
      removeSlowestEndpoint();
    }

    long now = Clock.now();
    if (now - lastRefresh >= refreshPeriod) {
      long prev = refreshPeriod;
      refreshPeriod = (long) Math.min(refreshPeriod * 1.5, maxRefreshPeriod);
      logger.debug("Bumping refresh period, {}->{}", prev / 1000, refreshPeriod / 1000);
      lastRefresh = now;
      addEndpoints(1);
    }

    return activeEndpoints;
  }

  private synchronized void refreshAperture() {
    int n = activeEndpoints.size();
    if (n == 0) {
      return;
    }

    double p = 0.0;
    for (WeightedEgressEndpoint wrs : activeEndpoints) {
      p += wrs.pending();
    }
    p /= n + pendingSockets;
    pendings.insert(p);
    double avgPending = pendings.value();

    long now = Clock.now();
    boolean underRateLimit = now - lastApertureRefresh > APERTURE_REFRESH_PERIOD;
    if (avgPending < 1.0 && underRateLimit) {
      updateAperture(targetAperture - 1, now);
    } else if (2.0 < avgPending && underRateLimit) {
      updateAperture(targetAperture + 1, now);
    }
  }

  private void updateAperture(int newValue, long now) {
    int previous = targetAperture;
    targetAperture = newValue;
    targetAperture = Math.max(minAperture, targetAperture);
    int maxAperture =
        Math.min(this.maxAperture, activeEndpoints.size() + egressEndpointFactoryPool.size());
    targetAperture = Math.min(maxAperture, targetAperture);
    lastApertureRefresh = now;
    pendings.reset((minPendings + maxPendings) / 2);

    if (targetAperture != previous) {
      logger.debug(
          "Current pending={}, new target={}, previous target={}",
          pendings.value(),
          targetAperture,
          previous);
    }
  }

  private double algorithmicWeight(WeightedEgressEndpoint endpoint) {
    int pendings = endpoint.pending();
    double latency = endpoint.predictedLatency();

    double low = lowerQuantile.estimation();
    double high =
        Math.max(
            higherQuantile.estimation(),
            low * 1.001); // ensure higherQuantile > lowerQuantile + .1%
    double bandWidth = Math.max(high - low, 1);

    if (latency < low) {
      latency /= calculateFactor(low, latency, bandWidth);
    } else if (latency > high) {
      latency *= calculateFactor(latency, high, bandWidth);
    }

    return latency * (pendings + 1);
  }

  private double calculateFactor(double u, double l, double bandWidth) {
    double alpha = (u - l) / bandWidth;
    return Math.pow(1 + alpha, expFactor);
  }

  private synchronized void removeSlowestEndpoint() {
    WeightedEgressEndpoint slowest = null;
    synchronized (this) {
      if (activeEndpoints.size() <= 1) {
        return;
      }

      double currentLowestLatency = Double.MAX_VALUE;
      for (WeightedEgressEndpoint socket : activeEndpoints) {
        double latency = socket.predictedLatency();
        if (latency <= currentLowestLatency) {
          currentLowestLatency = latency;
          slowest = socket;
        }
      }

      if (slowest != null) {
        activeEndpoints.remove(slowest);
      }
    }

    if (slowest != null) {
      slowest.dispose();
    }
  }

  private synchronized void addEndpoints(int numberOfNewEndpoints) {
    int n = numberOfNewEndpoints;
    int poolSize = egressEndpointFactoryPool.size();
    if (n > poolSize) {
      n = poolSize;
      logger.debug(
          "addEndpoints({}) restricted by the number of factories, i.e. addEndpoints({})",
          numberOfNewEndpoints,
          n);
    }

    for (int i = 0; i < n; i++) {
      Optional<WeightedEgressEndpointFactory> optional =
          egressEndpointFactoryPool.lease();

      if (optional.isPresent()) {
        EgressEndpointFactory<WeightedEgressEndpoint> factory = optional.get();
        WeightedEgressEndpoint endpoint = factory.get();
        activeEndpoints.add(endpoint);
        endpoint
            .onClose()
            .doFinally(
                signalType -> {
                  synchronized (EWMAEndpointLoadBalancer.this) {
                    activeEndpoints.remove(endpoint);
                  }
                })
            .subscribe();
      } else {
        break;
      }
    }
  }
}
