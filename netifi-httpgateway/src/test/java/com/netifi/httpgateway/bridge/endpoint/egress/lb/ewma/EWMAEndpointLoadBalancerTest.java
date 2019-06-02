package com.netifi.httpgateway.bridge.endpoint.egress.lb.ewma;

import com.netifi.httpgateway.bridge.endpoint.egress.pool.EgressEndpointFactoryPool;
import org.junit.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

public class EWMAEndpointLoadBalancerTest {
  @Test(expected = IllegalStateException.class)
  public void testShouldThrowExceptionWhenEmpty() {
    EgressEndpointFactoryPool factoryPool = Mockito.mock(EgressEndpointFactoryPool.class);
    Mockito.when(factoryPool.onClose()).thenReturn(Mono.never());
    EWMAEndpointLoadBalancer loadBalancer = new EWMAEndpointLoadBalancer(factoryPool);
    loadBalancer.select();
  }
}