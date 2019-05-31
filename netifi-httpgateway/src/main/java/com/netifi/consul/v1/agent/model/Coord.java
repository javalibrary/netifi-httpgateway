package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;

// https://github.com/hashicorp/consul/blob/v1.5.1/agent/agent_endpoint.go#L65
// https://github.com/hashicorp/consul/blob/v1.5.1/vendor/github.com/hashicorp/serf/coordinate/coordinate.go#L12-L32
@JsonDeserialize(builder = Coord.Builder.class)
public class Coord {

  @JsonProperty("Vec")
  private final List<Float> vec;

  @JsonProperty("Error")
  private final Float error;

  @JsonProperty("Adjustment")
  private final Float adjustment;

  @JsonProperty("Height")
  private final Float height;

  private Coord(Builder builder) {
    vec = builder.vec;
    error = builder.error;
    adjustment = builder.adjustment;
    height = builder.height;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public List<Float> getVec() {
    return vec;
  }

  public Float getError() {
    return error;
  }

  public Float getAdjustment() {
    return adjustment;
  }

  public Float getHeight() {
    return height;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("Vec")
    private List<Float> vec;

    @JsonProperty("Error")
    private Float error;

    @JsonProperty("Adjustment")
    private Float adjustment;

    @JsonProperty("Height")
    private Float height;

    private Builder() {}

    public Builder withVec(List<Float> val) {
      vec = val;
      return this;
    }

    public Builder withError(Float val) {
      error = val;
      return this;
    }

    public Builder withAdjustment(Float val) {
      adjustment = val;
      return this;
    }

    public Builder withHeight(Float val) {
      height = val;
      return this;
    }

    public Coord build() {
      return new Coord(this);
    }
  }
}
