package com.netifi.consul.v1.catalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Map;

// https://github.com/hashicorp/consul/blob/v1.5.1/api/catalog.go#L8
@JsonDeserialize(builder = Node.Builder.class)
public class Node {

  @JsonProperty("ID")
  private final String id;

  @JsonProperty("Node")
  private final String node;

  @JsonProperty("Address")
  private final String address;

  @JsonProperty("Datacenter")
  private final String datacenter;

  @JsonProperty("TaggedAddresses")
  private final Map<String, String> taggedAddresses;

  @JsonProperty("Meta")
  private final Map<String, String> meta;

  @JsonProperty("CreateIndex")
  private final Long createIndex;

  @JsonProperty("ModifyIndex")
  private final Long modifyIndex;

  private Node(Builder builder) {
    id = builder.id;
    node = builder.node;
    address = builder.address;
    datacenter = builder.datacenter;
    taggedAddresses = builder.taggedAddresses;
    meta = builder.meta;
    createIndex = builder.createIndex;
    modifyIndex = builder.modifyIndex;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getId() {
    return id;
  }

  public String getNode() {
    return node;
  }

  public String getAddress() {
    return address;
  }

  public String getDatacenter() {
    return datacenter;
  }

  public Map<String, String> getTaggedAddresses() {
    return taggedAddresses;
  }

  public Map<String, String> getMeta() {
    return meta;
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

    @JsonProperty("Node")
    private String node;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("Datacenter")
    private String datacenter;

    @JsonProperty("TaggedAddresses")
    private Map<String, String> taggedAddresses;

    @JsonProperty("Meta")
    private Map<String, String> meta;

    @JsonProperty("CreateIndex")
    private Long createIndex;

    @JsonProperty("ModifyIndex")
    private Long modifyIndex;

    private Builder() {}

    public Builder withId(String val) {
      id = val;
      return this;
    }

    public Builder withNode(String val) {
      node = val;
      return this;
    }

    public Builder withAddress(String val) {
      address = val;
      return this;
    }

    public Builder withDatacenter(String val) {
      datacenter = val;
      return this;
    }

    public Builder withTaggedAddresses(Map<String, String> val) {
      taggedAddresses = val;
      return this;
    }

    public Builder withMeta(Map<String, String> val) {
      meta = val;
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

    public Node build() {
      return new Node(this);
    }
  }
}
