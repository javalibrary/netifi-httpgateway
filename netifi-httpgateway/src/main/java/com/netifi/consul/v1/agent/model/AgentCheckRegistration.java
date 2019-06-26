package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netifi.consul.v1.agent.model.AgentCheckRegistration.Builder;
import java.util.List;
import java.util.Map;

// https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L178
@JsonDeserialize(builder = Builder.class)
public class AgentCheckRegistration {

  @JsonProperty("ID")
  private final String id;

  @JsonProperty("ServiceID")
  private final String serviceId;

  // All the other fields of AgentServiceCheck except CheckID

  @JsonProperty("Name")
  private final String name;

  @JsonProperty("Args")
  private final List<String> args;

  @JsonProperty("DockerContainerID")
  private final String dockerContainerID;

  @JsonProperty("Shell")
  private final String shell;

  @JsonProperty("Interval")
  private final String interval;

  @JsonProperty("Timeout")
  private final String timeout;

  @JsonProperty("TTL")
  private final String ttl;

  @JsonProperty("HTTP")
  private final String http;

  @JsonProperty("Header")
  private final Map<String, List<String>> header;

  @JsonProperty("Method")
  private final String method;

  @JsonProperty("TCP")
  private final String tcp;

  @JsonProperty("Status")
  private final String status;

  @JsonProperty("Notes")
  private final String notes;

  @JsonProperty("TLSSkipVerify")
  private final Boolean tlsSkipVerify;

  @JsonProperty("GRPC")
  private final String grpc;

  @JsonProperty("GRPCUseTLS")
  private final Boolean grpcUseTLS;

  @JsonProperty("AliasNode")
  private final String aliasNode;

  @JsonProperty("AliasService")
  private final String aliasService;

  @JsonProperty("DeregisterCriticalServiceAfter")
  private final String deregisterCriticalServiceAfter;

  private AgentCheckRegistration(Builder builder) {
    id = builder.id;
    serviceId = builder.serviceId;
    name = builder.name;
    args = builder.args;
    dockerContainerID = builder.dockerContainerID;
    shell = builder.shell;
    interval = builder.interval;
    timeout = builder.timeout;
    ttl = builder.ttl;
    http = builder.http;
    header = builder.header;
    method = builder.method;
    tcp = builder.tcp;
    status = builder.status;
    notes = builder.notes;
    tlsSkipVerify = builder.tlsSkipVerify;
    grpc = builder.grpc;
    grpcUseTLS = builder.grpcUseTLS;
    aliasNode = builder.aliasNode;
    aliasService = builder.aliasService;
    deregisterCriticalServiceAfter = builder.deregisterCriticalServiceAfter;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getId() {
    return id;
  }

  public String getServiceId() {
    return serviceId;
  }

  public String getName() {
    return name;
  }

  public List<String> getArgs() {
    return args;
  }

  public String getDockerContainerID() {
    return dockerContainerID;
  }

  public String getShell() {
    return shell;
  }

  public String getInterval() {
    return interval;
  }

  public String getTimeout() {
    return timeout;
  }

  public String getTtl() {
    return ttl;
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

  public String getTcp() {
    return tcp;
  }

  public String getStatus() {
    return status;
  }

  public String getNotes() {
    return notes;
  }

  public Boolean getTlsSkipVerify() {
    return tlsSkipVerify;
  }

  public String getGrpc() {
    return grpc;
  }

  public Boolean getGrpcUseTLS() {
    return grpcUseTLS;
  }

  public String getAliasNode() {
    return aliasNode;
  }

  public String getAliasService() {
    return aliasService;
  }

  public String getDeregisterCriticalServiceAfter() {
    return deregisterCriticalServiceAfter;
  }

  public static final class Builder {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("ServiceID")
    private String serviceId;

    // All the other fields of AgentServiceCheck except CheckID

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Args")
    private List<String> args;

    @JsonProperty("DockerContainerID")
    private String dockerContainerID;

    @JsonProperty("Shell")
    private String shell;

    @JsonProperty("Interval")
    private String interval;

    @JsonProperty("Timeout")
    private String timeout;

    @JsonProperty("TTL")
    private String ttl;

    @JsonProperty("HTTP")
    private String http;

    @JsonProperty("Header")
    private Map<String, List<String>> header;

    @JsonProperty("Method")
    private String method;

    @JsonProperty("TCP")
    private String tcp;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Notes")
    private String notes;

    @JsonProperty("TLSSkipVerify")
    private Boolean tlsSkipVerify;

    @JsonProperty("GRPC")
    private String grpc;

    @JsonProperty("GRPCUseTLS")
    private Boolean grpcUseTLS;

    @JsonProperty("AliasNode")
    private String aliasNode;

    @JsonProperty("AliasService")
    private String aliasService;

    @JsonProperty("DeregisterCriticalServiceAfter")
    private String deregisterCriticalServiceAfter;

    private Builder() {}

    public Builder withId(String val) {
      id = val;
      return this;
    }

    public Builder withServiceId(String val) {
      serviceId = val;
      return this;
    }

    public Builder withName(String val) {
      name = val;
      return this;
    }

    public Builder withArgs(List<String> val) {
      args = val;
      return this;
    }

    public Builder withDockerContainerID(String val) {
      dockerContainerID = val;
      return this;
    }

    public Builder withShell(String val) {
      shell = val;
      return this;
    }

    public Builder withInterval(String val) {
      interval = val;
      return this;
    }

    public Builder withTimeout(String val) {
      timeout = val;
      return this;
    }

    public Builder withTtl(String val) {
      ttl = val;
      return this;
    }

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

    public Builder withTcp(String val) {
      tcp = val;
      return this;
    }

    public Builder withStatus(String val) {
      status = val;
      return this;
    }

    public Builder withNotes(String val) {
      notes = val;
      return this;
    }

    public Builder withTlsSkipVerify(Boolean val) {
      tlsSkipVerify = val;
      return this;
    }

    public Builder withGrpc(String val) {
      grpc = val;
      return this;
    }

    public Builder withGrpcUseTLS(Boolean val) {
      grpcUseTLS = val;
      return this;
    }

    public Builder withAliasNode(String val) {
      aliasNode = val;
      return this;
    }

    public Builder withAliasService(String val) {
      aliasService = val;
      return this;
    }

    public Builder withDeregisterCriticalServiceAfter(String val) {
      deregisterCriticalServiceAfter = val;
      return this;
    }

    public AgentCheckRegistration build() {
      return new AgentCheckRegistration(this);
    }
  }
}
