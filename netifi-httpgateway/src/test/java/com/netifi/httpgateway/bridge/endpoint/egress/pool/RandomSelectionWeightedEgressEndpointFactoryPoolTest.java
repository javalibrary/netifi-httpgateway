package com.netifi.httpgateway.bridge.endpoint.egress.pool;

import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactorySupplier;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpointFactory;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import reactor.core.publisher.Flux;

public class RandomSelectionWeightedEgressEndpointFactoryPoolTest {
  @Test
  public void testShouldBeEmptyWhenNoItemsInPool() {
    EgressEndpointFactorySupplier mock = Mockito.mock(EgressEndpointFactorySupplier.class);
    Mockito.when(mock.get()).thenReturn(Flux.never());

    RandomSelectionWeightedEgressEndpointFactoryPool pool =
        new RandomSelectionWeightedEgressEndpointFactoryPool(mock, "", new SimpleMeterRegistry());

    Optional<WeightedEgressEndpointFactory> lease = pool.lease();

    Assert.assertFalse(lease.isPresent());
  }

  @Test
  public void testShouldBeLeaseAnItem() {
    EgressEndpointFactorySupplier egressEndpointFactorySupplier =
        Mockito.mock(EgressEndpointFactorySupplier.class);
    Set<EgressEndpointFactory> setOfFactories =
        Sets.newSet(Mockito.mock(WeightedEgressEndpointFactory.class));

    Mockito.when(egressEndpointFactorySupplier.get()).thenReturn(Flux.just(setOfFactories));

    RandomSelectionWeightedEgressEndpointFactoryPool pool =
        new RandomSelectionWeightedEgressEndpointFactoryPool(
            egressEndpointFactorySupplier, "", new SimpleMeterRegistry());

    Assert.assertEquals(1, pool.size());
    Optional<WeightedEgressEndpointFactory> lease = pool.lease();
    Assert.assertTrue(lease.isPresent());
    Assert.assertEquals(0, pool.size());
    Optional<WeightedEgressEndpointFactory> lease1 = pool.lease();
    Assert.assertFalse(lease1.isPresent());
  }
}
