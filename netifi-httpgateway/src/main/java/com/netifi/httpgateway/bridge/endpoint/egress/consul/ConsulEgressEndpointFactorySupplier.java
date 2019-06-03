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
  private String serviceName;
  private Quantile lowerQuantile;
  private Quantile higherQuantile;
  private MeterRegistry registry;
  private Flux<Set<WeightedEgressEndpointFactory>> factoryStream;

  public ConsulEgressEndpointFactorySupplier(
      String serviceName, HealthClient healthClient, SslContext context, MeterRegistry registry) {
    this.serviceName = serviceName;
    this.healthClient = healthClient;
    this.factories = new ConcurrentHashMap<>();
    this.factoryStream =
        Flux.interval(Duration.ofSeconds(10), Schedulers.single())
            .<Set<WeightedEgressEndpointFactory>>handle(
                (l, sink) -> {
                  ConsulResponse<List<ServiceHealth>> healthyServiceInstances =
                      healthClient.getHealthyServiceInstances(serviceName);
                  boolean changeOccurred = false;
                  if (healthyServiceInstances != null) {
                    List<ServiceHealth> response = healthyServiceInstances.getResponse();

                    if (!response.isEmpty() || !factories.isEmpty()) {
                      Set<String> incomingNodes = new HashSet<>();
                      for (ServiceHealth health : response) {
                        Service service = health.getService();
                        String id = service.getId();
                        incomingNodes.add(id);
                        if (!factories.containsKey(id)) {
                          String address = service.getAddress();
                          if (address == null || address.isEmpty()) {
                            address = health.getNode().getAddress();
                          }
                          WeightedEgressEndpointFactory factory =
                              new WeightedEgressEndpointFactory(
                                  serviceName,
                                  id,
                                  address,
                                  service.getPort(),
                                  context,
                                  true,
                                  lowerQuantile,
                                  higherQuantile,
                                  registry);
                          factories.put(id, factory);
                          changeOccurred = true;
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
                        changeOccurred = true;
                      }

                      if (changeOccurred) {
                        sink.next(new HashSet<>(factories.values()));
                      }
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
