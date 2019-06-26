package com.netifi.consul.v1.health.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import java.util.Map;

// https://github.com/hashicorp/consul/blob/v1.5.1/api/health.go#L48
@JsonDeserialize(builder = HealthCheckDefinition.Builder.class)
public class HealthCheckDefinition {
  @JsonProperty("HTTP")
  private final String http;

  @JsonProperty("Header")
  private final Map<String, List<String>> header;

  @JsonProperty("Method")
  private final String method;

  @JsonProperty("TLSSkipVerify")
  private final boolean tlsSkipVerify;

  @JsonProperty("TCP")
  private final String tcp;

  private HealthCheckDefinition(Builder builder) {
    http = builder.http;
    header = builder.header;
    method = builder.method;
    tlsSkipVerify = builder.tlsSkipVerify;
    tcp = builder.tcp;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getHttp() {
    return http;
  }

  public Map<String, List<String>> getHeader() {
    return header;
  }

  public String getMethod() {
    return method;
  }

  public boolean isTlsSkipVerify() {
    return tlsSkipVerify;
  }

  public String getTcp() {
    return tcp;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

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

    private Builder() {}

    public Builder withHttp(String val) {
      http = val;
      return this;
    }

    public Builder withHeader(Map<String, List<String>> val) {
      header = val;
      return this;
    }

    public Builder withMethod(String val) {
      method = val;
      return this;
    }

    public Builder withTlsSkipVerify(boolean val) {
      tlsSkipVerify = val;
      return this;
    }

    public Builder withTcp(String val) {
      tcp = val;
      return this;
    }

    public HealthCheckDefinition build() {
      return new HealthCheckDefinition(this);
    }
  }
}
