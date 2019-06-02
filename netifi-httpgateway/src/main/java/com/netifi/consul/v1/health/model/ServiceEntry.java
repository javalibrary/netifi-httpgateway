package com.netifi.consul.v1.health.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.netifi.consul.v1.agent.model.AgentService;
import com.netifi.consul.v1.catalog.model.Node;
import java.util.List;

// https://github.com/hashicorp/consul/blob/v1.5.1/api/health.go#L180
@JsonDeserialize(builder = ServiceEntry.Builder.class)
public class ServiceEntry {

  @JsonProperty("Node")
  private final Node node;

  @JsonProperty("Service")
  private final AgentService service;

  @JsonProperty("Checks")
  private final List<HealthCheck> agentChecks;

  private ServiceEntry(Builder builder) {
    node = builder.node;
    service = builder.service;
    agentChecks = builder.agentChecks;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Node getNode() {
    return node;
  }

  public AgentService getService() {
    return service;
  }

  public List<HealthCheck> getAgentChecks() {
    return agentChecks;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Node")
    private Node node;

    @JsonProperty("Service")
    private AgentService service;

    @JsonProperty("Checks")
    private List<HealthCheck> agentChecks;

    private Builder() {}

    public Builder withNode(Node val) {
      node = val;
      return this;
    }

    public Builder withService(AgentService val) {
      service = val;
      return this;
    }

    public Builder withAgentChecks(List<HealthCheck> val) {
      agentChecks = val;
      return this;
    }

    public ServiceEntry build() {
      return new ServiceEntry(this);
    }
  }
}
