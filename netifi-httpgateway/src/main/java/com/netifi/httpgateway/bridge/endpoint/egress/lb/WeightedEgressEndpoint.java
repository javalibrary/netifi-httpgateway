package com.netifi.httpgateway.bridge.endpoint.egress.lb;

import com.netifi.common.stats.Ewma;
import com.netifi.common.stats.Median;
import com.netifi.common.stats.Quantile;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpoint;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import io.rsocket.Payload;
import io.rsocket.rpc.exception.TimeoutException;
import io.rsocket.util.Clock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.netty.http.client.HttpClient;

public class WeightedEgressEndpoint extends AtomicBoolean implements EgressEndpoint {
  private static final double STARTUP_PENALTY = Long.MAX_VALUE >> 12;
  private static final long DEFAULT_INITIAL_INTER_ARRIVAL_TIME =
      Clock.unit().convert(1L, TimeUnit.SECONDS);
  private static final int DEFAULT_INACTIVITY_FACTOR = 500;

  private final Quantile lowerQuantile;
  private final Quantile higherQuantile;
  private final long inactivityFactor = DEFAULT_INACTIVITY_FACTOR;
  private final long tau;
  private final Ewma errorPercentage;
  private final Ewma interArrivalTime;
  private final HttpClient client;
  private final Median median;
  private final MonoProcessor<Void> onClose;
  private final String egressEndpointId;
  private final MeterRegistry registry;
  private final Timer summary;
  private long errorStamp; // last we got an error
  private long stamp; // last timestamp we sent a request
  private long stamp0; // last timestamp we sent a request or receive a response
  private long duration; // instantaneous cumulative duration
  private volatile int pending; // instantaneous rate

  public WeightedEgressEndpoint(
      String serviceName,
      String egressEndpointId,
      String host,
      int port,
      HttpClient client,
      Mono<Void> onClientConnectionClose,
      Quantile lowerQuantile,
      Quantile higherQuantile,
      MeterRegistry registry) {
    this.egressEndpointId = egressEndpointId;
    this.client = client;
    this.median = new Median();
    this.lowerQuantile = lowerQuantile;
    this.higherQuantile = higherQuantile;
    this.interArrivalTime = new Ewma(1, TimeUnit.MINUTES, DEFAULT_INITIAL_INTER_ARRIVAL_TIME);
    this.errorPercentage = new Ewma(5, TimeUnit.SECONDS, 1.0);
    this.tau = Clock.unit().convert((long) (5 / Math.log(2)), TimeUnit.SECONDS);
    this.onClose = MonoProcessor.create();
    this.registry = registry;

    Tags tags =
        Tags.of(
            "serviceName",
            serviceName,
            "egressEndpointId",
            egressEndpointId,
            "host",
            host,
            "port",
            String.valueOf(port),
            "type",
            "WeightedEgressEndpoint");
    registry.gauge("pendingRequests", tags, this, value -> pending);
    registry.gauge("errorPercentage", tags, errorPercentage, Ewma::value);

    summary = Timer.builder("requests").tags(tags).register(registry);

    onClientConnectionClose.doFinally(s -> dispose()).subscribe();
    onClose.doFinally(signalType -> dispose()).subscribe();
  }

  @Override
  public String getEgressEndpointId() {
    return egressEndpointId;
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

  public Mono<Payload> request(Function<HttpClient, Mono<Payload>> handle) {
    long start = start();
    try {
      return handle
          .apply(client)
          .doOnSuccessOrError(
              (p, t) -> {
                final long now = stop(start);
                final long roundTripTime = now - start;
                summary.record(roundTripTime, TimeUnit.MILLISECONDS);
                if (t == null || t instanceof TimeoutException) {
                  record(roundTripTime);
                }

                if (t != null) {
                  recordError(0.0);
                } else {
                  recordError(1.0);
                }
              });
    } catch (Throwable t) {
      stop(start);
      recordError(0.0);
      return Mono.error(t);
    }
  }

  public synchronized double predictedLatency() {
    long now = Clock.now();
    long elapsed = Math.max(now - stamp, 1L);

    double weight;
    double prediction = median.estimation();

    if (prediction == 0.0) {
      if (pending == 0) {
        weight = 0.0; // first request
      } else {
        // subsequent requests while we don't have any history
        weight = STARTUP_PENALTY + pending;
      }
    } else if (pending == 0 && elapsed > inactivityFactor * interArrivalTime.value()) {
      // if we did't see any data for a while, we decay the prediction by inserting
      // artificial 0.0 into the median
      median.insert(0.0);
      weight = median.estimation();
    } else {
      double predicted = prediction * pending;
      double instant = instantaneous(now);

      if (predicted < instant) { // NB: (0.0 < 0.0) == false
        weight = instant / pending; // NB: pending never equal 0 here
      } else {
        // we are under the predictions
        weight = prediction;
      }
    }

    return weight;
  }

  private synchronized long instantaneous(long now) {
    return duration + (now - stamp0) * pending;
  }

  private synchronized long start() {
    long now = Clock.now();
    interArrivalTime.insert(now - stamp);
    duration += Math.max(0, now - stamp0) * pending;
    pending += 1;
    stamp = now;
    stamp0 = now;
    return now;
  }

  private synchronized long stop(long timestamp) {
    long now = Clock.now();
    duration += Math.max(0, now - stamp0) * pending - (now - timestamp);
    pending -= 1;
    stamp0 = now;
    return now;
  }

  private synchronized void record(double roundTripTime) {
    median.insert(roundTripTime);
    lowerQuantile.insert(roundTripTime);
    higherQuantile.insert(roundTripTime);
  }

  private synchronized void recordError(double value) {
    errorPercentage.insert(value);
    errorStamp = Clock.now();
  }

  public int pending() {
    return pending;
  }

  @Override
  public String toString() {
    return "WeightedEgressEndpoint{"
        + "lowerQuantile="
        + lowerQuantile
        + ", higherQuantile="
        + higherQuantile
        + ", inactivityFactor="
        + inactivityFactor
        + ", tau="
        + tau
        + ", errorPercentage="
        + errorPercentage
        + ", errorStamp="
        + errorStamp
        + ", stamp="
        + stamp
        + ", stamp0="
        + stamp0
        + ", duration="
        + duration
        + ", median="
        + median
        + ", interArrivalTime="
        + interArrivalTime
        + ", client="
        + client
        + ", pending="
        + pending
        + '}';
  }
}
