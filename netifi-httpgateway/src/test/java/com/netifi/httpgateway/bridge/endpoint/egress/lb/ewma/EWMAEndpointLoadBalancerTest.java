package com.netifi.httpgateway.bridge.endpoint.egress.lb.ewma;

import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactorySupplier;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.pool.EgressEndpointFactoryPool;
import com.netifi.httpgateway.bridge.endpoint.egress.pool.RandomSelectionWeightedEgressEndpointFactoryPool;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public class EWMAEndpointLoadBalancerTest {
  @Test(expected = IllegalStateException.class)
  public void testShouldThrowExceptionWhenEmpty() {
    EgressEndpointFactoryPool factoryPool = Mockito.mock(EgressEndpointFactoryPool.class);
    Mockito.when(factoryPool.onClose()).thenReturn(Mono.never());
    EWMAEndpointLoadBalancer loadBalancer =
        Mockito.spy(new EWMAEndpointLoadBalancer(factoryPool, new SimpleMeterRegistry()));
    loadBalancer.select();
  }

  @Test
  public void testSelect1Item() {
    EgressEndpointFactorySupplier egressEndpointFactorySupplier =
        Mockito.mock(EgressEndpointFactorySupplier.class);
    WeightedEgressEndpoint mockEndpoint = Mockito.mock(WeightedEgressEndpoint.class);
    Mockito.when(mockEndpoint.onClose()).thenReturn(Mono.never());
    WeightedEgressEndpointFactory factory = Mockito.mock(WeightedEgressEndpointFactory.class);
    Mockito.when(factory.get()).thenReturn(mockEndpoint);
    Set<EgressEndpointFactory> setOfFactories = Sets.newSet(factory);

    Mockito.when(egressEndpointFactorySupplier.get()).thenReturn(Flux.just(setOfFactories));

    RandomSelectionWeightedEgressEndpointFactoryPool factoryPool =
        new RandomSelectionWeightedEgressEndpointFactoryPool(
            egressEndpointFactorySupplier, "", new SimpleMeterRegistry());

    EWMAEndpointLoadBalancer loadBalancer =
        Mockito.spy(new EWMAEndpointLoadBalancer(factoryPool, new SimpleMeterRegistry()));
    EgressEndpoint endpoint = loadBalancer.select();
    Mockito.verify(loadBalancer, Mockito.times(1)).refreshEndpoints();
  }
}
