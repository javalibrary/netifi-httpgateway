package com.netifi.httpgateway.bridge.endpoint.ingress;

import io.rsocket.Closeable;
import io.rsocket.RSocket;
import reactor.core.Disposable;

public interface IngressEndpointManager extends Disposable, Closeable {
  void register(String serviceName, RSocket target);

  void unregister(String serviceName);
}
