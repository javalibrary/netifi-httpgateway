package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.netifi.consul.v1.agent.model.AgentCheck.CheckStatus;

@JsonDeserialize(builder = CheckUpdate.Builder.class)
public class CheckUpdate {

  @JsonProperty("Status")
  private final CheckStatus status;

  @JsonProperty("Output")
  private final String output;

  private CheckUpdate(Builder builder) {
    status = builder.status;
    output = builder.output;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public CheckStatus getStatus() {
    return status;
  }

  public String getOutput() {
    return output;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Status")
    private CheckStatus status;

    @JsonProperty("Output")
    private String output;

    private Builder() {}

    public Builder withStatus(CheckStatus val) {
      status = val;
      return this;
    }

    public Builder withOutput(String val) {
      output = val;
      return this;
    }

    public CheckUpdate build() {
      return new CheckUpdate(this);
    }
  }
}
