package com.netifi.httpgateway.bridge.endpoint.egress;

import reactor.core.Disposable;

import java.util.function.Supplier;

public interface EgressEndpointFactory<E extends EgressEndpoint> extends Supplier<E>, Disposable {
  String getEgressEndpointId();
}
