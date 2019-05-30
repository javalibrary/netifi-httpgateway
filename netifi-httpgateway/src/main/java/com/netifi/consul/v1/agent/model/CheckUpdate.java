package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netifi.consul.v1.agent.model.Check.CheckStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckUpdate {

  @JsonProperty("Status")
  public CheckStatus status;

  @JsonProperty("Output")
  public String output;

  public CheckStatus getStatus() {
    return status;
  }

  public void setStatus(CheckStatus status) {
    this.status = status;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }
}
