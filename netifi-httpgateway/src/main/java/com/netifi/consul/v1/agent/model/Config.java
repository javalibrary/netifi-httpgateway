package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Config {

  @JsonProperty("Datacenter")
  public String datacenter;
  @JsonProperty("NodeName")
  public String nodeName;
  @JsonProperty("Revision")
  public String revision;
  @JsonProperty("Server")
  public boolean server;
  @JsonProperty("Version")
  public String version;

  public String getDatacenter() {
    return datacenter;
  }

  public void setDatacenter(String datacenter) {
    this.datacenter = datacenter;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public String getRevision() {
    return revision;
  }

  public void setRevision(String revision) {
    this.revision = revision;
  }

  public boolean getServer() {
    return server;
  }

  public void setServer(boolean server) {
    this.server = server;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}