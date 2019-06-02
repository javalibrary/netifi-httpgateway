package com.netifi.httpgateway.bridge.endpoint.egress.lb.ewma;

import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactorySupplier;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.EgressEndpointLoadBalancerFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.pool.RandomSelectionWeightedEgressEndpointFactoryPool;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

/** Factory that creates {@link EWMAEndpointLoadBalancerFactory} */
@Component
public class EWMAEndpointLoadBalancerFactory
    implements EgressEndpointLoadBalancerFactory<
        EWMAEndpointLoadBalancer,
        WeightedEgressEndpoint,
        WeightedEgressEndpointFactory,
        EgressEndpointFactorySupplier<WeightedEgressEndpoint, WeightedEgressEndpointFactory>> {

  /**
   * Creates a new loadbalancer that is populated with EgressEndpoints supplied by the provided
   * factory supplier
   *
   * @param egressEndpointFactorySupplier a supplier that produces a stream of sets of
   *     WeightedEgressEndpointFactory. These factories are used by the load balancer to create new
   *     connections based on the aperture.
   * @return a load balancer
   */
  public EWMAEndpointLoadBalancer createNewLoadBalancer(
      String serviceName,
      EgressEndpointFactorySupplier<WeightedEgressEndpoint, WeightedEgressEndpointFactory>
          egressEndpointFactorySupplier,
      MeterRegistry registry) {
    RandomSelectionWeightedEgressEndpointFactoryPool pool =
        new RandomSelectionWeightedEgressEndpointFactoryPool(
            egressEndpointFactorySupplier, serviceName, registry);

    return new EWMAEndpointLoadBalancer(pool, registry);
  }
}
