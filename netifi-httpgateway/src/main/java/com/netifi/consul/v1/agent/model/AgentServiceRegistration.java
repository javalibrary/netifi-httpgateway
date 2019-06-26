package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import java.util.Map;

// https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L159
@JsonDeserialize(builder = AgentServiceRegistration.Builder.class)
public class AgentServiceRegistration {

  @JsonProperty("Kind")
  private final String kind;

  @JsonProperty("ID")
  private final String id;

  @JsonProperty("Name")
  private final String name;

  @JsonProperty("Tags")
  private final List<String> tags;

  @JsonProperty("Port")
  private final Integer port;

  @JsonProperty("Address")
  private final String address;

  @JsonProperty("EnableTagOverride")
  private final Boolean enableTagOverride;

  @JsonProperty("Meta")
  private final Map<String, String> meta;

  @JsonProperty("Weights")
  private final AgentWeights agentWeights;

  @JsonProperty("Check")
  @JsonInclude(Include.NON_EMPTY)
  private final AgentServiceCheck check;

  @JsonProperty("Checks")
  private final List<AgentServiceCheck> checks;

  private AgentServiceRegistration(Builder builder) {
    kind = builder.kind;
    id = builder.id;
    name = builder.name;
    tags = builder.tags;
    port = builder.port;
    address = builder.address;
    enableTagOverride = builder.enableTagOverride;
    meta = builder.meta;
    agentWeights = builder.agentWeights;
    check = builder.check;
    checks = builder.checks;
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

  public String getName() {
    return name;
  }

  public List<String> getTags() {
    return tags;
  }

  public Integer getPort() {
    return port;
  }

  public String getAddress() {
    return address;
  }

  public Boolean getEnableTagOverride() {
    return enableTagOverride;
  }

  public Map<String, String> getMeta() {
    return meta;
  }

  public AgentWeights getAgentWeights() {
    return agentWeights;
  }

  public AgentServiceCheck getCheck() {
    return check;
  }

  public List<AgentServiceCheck> getChecks() {
    return checks;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Kind")
    private String kind;

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Tags")
    private List<String> tags;

    @JsonProperty("Port")
    private Integer port;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("EnableTagOverride")
    private Boolean enableTagOverride;

    @JsonProperty("Meta")
    private Map<String, String> meta;

    @JsonProperty("Weights")
    private AgentWeights agentWeights;

    @JsonProperty("Check")
    @JsonInclude(Include.NON_EMPTY)
    private AgentServiceCheck check;

    @JsonProperty("Checks")
    private List<AgentServiceCheck> checks;

    private Builder() {}

    public Builder withKind(String val) {
      kind = val;
      return this;
    }

    public Builder withId(String val) {
      id = val;
      return this;
    }

    public Builder withName(String val) {
      name = val;
      return this;
    }

    public Builder withTags(List<String> val) {
      tags = val;
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

    public Builder withEnableTagOverride(Boolean val) {
      enableTagOverride = val;
      return this;
    }

    public Builder withMeta(Map<String, String> val) {
      meta = val;
      return this;
    }

    public Builder withAgentWeights(AgentWeights val) {
      agentWeights = val;
      return this;
    }

    public Builder withCheck(AgentServiceCheck val) {
      check = val;
      return this;
    }

    public Builder withChecks(List<AgentServiceCheck> val) {
      checks = val;
      return this;
    }

    public AgentServiceRegistration build() {
      return new AgentServiceRegistration(this);
    }
  }
}
