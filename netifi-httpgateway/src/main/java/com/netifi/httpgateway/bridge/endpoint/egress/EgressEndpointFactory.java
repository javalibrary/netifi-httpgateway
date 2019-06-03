package com.netifi.httpgateway.bridge.endpoint.egress;

import java.util.function.Supplier;
import reactor.core.Disposable;

public interface EgressEndpointFactory<E extends EgressEndpoint> extends Supplier<E>, Disposable {
  String getEgressEndpointId();
}
