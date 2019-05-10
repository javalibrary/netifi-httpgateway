package com.netifi.httpgateway.bridge.endpoint.egress;

import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.function.Supplier;

public interface EgressEndpointFactorySupplier<
        E extends EgressEndpoint, F extends EgressEndpointFactory<E>>
    extends Supplier<Flux<Set<F>>> {}
