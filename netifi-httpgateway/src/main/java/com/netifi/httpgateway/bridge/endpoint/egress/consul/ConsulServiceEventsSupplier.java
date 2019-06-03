package com.netifi.httpgateway.bridge.endpoint.egress.consul;

import com.netifi.httpgateway.bridge.endpoint.SslContextFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.ServiceEventsSupplier;
import com.orbitz.consul.CatalogClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
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
  private final Logger logger = LogManager.getLogger(ConsulServiceEventsSupplier.class);
  private final HealthClient healthClient;
  private final CatalogClient catalogClient;
  private final Set<String> currentServices;
  private final Flux<ServiceEvent> serviceEventFlux;
  private final MeterRegistry registry;
  private final SslContextFactory context;

  @Autowired
  public ConsulServiceEventsSupplier(
      Consul consul, MeterRegistry registry, SslContextFactory sslContext) {
    this.healthClient = consul.healthClient();
    this.context = sslContext;
    this.registry = registry;
    this.catalogClient = consul.catalogClient();
    this.currentServices = ConcurrentHashMap.newKeySet();
    this.serviceEventFlux =
        Flux.interval(
                Duration.ofSeconds(10), Schedulers.newSingle("consul-service-events-supplier"))
            .onBackpressureDrop()
            .concatMapIterable(
                l -> {
                  List<ServiceEvent> events = new ArrayList<>();
                  ConsulResponse<Map<String, List<String>>> services = catalogClient.getServices();

                  if (services != null) {
                    Map<String, List<String>> response = services.getResponse();

                    if (!response.isEmpty()) {
                      Set<String> incomingServices = response.keySet();

                      Set<String> missingInNewServices = new HashSet<>(this.currentServices);
                      missingInNewServices.removeAll(incomingServices);

                      Set<String> missingInCurrentServices = new HashSet<>(incomingServices);
                      missingInCurrentServices.removeAll(this.currentServices);

                      for (String s : missingInCurrentServices) {
                        ConsulServiceJoinEvent joinEvent = new ConsulServiceJoinEvent(s);
                        logger.debug("adding consul join event for {} ", s);
                        events.add(joinEvent);
                        this.currentServices.add(s);
                      }

                      for (String s : missingInNewServices) {
                        ConsulServiceLeaveEvent leaveEvent = new ConsulServiceLeaveEvent(s);
                        logger.debug("adding consul leave event for {} ", s);
                        events.add(leaveEvent);
                        this.currentServices.remove(s);
                      }
                    }
                  }

                  return events;
                })
            .publish()
            .refCount();
  }

  @Override
  public Flux<ServiceEvent> get() {
    return serviceEventFlux;
  }

  private abstract class ConsulServiceEvent implements ServiceEvent {
    private String serviceName;

    public ConsulServiceEvent(String serviceName) {
      this.serviceName = serviceName;
    }

    @Override
    public String getServiceName() {
      return serviceName;
    }
  }

  class ConsulServiceJoinEvent extends ConsulServiceEvent
      implements ServiceEventsSupplier.ServiceJoinEvent {

    public ConsulServiceJoinEvent(String serviceName) {
      super(serviceName);
    }

    @Override
    public <E extends EgressEndpoint, F extends EgressEndpointFactory<E>>
        ConsulEgressEndpointFactorySupplier getEgressEndpointFactory() {
      return new ConsulEgressEndpointFactorySupplier(
          getServiceName(), healthClient, context.getSslContext(), registry);
    }
  }

  class ConsulServiceLeaveEvent extends ConsulServiceEvent implements ServiceLeaveEvent {
    public ConsulServiceLeaveEvent(String serviceName) {
      super(serviceName);
    }

    @Override
    public String getServiceName() {
      return super.getServiceName();
    }
  }
}
