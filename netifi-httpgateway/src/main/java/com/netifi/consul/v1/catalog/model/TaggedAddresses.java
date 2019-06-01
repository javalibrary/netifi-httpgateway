package com.netifi.consul.v1.catalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = TaggedAddresses.Builder.class)
public class TaggedAddresses {
  @JsonProperty("wan")
  private final String wan;

  @JsonProperty("lan")
  private final String lan;

  private TaggedAddresses(Builder builder) {
    wan = builder.wan;
    lan = builder.lan;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getWan() {
    return wan;
  }

  public String getLan() {
    return lan;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("wan")
    private String wan;

    @JsonProperty("lan")
    private String lan;

    private Builder() {}

    public Builder withWan(String val) {
      wan = val;
      return this;
    }

    public Builder withLan(String val) {
      lan = val;
      return this;
    }

    public TaggedAddresses build() {
      return new TaggedAddresses(this);
    }
  }
}
