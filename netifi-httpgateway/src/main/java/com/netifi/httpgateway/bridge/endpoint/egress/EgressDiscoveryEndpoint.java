package com.netifi.httpgateway.bridge.endpoint.egress;

public class EgressDiscoveryEndpoint {

  private final String host;
  private final int port;

  private EgressDiscoveryEndpoint(Builder builder) {
    host = builder.host;
    port = builder.port;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }


  public static final class Builder {

    private String host;
    private int port;

    private Builder() {
    }

    public Builder withHost(String val) {
      host = val;
      return this;
    }

    public Builder withPort(int val) {
      port = val;
      return this;
    }

    public EgressDiscoveryEndpoint build() {
      return new EgressDiscoveryEndpoint(this);
    }
  }
}
