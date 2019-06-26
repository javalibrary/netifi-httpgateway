package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

// https://github.com/hashicorp/consul/blob/v1.5.1/agent/agent_endpoint.go#L70-L84
@JsonDeserialize(builder = SelfConfig.Builder.class)
public class SelfConfig {

  @JsonProperty("Datacenter")
  private final String datacenter;

  @JsonProperty("NodeName")
  private final String nodeName;

  @JsonProperty("Revision")
  private final String revision;

  @JsonProperty("Server")
  private final boolean server;

  @JsonProperty("Version")
  private final String version;

  private SelfConfig(Builder builder) {
    datacenter = builder.datacenter;
    nodeName = builder.nodeName;
    revision = builder.revision;
    server = builder.server;
    version = builder.version;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getDatacenter() {
    return datacenter;
  }

  public String getNodeName() {
    return nodeName;
  }

  public String getRevision() {
    return revision;
  }

  public boolean isServer() {
    return server;
  }

  public String getVersion() {
    return version;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Datacenter")
    private String datacenter;

    @JsonProperty("NodeName")
    private String nodeName;

    @JsonProperty("Revision")
    private String revision;

    @JsonProperty("Server")
    private boolean server;

    @JsonProperty("Version")
    private String version;

    private Builder() {}

    public Builder withDatacenter(String val) {
      datacenter = val;
      return this;
    }

    public Builder withNodeName(String val) {
      nodeName = val;
      return this;
    }

    public Builder withRevision(String val) {
      revision = val;
      return this;
    }

    public Builder withServer(boolean val) {
      server = val;
      return this;
    }

    public Builder withVersion(String val) {
      version = val;
      return this;
    }

    public SelfConfig build() {
      return new SelfConfig(this);
    }
  }
}
