package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import java.util.Map;

@JsonDeserialize(builder = Service.Builder.class)
public class Service {

  @JsonProperty("ID")
  private final String id;

  @JsonProperty("Service")
  private final String service;

  @JsonProperty("Tags")
  private final List<String> tags;

  @JsonProperty("Address")
  private final String address;

  @JsonProperty("Meta")
  private final Map<String, String> meta;

  @JsonProperty("Port")
  private final Integer port;

  @JsonProperty("EnableTagOverride")
  private final Boolean enableTagOverride;

  @JsonProperty("CreateIndex")
  private final Long createIndex;

  @JsonProperty("ModifyIndex")
  private final Long modifyIndex;

  private Service(Builder builder) {
    id = builder.id;
    service = builder.service;
    tags = builder.tags;
    address = builder.address;
    meta = builder.meta;
    port = builder.port;
    enableTagOverride = builder.enableTagOverride;
    createIndex = builder.createIndex;
    modifyIndex = builder.modifyIndex;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return "Service{"
        + "id='"
        + id
        + '\''
        + ", service='"
        + service
        + '\''
        + ", tags="
        + tags
        + ", address='"
        + address
        + '\''
        + ", meta="
        + meta
        + ", port="
        + port
        + ", enableTagOverride="
        + enableTagOverride
        + ", createIndex="
        + createIndex
        + ", modifyIndex="
        + modifyIndex
        + '}';
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

  public String getAddress() {
    return address;
  }

  public Map<String, String> getMeta() {
    return meta;
  }

  public Integer getPort() {
    return port;
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

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Service")
    private String service;

    @JsonProperty("Tags")
    private List<String> tags;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("Meta")
    private Map<String, String> meta;

    @JsonProperty("Port")
    private Integer port;

    @JsonProperty("EnableTagOverride")
    private Boolean enableTagOverride;

    @JsonProperty("CreateIndex")
    private Long createIndex;

    @JsonProperty("ModifyIndex")
    private Long modifyIndex;

    private Builder() {}

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

    public Builder withAddress(String val) {
      address = val;
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

    public Service build() {
      return new Service(this);
    }
  }
}
