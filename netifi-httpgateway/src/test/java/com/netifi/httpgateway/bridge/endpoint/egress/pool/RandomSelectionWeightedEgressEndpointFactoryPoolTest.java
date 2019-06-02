package com.netifi.httpgateway.bridge.endpoint.egress.pool;

import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactorySupplier;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpointFactory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class RandomSelectionWeightedEgressEndpointFactoryPoolTest {
  @Test
  public void testShouldBeEmptyWhenNoItemsInPool() {
    EgressEndpointFactorySupplier mock = Mockito.mock(EgressEndpointFactorySupplier.class);
  
    RandomSelectionWeightedEgressEndpointFactoryPool pool
      = new RandomSelectionWeightedEgressEndpointFactoryPool(mock);
  
    Optional<WeightedEgressEndpointFactory> lease = pool.lease();

    Assert.assertFalse(lease.isPresent());
  }
}