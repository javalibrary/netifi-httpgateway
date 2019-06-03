package com.netifi.httpgateway.bridge.endpoint.egress;

import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.WeightedEgressEndpointFactory;
import java.util.Set;
import reactor.core.publisher.Flux;

public class DefaultEgressEndpointFactorySupplier
    implements EgressEndpointFactorySupplier<
        WeightedEgressEndpoint, WeightedEgressEndpointFactory> {

  // TODO: use events from consul to maintain a Set<WeightedEgressEndpointFactory>

  @Override
  public Flux<Set<WeightedEgressEndpointFactory>> get() {
    return null;
  }
}
