package com.netifi.httpgateway.bridge.endpoint.ingress;

public interface IngressDiscoveryRegister {

  public void serviceRegister();

  public void serviceDeregister();

  public void dispose();

  public void passService();

  public void failService();
}
