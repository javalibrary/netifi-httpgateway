package com.netifi.httpgateway.bridge.endpoint.egress.lb;

import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactorySupplier;

public interface EgressEndpointLoadBalancerFactory<
    L extends EgressEndpointLoadBalancer,
    E extends EgressEndpoint,
    F extends EgressEndpointFactory<E>,
    S extends EgressEndpointFactorySupplier<E, F>> {
  L createNewLoadBalancer(S s);
}
