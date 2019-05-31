package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Map;

// https://github.com/hashicorp/consul/blob/v1.5.1/agent/agent_endpoint.go#L85-L92
@JsonDeserialize(builder = Self.Builder.class)
public class Self {

  @JsonProperty("Config")
  private final SelfConfig selfConfig;

  @JsonInclude(Include.NON_NULL)
  @JsonProperty("DebugConfig")
  private final RuntimeConfig runtimeConfig;

  @JsonProperty("Member")
  private final Member member;

  @JsonProperty("Coord")
  private final Coord coord;

  // https://github.com/hashicorp/consul/blob/v1.5.1/agent/agent.go#L2972
  @JsonProperty("Stats")
  private final Map<String, Map<String, String>> stats;

  // https://github.com/hashicorp/consul/blob/v1.5.1/agent/local/state.go#L967:17
  @JsonProperty("Meta")
  private final Map<String, String> meta;

  private Self(Builder builder) {
    selfConfig = builder.selfConfig;
    runtimeConfig = builder.runtimeConfig;
    member = builder.member;
    coord = builder.coord;
    stats = builder.stats;
    meta = builder.meta;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public SelfConfig getSelfConfig() {
    return selfConfig;
  }

  public RuntimeConfig getRuntimeConfig() {
    return runtimeConfig;
  }

  public Member getMember() {
    return member;
  }

  public Coord getCoord() {
    return coord;
  }

  public Map<String, Map<String, String>> getStats() {
    return stats;
  }

  public Map<String, String> getMeta() {
    return meta;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Config")
    private SelfConfig selfConfig;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("DebugConfig")
    private RuntimeConfig runtimeConfig;

    @JsonProperty("Member")
    private Member member;

    @JsonProperty("Coord")
    private Coord coord;

    @JsonProperty("Stats")
    private Map<String, Map<String, String>> stats;

    @JsonProperty("Meta")
    private Map<String, String> meta;

    private Builder() {}

    public Builder withConfig(SelfConfig val) {
      selfConfig = val;
      return this;
    }

    public Builder withDebugConfig(RuntimeConfig val) {
      runtimeConfig = val;
      return this;
    }

    public Builder withMember(Member val) {
      member = val;
      return this;
    }

    public Builder withCoord(Coord val) {
      coord = val;
      return this;
    }

    public Builder withStats(Map<String, Map<String, String>> val) {
      stats = val;
      return this;
    }

    public Builder withMeta(Map<String, String> val) {
      meta = val;
      return this;
    }

    public Self build() {
      return new Self(this);
    }
  }
}
