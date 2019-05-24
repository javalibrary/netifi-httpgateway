package com.netifi.consul.v1.catalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaggedAddresses {
  @JsonProperty("wan")
  public String wan;
  @JsonProperty("lan")
  public String lan;

  public String getWan() {
    return wan;
  }

  public void setWan(String wan) {
    this.wan = wan;
  }

  public String getLan() {
    return lan;
  }

  public void setLan(String lan) {
    this.lan = lan;
  }
}
