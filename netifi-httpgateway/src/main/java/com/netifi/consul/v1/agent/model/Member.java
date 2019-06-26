package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Map;

// https://github.com/hashicorp/consul/blob/v1.5.1/agent/agent.go#L1616-L1618
// https://github.com/hashicorp/consul/blob/v1.5.1/vendor/github.com/hashicorp/serf/serf/serf.go#L128-L144
@JsonDeserialize(builder = Member.Builder.class)
public final class Member {

  @JsonProperty("Name")
  private final String name;

  @JsonProperty("Addr")
  private final String address;

  @JsonProperty("Port")
  private final int port;

  @JsonProperty("Tags")
  private final Map<String, String> tags;

  @JsonProperty("Status")
  private final int status;

  @JsonProperty("ProtocolMin")
  private final int protocolMin;

  @JsonProperty("ProtocolMax")
  private final int protocolMax;

  @JsonProperty("ProtocolCur")
  private final int protocolCur;

  @JsonProperty("DelegateMin")
  private final int delegateMin;

  @JsonProperty("DelegateMax")
  private final int delegateMax;

  @JsonProperty("DelegateCur")
  private final int delegateCur;

  private Member(Builder builder) {
    name = builder.name;
    address = builder.address;
    port = builder.port;
    tags = builder.tags;
    status = builder.status;
    protocolMin = builder.protocolMin;
    protocolMax = builder.protocolMax;
    protocolCur = builder.protocolCur;
    delegateMin = builder.delegateMin;
    delegateMax = builder.delegateMax;
    delegateCur = builder.delegateCur;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public int getPort() {
    return port;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public int getStatus() {
    return status;
  }

  public int getProtocolMin() {
    return protocolMin;
  }

  public int getProtocolMax() {
    return protocolMax;
  }

  public int getProtocolCur() {
    return protocolCur;
  }

  public int getDelegateMin() {
    return delegateMin;
  }

  public int getDelegateMax() {
    return delegateMax;
  }

  public int getDelegateCur() {
    return delegateCur;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Addr")
    private String address;

    @JsonProperty("Port")
    private int port;

    @JsonProperty("Tags")
    private Map<String, String> tags;

    @JsonProperty("Status")
    private int status;

    @JsonProperty("ProtocolMin")
    private int protocolMin;

    @JsonProperty("ProtocolMax")
    private int protocolMax;

    @JsonProperty("ProtocolCur")
    private int protocolCur;

    @JsonProperty("DelegateMin")
    private int delegateMin;

    @JsonProperty("DelegateMax")
    private int delegateMax;

    @JsonProperty("DelegateCur")
    private int delegateCur;

    private Builder() {}

    public Builder withName(String val) {
      name = val;
      return this;
    }

    public Builder withAddress(String val) {
      address = val;
      return this;
    }

    public Builder withPort(int val) {
      port = val;
      return this;
    }

    public Builder withTags(Map<String, String> val) {
      tags = val;
      return this;
    }

    public Builder withStatus(int val) {
      status = val;
      return this;
    }

    public Builder withProtocolMin(int val) {
      protocolMin = val;
      return this;
    }

    public Builder withProtocolMax(int val) {
      protocolMax = val;
      return this;
    }

    public Builder withProtocolCur(int val) {
      protocolCur = val;
      return this;
    }

    public Builder withDelegateMin(int val) {
      delegateMin = val;
      return this;
    }

    public Builder withDelegateMax(int val) {
      delegateMax = val;
      return this;
    }

    public Builder withDelegateCur(int val) {
      delegateCur = val;
      return this;
    }

    public Member build() {
      return new Member(this);
    }
  }
}
