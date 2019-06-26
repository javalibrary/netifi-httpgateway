package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.net.URI;

@JsonDeserialize(builder = NetworkSegment.Builder.class)
public class NetworkSegment {

  @JsonProperty("Name")
  private final String name;

  @JsonProperty("Bind")
  private final String bind;

  @JsonProperty("Port")
  private final int port;

  @JsonProperty("Advertise")
  private final String advertise;

  @JsonProperty("RPCAddr")
  private URI rpcAddr;

  private NetworkSegment(Builder builder) {
    name = builder.name;
    bind = builder.bind;
    port = builder.port;
    advertise = builder.advertise;
    rpcAddr = builder.rpcAddr;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public String getBind() {
    return bind;
  }

  public int getPort() {
    return port;
  }

  public String getAdvertise() {
    return advertise;
  }

  public URI getRpcAddr() {
    return rpcAddr;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Bind")
    private String bind;

    @JsonProperty("Port")
    private int port;

    @JsonProperty("Advertise")
    private String advertise;

    @JsonProperty("RPCAddr")
    private URI rpcAddr;

    private Builder() {}

    public Builder withName(String val) {
      name = val;
      return this;
    }

    public Builder withBind(String val) {
      bind = val;
      return this;
    }

    public Builder withPort(int val) {
      port = val;
      return this;
    }

    public Builder withAdvertise(String val) {
      advertise = val;
      return this;
    }

    public Builder withRpcAddr(URI val) {
      rpcAddr = val;
      return this;
    }

    public NetworkSegment build() {
      return new NetworkSegment(this);
    }
  }
}
