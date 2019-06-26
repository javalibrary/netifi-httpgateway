package com.netifi.httpgateway.bridge.endpoint.egress.lb;

import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactorySupplier;
import io.micrometer.core.instrument.MeterRegistry;

public interface EgressEndpointLoadBalancerFactory<
    L extends EgressEndpointLoadBalancer,
    E extends EgressEndpoint,
    F extends EgressEndpointFactory<E>,
    S extends EgressEndpointFactorySupplier<E, F>> {
  L createNewLoadBalancer(String serviceName, S s, MeterRegistry registry);
}
