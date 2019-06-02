package com.netifi.httpgateway.bridge.endpoint.ingress;

import com.netifi.broker.BrokerClient;
import com.netifi.broker.info.*;
import com.netifi.broker.rsocket.BrokerSocket;
import com.netifi.httpgateway.bridge.endpoint.source.BridgeEndpointSourceClient;
import com.netifi.httpgateway.util.Constants;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentracing.Tracer;
import io.rsocket.Closeable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.scheduler.Scheduler;
import reactor.retry.Retry;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Manages the life cycle of groups with the tag {@link
 * com.netifi.httpgateway.util.Constants}.HTTP_GATEWAY_KEY present
 */
public class DefaultIngressGroupManager extends AtomicBoolean
    implements IngressGroupManager, Disposable, Closeable {
  private static final Logger logger = LogManager.getLogger(DefaultIngressGroupManager.class);
  private static final Tags FILTER =
      Tags.newBuilder()
          .addTags(
              Tag.newBuilder()
                  .setKey(Constants.HTTP_GATEWAY_KEY)
                  .setValue(Constants.HTTP_GATEWAY_VALUE))
          .build();

  private final MonoProcessor<Void> onClose;
  private final BrokerInfoServiceClient brokerInfoService;
  private final ConcurrentHashMap<String, IngressEndpointManager> ingressEndpointManagers;
  private final Scheduler scheduler;
  private final PortManager portManager;
  private final BrokerClient brokerClient;
  private final boolean disableSSL;
  private final SslContextFactory sslContextFactory;
  private final MeterRegistry meterRegistry;
  private final Tracer tracer;

  public DefaultIngressGroupManager(
      BrokerInfoServiceClient brokerInfoService,
      Scheduler scheduler,
      BrokerClient brokerClient,
      PortManager portManager,
      SslContextFactory sslContextFactory,
      boolean disableSSL,
      MeterRegistry meterRegistry,
      Tracer tracer) {
    this.onClose = MonoProcessor.create();
    this.brokerInfoService = brokerInfoService;
    this.ingressEndpointManagers = new ConcurrentHashMap<>();
    this.scheduler = scheduler;
    this.portManager = portManager;
    this.brokerClient = brokerClient;
    this.sslContextFactory = sslContextFactory;
    this.disableSSL = disableSSL;
    this.meterRegistry = meterRegistry;
    this.tracer = tracer;

    Disposable disposable = handleGroups();

    onClose.doFinally(signalType -> disposable.dispose()).subscribe();
  }

  private Disposable handleGroups() {
    Mono<Void> filterGroups =
        brokerInfoService
            .groupsFilteredByTags(FILTER)
            .doOnSubscribe(
                s -> logger.info("looking for groups with tag {}", Constants.HTTP_GATEWAY_KEY))
            .map(Group::getGroup)
            .collect(Collectors.toSet())
            .doOnNext(
                groups -> {
                  Set<String> keys = ingressEndpointManagers.keySet();
                  Set<String> current = new HashSet<>(keys);
                  current.remove(groups);
                  for (String s : current) {
                    logger.info("removing ingress endpoint manager for group {}", s);
                    ingressEndpointManagers.remove(s).dispose();
                  }
                })
            .then();

    return Flux.defer(
            () ->
                filterGroups.thenMany(
                    brokerInfoService.streamGroupRollupEventsFilteredByTags(FILTER)))
        .doOnNext(this::handleEvent)
        .doOnError(throwable -> logger.error("error streaming group information", throwable))
        .retryWhen(
            Retry.any()
                .withBackoffScheduler(scheduler)
                .exponentialBackoffWithJitter(Duration.ofMillis(100), Duration.ofSeconds(30)))
        .subscribe();
  }

  private void handleEvent(Event event) {
    String group = event.getDestination().getGroup();
    switch (event.getType()) {
      case JOIN:
        ingressEndpointManagers.computeIfAbsent(
            group,
            g -> {
              logger.info("adding new ingress endpoint manager for group {}", group);
              BrokerSocket brokerSocket =
                  brokerClient.groupServiceSocket(g, com.netifi.common.tags.Tags.empty());
              BridgeEndpointSourceClient client =
                  new BridgeEndpointSourceClient(brokerSocket, meterRegistry, tracer);

              return new DefaultIngressEndpointManager(
                  g, client, portManager, sslContextFactory, disableSSL);
            });
        break;
      case LEAVE:
        IngressEndpointManager removed = ingressEndpointManagers.remove(group);
        if (removed != null) {
          logger.info("removing ingress endpoint manager from group {}", group);
          removed.dispose();
        }
        break;
      default:
        throw new IllegalArgumentException("unknown group type " + event.getType());
    }
  }

  @Override
  public void dispose() {
    if (compareAndSet(false, true)) {
      onClose.onComplete();
    }
  }

  @Override
  public boolean isDisposed() {
    return get();
  }

  @Override
  public Mono<Void> onClose() {
    return onClose;
  }
}