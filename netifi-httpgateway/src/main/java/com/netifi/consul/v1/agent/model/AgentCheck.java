package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.netifi.consul.v1.health.model.HealthCheckDefinition;
import java.util.List;

@JsonDeserialize(builder = AgentCheck.Builder.class)
public class AgentCheck {

  @JsonProperty("Node")
  private final String node;

  @JsonProperty("CheckID")
  private final String checkId;

  @JsonProperty("Name")
  private final String name;

  @JsonProperty("Status")
  private final CheckStatus status;

  @JsonProperty("Notes")
  private final String notes;

  @JsonProperty("Output")
  private final String output;

  @JsonProperty("ServiceID")
  private final String serviceId;

  @JsonProperty("ServiceName")
  private final String serviceName;

  @JsonProperty("ServiceTags")
  private final List<String> serviceTags;

  @JsonProperty("CreateIndex")
  private final Long createIndex;

  @JsonProperty("ModifyIndex")
  private final Long modifyIndex;

  @JsonProperty("Definition")
  private final HealthCheckDefinition healthCheckDefinition;

  private AgentCheck(Builder builder) {
    node = builder.node;
    checkId = builder.checkId;
    name = builder.name;
    status = builder.status;
    notes = builder.notes;
    output = builder.output;
    serviceId = builder.serviceId;
    serviceName = builder.serviceName;
    serviceTags = builder.serviceTags;
    createIndex = builder.createIndex;
    modifyIndex = builder.modifyIndex;
    healthCheckDefinition = builder.healthCheckDefinition;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getNode() {
    return node;
  }

  public String getCheckId() {
    return checkId;
  }

  public String getName() {
    return name;
  }

  public CheckStatus getStatus() {
    return status;
  }

  public String getNotes() {
    return notes;
  }

  public String getOutput() {
    return output;
  }

  public String getServiceId() {
    return serviceId;
  }

  public String getServiceName() {
    return serviceName;
  }

  public List<String> getServiceTags() {
    return serviceTags;
  }

  public Long getCreateIndex() {
    return createIndex;
  }

  public Long getModifyIndex() {
    return modifyIndex;
  }

  public HealthCheckDefinition getHealthCheckDefinition() {
    return healthCheckDefinition;
  }

  @Override
  public String toString() {
    return "AgentCheck{"
        + "node='"
        + node
        + '\''
        + ", checkId='"
        + checkId
        + '\''
        + ", name='"
        + name
        + '\''
        + ", status="
        + status
        + ", notes='"
        + notes
        + '\''
        + ", output='"
        + output
        + '\''
        + ", serviceId='"
        + serviceId
        + '\''
        + ", serviceName='"
        + serviceName
        + '\''
        + ", serviceTags="
        + serviceTags
        + ", createIndex="
        + createIndex
        + ", modifyIndex="
        + modifyIndex
        + '}';
  }

  public static enum CheckStatus {
    @JsonProperty("unknown")
    UNKNOWN,
    @JsonProperty("passing")
    PASSING,
    @JsonProperty("warning")
    WARNING,
    @JsonProperty("critical")
    CRITICAL
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Node")
    private String node;

    @JsonProperty("CheckID")
    private String checkId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Status")
    private CheckStatus status;

    @JsonProperty("Notes")
    private String notes;

    @JsonProperty("Output")
    private String output;

    @JsonProperty("ServiceID")
    private String serviceId;

    @JsonProperty("ServiceName")
    private String serviceName;

    @JsonProperty("ServiceTags")
    private List<String> serviceTags;

    @JsonProperty("CreateIndex")
    private Long createIndex;

    @JsonProperty("ModifyIndex")
    private Long modifyIndex;

    @JsonProperty("Definition")
    private HealthCheckDefinition healthCheckDefinition;

    private Builder() {}

    public Builder withNode(String val) {
      node = val;
      return this;
    }

    public Builder withCheckId(String val) {
      checkId = val;
      return this;
    }

    public Builder withName(String val) {
      name = val;
      return this;
    }

    public Builder withStatus(CheckStatus val) {
      status = val;
      return this;
    }

    public Builder withNotes(String val) {
      notes = val;
      return this;
    }

    public Builder withOutput(String val) {
      output = val;
      return this;
    }

    public Builder withServiceId(String val) {
      serviceId = val;
      return this;
    }

    public Builder withServiceName(String val) {
      serviceName = val;
      return this;
    }

    public Builder withServiceTags(List<String> val) {
      serviceTags = val;
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

    public Builder withHealthCheckDefinition(HealthCheckDefinition val) {
      healthCheckDefinition = val;
      return this;
    }

    public AgentCheck build() {
      return new AgentCheck(this);
    }
  }
}
