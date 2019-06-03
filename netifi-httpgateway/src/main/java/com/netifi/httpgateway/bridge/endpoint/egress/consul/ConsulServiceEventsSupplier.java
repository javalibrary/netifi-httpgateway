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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Component
public class ConsulServiceEventsSupplier implements ServiceEventsSupplier {
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
        Flux.interval(Duration.ofSeconds(10), Schedulers.single())
            .<ServiceEvent>handle(
                (l, sink) -> {
                  ConsulResponse<Map<String, List<String>>> services = catalogClient.getServices();

                  if (services != null) {
                    Map<String, List<String>> response = services.getResponse();

                    if (!response.isEmpty()) {
                      Set<String> incomingServices = response.keySet();

                      Set<String> missingInNewServices = new HashSet<>(currentServices);
                      missingInNewServices.remove(incomingServices);

                      Set<String> missingInCurrentServices = new HashSet<>(incomingServices);
                      missingInCurrentServices.remove(incomingServices);

                      for (String s : missingInCurrentServices) {
                        ConsulServiceJoinEvent joinEvent = new ConsulServiceJoinEvent(s);
                        sink.next(joinEvent);
                      }

                      for (String s : missingInNewServices) {
                        ConsulServiceLeaveEvent leaveEvent = new ConsulServiceLeaveEvent(s);
                        sink.next(leaveEvent);
                      }
                    }
                  }
                })
            .onBackpressureBuffer(256, BufferOverflowStrategy.ERROR)
            .publish()
            .refCount();
  }

  @Override
  public Flux<ServiceEvent> get() {
    return serviceEventFlux;
  }

  private abstract class ConsulServiceEvent implements ServiceEvent {
    private String serviceId;

    public ConsulServiceEvent(String serviceId) {
      this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
      return serviceId;
    }
  }

  class ConsulServiceJoinEvent extends ConsulServiceEvent
      implements ServiceEventsSupplier.ServiceJoinEvent {

    public ConsulServiceJoinEvent(String serviceId) {
      super(serviceId);
    }

    @Override
    public <E extends EgressEndpoint, F extends EgressEndpointFactory<E>>
        ConsulEgressEndpointFactorySupplier getEgressEndpointFactory() {
      return new ConsulEgressEndpointFactorySupplier(
          getServiceId(), healthClient, context.getSslContext(), registry);
    }
  }

  class ConsulServiceLeaveEvent extends ConsulServiceEvent implements ServiceLeaveEvent {
    public ConsulServiceLeaveEvent(String serviceId) {
      super(serviceId);
    }

    @Override
    public String getServiceId() {
      return super.getServiceId();
    }
  }
}
