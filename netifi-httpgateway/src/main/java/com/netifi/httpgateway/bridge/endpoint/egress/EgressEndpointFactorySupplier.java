package com.netifi.httpgateway.bridge.endpoint.egress;

import java.util.Set;
import java.util.function.Supplier;
import reactor.core.publisher.Flux;

public interface EgressEndpointFactorySupplier<
        E extends EgressEndpoint, F extends EgressEndpointFactory<E>>
    extends Supplier<Flux<Set<F>>> {}
