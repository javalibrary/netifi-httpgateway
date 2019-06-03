package com.netifi.httpgateway.bridge.endpoint.egress.consul;

import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
import org.junit.Rule;
import org.junit.Test;

public class ConsulEgressEndpointFactorySupplierTest {

  @Rule
  public ConsulResource consulServer =
      new ConsulResource(ConsulStarterBuilder.consulStarter().withConsulVersion("1.5.1").build());

  @Test
  public void get() {
    /*
    Consul consulClient =
        Consul.builder()
            .withHostAndPort(HostAndPort.fromParts("localhost", consulServer.getHttpPort()))
            .build();
    MeterRegistry meterRegistry = Mockito.mock(MeterRegistry.class);
    SslContext sslContext = Mockito.mock(SslContext.class);
    VirtualTimeScheduler.getOrSet();

    DefaultConsulIngressRegister testService1 =
        new DefaultConsulIngressRegister(
            "localhost", consulServer.getHttpPort(), null, "test-1", "test", "localhost", 9090);
    ConsulEgressEndpointFactorySupplier egressEndpointFactorySupplier =
        new ConsulEgressEndpointFactorySupplier(
            "test", consulClient.healthClient(), sslContext, meterRegistry);

    StepVerifier.withVirtualTime(egressEndpointFactorySupplier)
        .then(testService1::serviceRegister)
        .thenAwait(Duration.ofSeconds(15))
        .consumeNextWith(
            weightedEgressEndpointFactories -> {
              assertEquals(1, weightedEgressEndpointFactories.size());
              assertTrue(
                  weightedEgressEndpointFactories
                      .stream()
                      .allMatch(w -> w.getEgressEndpointId().equals("test-1")));
            })
        .expectNoEvent(Duration.ofSeconds(15))
        .then(testService1::failService)
        .thenAwait(Duration.ofSeconds(15))
        .consumeNextWith(
            weightedEgressEndpointFactories -> {
              assertTrue(weightedEgressEndpointFactories.isEmpty());
            })
        .then(testService1::passService)
        .thenAwait(Duration.ofSeconds(15))
        .consumeNextWith(
            weightedEgressEndpointFactories -> {
              assertEquals(1, weightedEgressEndpointFactories.size());
              assertTrue(
                  weightedEgressEndpointFactories
                      .stream()
                      .allMatch(w -> w.getEgressEndpointId().equals("test-1")));
            })
        .then(testService1::serviceDeregister)
        .thenAwait(Duration.ofSeconds(15))
        .consumeNextWith(
            weightedEgressEndpointFactories -> {
              assertTrue(weightedEgressEndpointFactories.isEmpty());
            })
        .expectNoEvent(Duration.ofSeconds(60))
        .thenCancel()
        .verify(Duration.ofSeconds(20));

     */
  }
}
