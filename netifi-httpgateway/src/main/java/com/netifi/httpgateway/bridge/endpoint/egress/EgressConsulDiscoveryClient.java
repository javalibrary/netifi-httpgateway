package com.netifi.httpgateway.bridge.endpoint.egress;

import com.orbitz.consul.CatalogClient;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.health.ServiceHealth;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import reactor.core.publisher.Flux;

public class EgressConsulDiscoveryClient {

  private HealthClient healthClient;
  private CatalogClient catalogClient;

  public Flux<Map<String, Set<EgressDiscoveryEndpoint>>> get() {
    return Flux.interval(Duration.ofSeconds(10)).map(l -> consulServiceSet());
  }
  private Map<String, Set<EgressDiscoveryEndpoint>> consulServiceSet() {
    ConsulResponse<Map<String, List<String>>> serviceListResp = catalogClient.getServices();
    if (serviceListResp != null) {
      Map<String, List<String>> serviceList = serviceListResp.getResponse();
      return serviceList.keySet().stream()
          .map(serviceName -> new AbstractMap.SimpleEntry<>(serviceName, getHealthyServiceEndpoints(serviceName)))
          .collect(Collectors.toConcurrentMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }
    return Collections.emptyMap();
  }
  private Set<EgressDiscoveryEndpoint> getHealthyServiceEndpoints(String serviceName) {
    ConsulResponse<List<ServiceHealth>> serviceHealthResponse = healthClient.getHealthyServiceInstances(serviceName);
    if (serviceHealthResponse != null) {
      List<ServiceHealth> serviceHealths = serviceHealthResponse.getResponse();
      return serviceHealths.stream().map(serviceHealth -> EgressDiscoveryEndpoint.newBuilder().withHost(serviceHealth.getService().getAddress()).withPort(serviceHealth.getService().getPort()).build()).collect(
          Collectors.toSet());
    }
    return Collections.emptySet();
  }
}
