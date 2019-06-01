package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.netifi.consul.v1.agent.model.AgentService.Builder;
import java.util.List;
import java.util.Map;

// https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L77
@JsonDeserialize(builder = AgentService.Builder.class)
public class AgentService {

  @JsonProperty("Kind")
  private final String kind;

  @JsonProperty("ID")
  private final String id;

  @JsonProperty("Service")
  private final String service;

  @JsonProperty("Tags")
  private final List<String> tags;

  @JsonProperty("Meta")
  private final Map<String, String> meta;

  @JsonProperty("Port")
  private final Integer port;

  @JsonProperty("Address")
  private final String address;

  @JsonProperty("Weights")
  private final AgentWeights weights;

  @JsonProperty("EnableTagOverride")
  private final Boolean enableTagOverride;

  @JsonProperty("CreateIndex")
  private final Long createIndex;

  @JsonProperty("ModifyIndex")
  private final Long modifyIndex;

  @JsonProperty("ContentHash")
  private final String contentHash;

  private AgentService(Builder builder) {
    kind = builder.kind;
    id = builder.id;
    service = builder.service;
    tags = builder.tags;
    meta = builder.meta;
    port = builder.port;
    address = builder.address;
    weights = builder.weights;
    enableTagOverride = builder.enableTagOverride;
    createIndex = builder.createIndex;
    modifyIndex = builder.modifyIndex;
    contentHash = builder.contentHash;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getKind() {
    return kind;
  }

  public String getId() {
    return id;
  }

  public String getService() {
    return service;
  }

  public List<String> getTags() {
    return tags;
  }

  public Map<String, String> getMeta() {
    return meta;
  }

  public Integer getPort() {
    return port;
  }

  public String getAddress() {
    return address;
  }

  public AgentWeights getWeights() {
    return weights;
  }

  public Boolean getEnableTagOverride() {
    return enableTagOverride;
  }

  public Long getCreateIndex() {
    return createIndex;
  }

  public Long getModifyIndex() {
    return modifyIndex;
  }

  public String getContentHash() {
    return contentHash;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Kind")
    private String kind;

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Service")
    private String service;

    @JsonProperty("Tags")
    private List<String> tags;

    @JsonProperty("Meta")
    private Map<String, String> meta;

    @JsonProperty("Port")
    private Integer port;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("Weights")
    private AgentWeights weights;

    @JsonProperty("EnableTagOverride")
    private Boolean enableTagOverride;

    @JsonProperty("CreateIndex")
    private Long createIndex;

    @JsonProperty("ModifyIndex")
    private Long modifyIndex;

    @JsonProperty("ContentHash")
    private String contentHash;

    private Builder() {}

    public Builder withKind(String val) {
      kind = val;
      return this;
    }

    public Builder withId(String val) {
      id = val;
      return this;
    }

    public Builder withService(String val) {
      service = val;
      return this;
    }

    public Builder withTags(List<String> val) {
      tags = val;
      return this;
    }

    public Builder withMeta(Map<String, String> val) {
      meta = val;
      return this;
    }

    public Builder withPort(Integer val) {
      port = val;
      return this;
    }

    public Builder withAddress(String val) {
      address = val;
      return this;
    }

    public Builder withWeights(AgentWeights val) {
      weights = val;
      return this;
    }

    public Builder withEnableTagOverride(Boolean val) {
      enableTagOverride = val;
      return this;
    }

    public Builder withCreateIndex(Long val) {
      createIndex = val;
      return this;
    }

    public Builder withModifyIndex(Long val) {
      modifyIndex = val;
      return this;
    }

    public Builder withContentHash(String val) {
      contentHash = val;
      return this;
    }

    public AgentService build() {
      return new AgentService(this);
    }
  }
}
