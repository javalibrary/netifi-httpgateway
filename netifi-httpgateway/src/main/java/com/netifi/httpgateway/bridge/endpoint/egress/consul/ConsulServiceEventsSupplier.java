package com.netifi.httpgateway.bridge.endpoint.egress.consul;

import com.netifi.httpgateway.ConsulClientProvider;
import com.netifi.httpgateway.bridge.endpoint.SslContextFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.ServiceEventsSupplier;
import com.netifi.httpgateway.util.Constants;
import com.orbitz.consul.CatalogClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.ConsulResponse;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Component
public class ConsulServiceEventsSupplier implements ServiceEventsSupplier {
  private static final Logger logger = LogManager.getLogger(ConsulServiceEventsSupplier.class);
  private final Set<String> currentServices;
  private final Flux<ServiceEvent> serviceEventFlux;
  private final MeterRegistry registry;
  private final SslContextFactory context;

  @Autowired
  public ConsulServiceEventsSupplier(
      ConsulClientProvider.ConsulSupplier consulSupplier,
      MeterRegistry registry,
      SslContextFactory sslContext) {
    this.context = sslContext;
    this.registry = registry;
    this.currentServices = ConcurrentHashMap.newKeySet();

    this.serviceEventFlux =
        Flux.defer(
                () -> {
                  final Consul consul = consulSupplier.get();
                  logger.info("streaming consul service events");
                  final CatalogClient catalogClient = consul.catalogClient();

                  return Flux.interval(
                          Duration.ofSeconds(2),
                          Schedulers.newSingle("consul-service-event-supplier"))
                      .onBackpressureDrop()
                      .concatMapIterable(
                          l -> {
                            List<ServiceEvent> events = new ArrayList<>();
                            ConsulResponse<Map<String, List<String>>> services =
                                catalogClient.getServices();

                            if (services != null) {
                              Map<String, List<String>> response = services.getResponse();

                              if (!response.isEmpty()) {
                                Set<String> incomingServices = response.keySet();

                                Set<String> missingInNewServices =
                                    new HashSet<>(this.currentServices);
                                missingInNewServices.removeAll(incomingServices);

                                Set<String> missingInCurrentServices =
                                    new HashSet<>(incomingServices);
                                missingInCurrentServices.removeAll(this.currentServices);

                                for (String s : missingInCurrentServices) {
                                  List<String> tags = response.get(s);
                                  if (tags.contains(Constants.HTTP_GATEWAY_KEY)) {
                                    logger.debug(
                                        "service named {} contains the tag {} - skipping",
                                        s,
                                        Constants.HTTP_GATEWAY_KEY);
                                    continue;
                                  }

                                  if (s.equals("consul")) {
                                    // logger.debug("found service named consul - skipping");
                                    continue;
                                  }

                                  ConsulServiceJoinEvent joinEvent =
                                      new ConsulServiceJoinEvent(s, consulSupplier);
                                  events.add(joinEvent);
                                  this.currentServices.add(s);
                                }

                                for (String s : missingInNewServices) {
                                  ConsulServiceLeaveEvent leaveEvent =
                                      new ConsulServiceLeaveEvent(s);
                                  events.add(leaveEvent);
                                  this.currentServices.remove(s);
                                }
                              }
                            }

                            return events;
                          });
                })
            .doOnError(
                throwable -> logger.error("error streaming consul service events", throwable))
            .publish()
            .refCount();
  }

  @Override
  public Flux<ServiceEvent> get() {
    return serviceEventFlux;
  }

  private abstract class ConsulServiceEvent implements ServiceEvent {
    private String serviceName;

    ConsulServiceEvent(String serviceName) {
      this.serviceName = serviceName;
    }

    @Override
    public String getServiceName() {
      return serviceName;
    }
  }

  class ConsulServiceJoinEvent extends ConsulServiceEvent
      implements ServiceEventsSupplier.ServiceJoinEvent {

    private final ConsulClientProvider.ConsulSupplier consulSupplier;

    ConsulServiceJoinEvent(String serviceName, ConsulClientProvider.ConsulSupplier consulSupplier) {
      super(serviceName);
      this.consulSupplier = consulSupplier;
    }

    @Override
    public <E extends EgressEndpoint, F extends EgressEndpointFactory<E>>
        ConsulEgressEndpointFactorySupplier getEgressEndpointFactory() {
      return new ConsulEgressEndpointFactorySupplier(
          getServiceName(), consulSupplier, context.getSslContext(), registry);
    }
  }

  class ConsulServiceLeaveEvent extends ConsulServiceEvent implements ServiceLeaveEvent {
    ConsulServiceLeaveEvent(String serviceName) {
      super(serviceName);
    }

    @Override
    public String getServiceName() {
      return super.getServiceName();
    }
  }
}
