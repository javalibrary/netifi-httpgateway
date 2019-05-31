package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Member {

  @JsonProperty("Name")
  public String name;

  @JsonProperty("Address")
  public String address;

  @JsonProperty("Port")
  public int port;

  @JsonProperty("Tags")
  public Map<String, String> tags;

  @JsonProperty("Status")
  public int status;

  @JsonProperty("ProtocolMin")
  public int protocolMin;

  @JsonProperty("ProtocolMax")
  public int protocolMax;

  @JsonProperty("ProtocolCur")
  public int protocolCur;

  @JsonProperty("DelegateMin")
  public int delegateMin;

  @JsonProperty("DelegateMax")
  public int delegateMax;

  @JsonProperty("DelegateCur")
  public int delegateCur;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getProtocolMin() {
    return protocolMin;
  }

  public void setProtocolMin(int protocolMin) {
    this.protocolMin = protocolMin;
  }

  public int getProtocolMax() {
    return protocolMax;
  }

  public void setProtocolMax(int protocolMax) {
    this.protocolMax = protocolMax;
  }

  public int getProtocolCur() {
    return protocolCur;
  }

  public void setProtocolCur(int protocolCur) {
    this.protocolCur = protocolCur;
  }

  public int getDelegateMin() {
    return delegateMin;
  }

  public void setDelegateMin(int delegateMin) {
    this.delegateMin = delegateMin;
  }

  public int getDelegateMax() {
    return delegateMax;
  }

  public void setDelegateMax(int delegateMax) {
    this.delegateMax = delegateMax;
  }

  public int getDelegateCur() {
    return delegateCur;
  }

  public void setDelegateCur(int delegateCur) {
    this.delegateCur = delegateCur;
  }
}
