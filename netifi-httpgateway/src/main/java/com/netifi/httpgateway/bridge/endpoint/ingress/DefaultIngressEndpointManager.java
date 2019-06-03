package com.netifi.httpgateway.bridge.endpoint.ingress;

import static com.netifi.httpgateway.util.Constants.HTTP_BRIDGE_NAMED_SOCKET_NAME;

import com.google.protobuf.Empty;
import com.netifi.broker.BrokerClient;
import com.netifi.broker.rsocket.BrokerSocket;
import com.netifi.httpgateway.bridge.endpoint.SslContextFactory;
import com.netifi.httpgateway.bridge.endpoint.source.BridgeEndpointSourceClient;
import com.netifi.httpgateway.bridge.endpoint.source.Event;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.rsocket.RSocket;
import io.rsocket.exceptions.RejectedSetupException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.retry.Retry;

public class DefaultIngressEndpointManager extends AtomicBoolean implements IngressEndpointManager {

  private final Logger logger = LogManager.getLogger(IngressEndpoint.class);

  private final ConcurrentHashMap<String, IngressEndpoint> ingressEndpoints;

  private final SslContextFactory sslContextFactory;

  private final boolean disableSsl;

  private final PortManager portManager;

  private final MonoProcessor<Void> onClose;

  private final String group;

  private final MeterRegistry registry;

  private final BrokerClient brokerClient;

  private final Counter joinEvents;

  private final Counter leaveEvents;

  public DefaultIngressEndpointManager(
      String group,
      BrokerClient brokerClient,
      BridgeEndpointSourceClient client,
      PortManager portManager,
      SslContextFactory sslContextFactory,
      boolean disableSSL,
      MeterRegistry registry) {
    this.registry = registry;
    this.brokerClient = brokerClient;
    this.group = group;
    this.ingressEndpoints = new ConcurrentHashMap<>();
    this.sslContextFactory = sslContextFactory;
    this.disableSsl = disableSSL;
    this.portManager = portManager;
    this.onClose = MonoProcessor.create();

    Disposable disposable =
        client
            .streamEndpointEvents(Empty.getDefaultInstance())
            .doOnNext(this::handleEvent)
            .doOnError(
                throwable ->
                    logger.error("error streaming endpoints for group " + group, throwable))
            .retryWhen(
                Retry.allBut(RejectedSetupException.class)
                    .exponentialBackoffWithJitter(Duration.ofSeconds(1), Duration.ofSeconds(30)))
            .subscribe();

    onClose
        .doFinally(
            signalType -> {
              disposable.dispose();
              ingressEndpoints.forEach((s, ingressEndpoint) -> ingressEndpoint.dispose());
            })
        .subscribe();

    Tags tags = Tags.of("type", "DefaultIngressEndpoint");

    this.joinEvents = registry.counter("endpointJoinEvents", tags);
    this.leaveEvents = registry.counter("endpointLeaveEvents", tags);
  }

  private void handleEvent(Event event) {
    logger.info("group {} sent event {}", group, event);
    if (event.hasJoinEvent()) {
      joinEvents.increment();
      BrokerSocket target = brokerClient.groupNamedRSocket(group, HTTP_BRIDGE_NAMED_SOCKET_NAME);
      register(event.getJoinEvent().getServiceName(), target);
    } else if (event.hasLeaveEvent()) {
      leaveEvents.increment();
      unregister(event.getLeaveEvent().getServiceName());
    }
  }

  public void register(String serviceName, RSocket target) {
    ingressEndpoints.compute(
        serviceName,
        (s, ingressEndpoint) -> {
          if (ingressEndpoint != null) {
            logger.warn("can't add service named {}, already present", serviceName);
            return ingressEndpoint;
          } else {
            int port = portManager.reservePort(serviceName);

            IngressEndpoint endpoint =
                new DefaultIngressEndpoint(
                    sslContextFactory, serviceName, disableSsl, port, target, registry);

            endpoint.onClose().doFinally(signalType -> portManager.releasePort(port)).subscribe();

            logger.info(
                "registering endpoint for service named {} on port {} - ssl disabled = {}",
                serviceName,
                port,
                disableSsl);

            return endpoint;
          }
        });
  }

  public void unregister(String serviceName) {
    IngressEndpoint ingressEndpoint = ingressEndpoints.remove(serviceName);
    if (ingressEndpoint != null) {
      logger.info("unregistering ingress endpoint for service name {}", serviceName);
      ingressEndpoint.dispose();
    }
  }

  @Override
  public Mono<Void> onClose() {
    return onClose;
  }

  @Override
  public void dispose() {
    set(true);
    onClose.onComplete();
  }

  @Override
  public boolean isDisposed() {
    return get();
  }
}
