package com.netifi.httpgateway.bridge.endpoint.ingress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.health.HealthCheck;
import com.orbitz.consul.model.health.Service;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
import java.util.List;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;

public class DefaultConsulIngressRegisterTest {

  @Rule
  public ConsulResource consulServer =
      new ConsulResource(ConsulStarterBuilder.consulStarter().withConsulVersion("1.5.1").build());

  @Test
  public void testServiceRegistration() {
    DefaultConsulIngressRegister defaultConsulIngressRegister =
        new DefaultConsulIngressRegister(
            "localhost",
            consulServer.getHttpPort(),
            null,
            "service-1",
            "service",
            "localhost",
            9090);
    defaultConsulIngressRegister.serviceRegister();

    Consul consulClient =
        Consul.builder()
            .withHostAndPort(HostAndPort.fromParts("localhost", consulServer.getHttpPort()))
            .build();

    Map<String, Service> services = consulClient.agentClient().getServices();
    assertEquals(1, services.size());
    assertEquals("service-1", services.get("service-1").getId());
    assertEquals("service", services.get("service-1").getService());
    assertEquals("localhost", services.get("service-1").getAddress());
    assertEquals(9090, services.get("service-1").getPort());

    ConsulResponse<Map<String, List<String>>> catalogServiceListRespoonse =
        consulClient.catalogClient().getServices();
    Map<String, List<String>> catalog = catalogServiceListRespoonse.getResponse();
    assertNotNull(catalog);
    assertEquals(2, catalog.size());
    assertEquals(1, catalog.keySet().stream().filter(s -> s.equals("consul")).count());
    assertEquals(1, catalog.keySet().stream().filter(s -> s.equals("service")).count());

    ConsulResponse<List<HealthCheck>> serviceHealthChecksResponse =
        consulClient.healthClient().getServiceChecks("service");
    List<HealthCheck> healthCheckList = serviceHealthChecksResponse.getResponse();
    assertNotNull(healthCheckList);
    assertEquals(1, healthCheckList.size());
    assertEquals("passing", healthCheckList.get(0).getStatus());

    defaultConsulIngressRegister.failService();

    serviceHealthChecksResponse = consulClient.healthClient().getServiceChecks("service");
    healthCheckList = serviceHealthChecksResponse.getResponse();
    assertNotNull(healthCheckList);
    assertEquals(1, healthCheckList.size());
    assertEquals("critical", healthCheckList.get(0).getStatus());

    defaultConsulIngressRegister.passService();

    serviceHealthChecksResponse = consulClient.healthClient().getServiceChecks("service");
    healthCheckList = serviceHealthChecksResponse.getResponse();
    assertNotNull(healthCheckList);
    assertEquals(1, healthCheckList.size());
    assertEquals("passing", healthCheckList.get(0).getStatus());

    defaultConsulIngressRegister.serviceDeregister();

    catalogServiceListRespoonse = consulClient.catalogClient().getServices();
    catalog = catalogServiceListRespoonse.getResponse();
    assertNotNull(catalog);
    assertEquals(1, catalog.size());
    assertEquals(1, catalog.keySet().stream().filter(s -> s.equals("consul")).count());
  }
}
