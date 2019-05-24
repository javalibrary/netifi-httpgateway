package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Coord {

  @JsonProperty("Vec")
  public List<Float> vec;

  @JsonProperty("Error")
  public Float error;

  @JsonProperty("Adjustment")
  public Float adjustment;

  @JsonProperty("Height")
  public Float height;

  public List<Float> getVec() {
    return vec;
  }

  public void setVec(List<Float> vec) {
    this.vec = vec;
  }

  public Float getError() {
    return error;
  }

  public void setError(Float error) {
    this.error = error;
  }

  public Float getAdjustment() {
    return adjustment;
  }

  public void setAdjustment(Float adjustment) {
    this.adjustment = adjustment;
  }

  public Float getHeight() {
    return height;
  }

  public void setHeight(Float height) {
    this.height = height;
  }
}
