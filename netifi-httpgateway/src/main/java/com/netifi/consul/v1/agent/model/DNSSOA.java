package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DNSSOA.Builder.class)
public class DNSSOA {

  @JsonProperty("Expire")
  private final int expire;

  @JsonProperty("Minttl")
  private final int minttl;

  @JsonProperty("Refresh")
  private final int refresh;

  @JsonProperty("Retry")
  private final int retry;

  private DNSSOA(Builder builder) {
    expire = builder.expire;
    minttl = builder.minttl;
    refresh = builder.refresh;
    retry = builder.retry;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getExpire() {
    return expire;
  }

  public int getMinttl() {
    return minttl;
  }

  public int getRefresh() {
    return refresh;
  }

  public int getRetry() {
    return retry;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Expire")
    private int expire;

    @JsonProperty("Minttl")
    private int minttl;

    @JsonProperty("Refresh")
    private int refresh;

    @JsonProperty("Retry")
    private int retry;

    private Builder() {}

    public Builder withExpire(int val) {
      expire = val;
      return this;
    }

    public Builder withMinttl(int val) {
      minttl = val;
      return this;
    }

    public Builder withRefresh(int val) {
      refresh = val;
      return this;
    }

    public Builder withRetry(int val) {
      retry = val;
      return this;
    }

    public DNSSOA build() {
      return new DNSSOA(this);
    }
  }
}
