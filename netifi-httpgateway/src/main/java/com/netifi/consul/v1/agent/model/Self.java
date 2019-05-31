package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Self {

  @JsonProperty("Config")
  public Config config;

  @JsonInclude(Include.NON_NULL)
  @JsonProperty("DebugConfig")
  public DebugConfig debugConfig;

  @JsonProperty("Member")
  public Member member;

  @JsonProperty("Coord")
  public Coord coord;

  @JsonProperty("Stats")
  public Map<String, Map<String, String>> stats;

  @JsonProperty("Meta")
  public Map<String, String> meta;

  public Config getConfig() {
    return config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public DebugConfig getDebugConfig() {
    return debugConfig;
  }

  public void setDebugConfig(DebugConfig debugConfig) {
    this.debugConfig = debugConfig;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }
}
