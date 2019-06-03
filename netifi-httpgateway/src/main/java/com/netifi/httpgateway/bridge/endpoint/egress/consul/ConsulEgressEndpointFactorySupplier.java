package com.netifi.httpgateway.bridge.endpoint.egress.consul;

import com.netifi.common.stats.Quantile;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactorySupplier;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpointFactory;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.health.Service;
import com.orbitz.consul.model.health.ServiceHealth;
import io.micrometer.core.instrument.MeterRegistry;
import io.netty.handler.ssl.SslContext;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ConsulEgressEndpointFactorySupplier
    implements EgressEndpointFactorySupplier<
        WeightedEgressEndpoint, WeightedEgressEndpointFactory> {
  private HealthClient healthClient;
  private ConcurrentHashMap<String, WeightedEgressEndpointFactory> factories;
  private String serviceId;
  private Quantile lowerQuantile;
  private Quantile higherQuantile;
  private MeterRegistry registry;
  private Flux<Set<WeightedEgressEndpointFactory>> factoryStream;

  public ConsulEgressEndpointFactorySupplier(
      String serviceId, HealthClient healthClient, SslContext context, MeterRegistry registry) {
    this.serviceId = serviceId;
    this.healthClient = healthClient;
    this.factories = new ConcurrentHashMap<>();
    this.factoryStream =
        Flux.interval(Duration.ofSeconds(10), Schedulers.single())
            .<Set<WeightedEgressEndpointFactory>>handle(
                (l, sink) -> {
                  ConsulResponse<List<ServiceHealth>> healthyServiceInstances =
                      healthClient.getHealthyServiceInstances(serviceId);

                  if (healthyServiceInstances != null) {
                    List<ServiceHealth> response = healthyServiceInstances.getResponse();

                    if (!response.isEmpty()) {
                      HashMap<String, WeightedEgressEndpointFactory> newFactories = new HashMap<>();
                      Set<String> incomingNodes = new HashSet<>();
                      for (ServiceHealth health : response) {
                        Service service = health.getService();
                        String id = service.getId();
                        incomingNodes.add(id);
                        if (!factories.contains(id)) {
                          WeightedEgressEndpointFactory factory =
                              new WeightedEgressEndpointFactory(
                                  serviceId,
                                  id,
                                  service.getAddress(),
                                  service.getPort(),
                                  context,
                                  true,
                                  lowerQuantile,
                                  higherQuantile,
                                  registry);
                          newFactories.put(id, factory);
                        }
                      }

                      Set<String> factoriesToRemove = new HashSet<>();
                      for (String k : factories.keySet()) {
                        if (!incomingNodes.contains(k)) {
                          factoriesToRemove.add(k);
                        }
                      }

                      for (String k : factoriesToRemove) {
                        factories.remove(k).dispose();
                      }

                      sink.next(new HashSet<>(factories.values()));
                    }
                  }
                })
            .publish()
            .refCount();
  }

  @Override
  public Flux<Set<WeightedEgressEndpointFactory>> get() {
    return factoryStream;
  }
}
