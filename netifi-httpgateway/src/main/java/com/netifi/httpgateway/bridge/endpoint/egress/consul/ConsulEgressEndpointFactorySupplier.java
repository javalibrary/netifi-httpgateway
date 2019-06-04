package com.netifi.httpgateway.bridge.endpoint.egress.consul;

import com.netifi.common.stats.FrugalQuantile;
import com.netifi.common.stats.Quantile;
import com.netifi.httpgateway.ConsulClientProvider;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactorySupplier;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpointFactory;
import com.netifi.httpgateway.util.Constants;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ConsulEgressEndpointFactorySupplier
    implements EgressEndpointFactorySupplier<
        WeightedEgressEndpoint, WeightedEgressEndpointFactory> {
  private static final Logger logger =
      LogManager.getLogger(ConsulEgressEndpointFactorySupplier.class);
  private final Quantile lowerQuantile = new FrugalQuantile(0.5D);
  private final Quantile higherQuantile = new FrugalQuantile(0.8D);
  private final ConcurrentHashMap<String, WeightedEgressEndpointFactory> factories;
  private final Flux<Set<WeightedEgressEndpointFactory>> factoryStream;

  public ConsulEgressEndpointFactorySupplier(
      String serviceName,
      ConsulClientProvider.ConsulSupplier consulSupplier,
      SslContext context,
      MeterRegistry registry) {
    this.factories = new ConcurrentHashMap<>();
    this.factoryStream =
        Flux.interval(Duration.ofSeconds(2), Schedulers.newSingle("consul-egress-endpoint-factory"))
            .<Set<WeightedEgressEndpointFactory>>handle(
                (l, sink) -> {
                  try {
                    HealthClient healthClient = consulSupplier.get().healthClient();
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
                          if (service.getTags().contains(Constants.HTTP_GATEWAY_KEY)) {
                            logger.debug(
                                "service {} is a netifi http gateway service instance - skipping",
                                id);
                            continue;
                          }
                          logger.debug("adding service {} with tags {}", id, service.getTags());
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
                  } catch (Throwable t) {
                    logger.error("error occurred create endpoint factory", t);
                    sink.error(t);
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
