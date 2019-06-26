package com.netifi.httpgateway.bridge.endpoint.egress.consul;

import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
import org.junit.Rule;
import org.junit.Test;

public class ConsulServiceEventsSupplierTest {

  @Rule
  public ConsulResource consulServer =
      new ConsulResource(ConsulStarterBuilder.consulStarter().withConsulVersion("1.5.1").build());

  @Test
  public void testBuildingEventsFromConsul() {
    /*
    Consul consulClient =
        Consul.builder()
            .withHostAndPort(HostAndPort.fromParts("localhost", consulServer.getHttpPort()))
            .build();
    MeterRegistry meterRegistry = Mockito.mock(MeterRegistry.class);
    SslContextFactory sslContext = Mockito.mock(SslContextFactory.class);
    VirtualTimeScheduler.getOrSet();
    ConsulServiceEventsSupplier consulServiceEventsSupplier =
        new ConsulServiceEventsSupplier(consulClient, meterRegistry, sslContext);
    DefaultConsulIngressRegister testService1 =
        new DefaultConsulIngressRegister(
            "localhost", consulServer.getHttpPort(), null, "test-1", "test", "localhost", 9090);
    StepVerifier.withVirtualTime(consulServiceEventsSupplier)
        .then(testService1::serviceRegister)
        .thenAwait(Duration.ofSeconds(15))
        .consumeNextWith(
            serviceEvent -> {
              assertEquals("test", serviceEvent.getServiceName());
              assertTrue(serviceEvent instanceof ServiceJoinEvent);
            })
        .consumeNextWith(
            serviceEvent -> {
              assertEquals("consul", serviceEvent.getServiceName());
              assertTrue(serviceEvent instanceof ServiceJoinEvent);
            })
        .then(testService1::serviceDeregister)
        .thenAwait(Duration.ofSeconds(15))
        .consumeNextWith(
            serviceEvent -> {
              assertEquals("test", serviceEvent.getServiceName());
              assertTrue(serviceEvent instanceof ServiceLeaveEvent);
            })
        .expectNoEvent(Duration.ofSeconds(15))
        .thenCancel()
        .verify(Duration.ofSeconds(20));*/
  }
}
