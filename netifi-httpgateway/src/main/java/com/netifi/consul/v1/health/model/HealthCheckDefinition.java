package com.netifi.consul.v1.health.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class HealthCheckDefinition {
  @JsonProperty("HTTP")
  private String http;

  @JsonProperty("Header")
  private Map<String, List<String>> header;

  @JsonProperty("Method")
  private String method;

  @JsonProperty("TLSSkipVerify")
  private boolean tlsSkipVerify;

  @JsonProperty("TCP")
  private String tcp;

  public String getHttp() {
    return http;
  }

  public void setHttp(String http) {
    this.http = http;
  }

  public Map<String, List<String>> getHeader() {
    return header;
  }

  public void setHeader(Map<String, List<String>> header) {
    this.header = header;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public boolean isTlsSkipVerify() {
    return tlsSkipVerify;
  }

  public void setTlsSkipVerify(boolean tlsSkipVerify) {
    this.tlsSkipVerify = tlsSkipVerify;
  }

  public String getTcp() {
    return tcp;
  }

  public void setTcp(String tcp) {
    this.tcp = tcp;
  }
}
