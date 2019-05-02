package com.netifi.httpgateway.bridge.endpoint.ingress;

import io.rsocket.Closeable;
import reactor.core.Disposable;

public interface IngressEndpoint extends Disposable, Closeable {
}
