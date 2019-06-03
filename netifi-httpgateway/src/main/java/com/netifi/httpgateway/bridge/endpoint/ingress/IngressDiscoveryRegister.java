package com.netifi.httpgateway.bridge.endpoint.ingress;

import reactor.core.Disposable;

interface IngressDiscoveryRegister {

  Disposable register(String serviceId, int port);
}
