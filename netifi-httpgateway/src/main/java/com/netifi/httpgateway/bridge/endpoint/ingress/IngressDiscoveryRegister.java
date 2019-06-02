package com.netifi.httpgateway.bridge.endpoint.ingress;

interface IngressDiscoveryRegister {

  void serviceRegister();

  void serviceDeregister();

  void passService();

  void failService();
}
