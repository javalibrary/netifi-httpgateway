package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;

@JsonDeserialize(builder = Telemetry.Builder.class)
public class Telemetry {

  @JsonProperty("AllowedPrefixes")
  private final List<String> allowedPrefixes;

  @JsonProperty("BlockedPrefixes")
  private final List<String> blockedPrefixes;

  @JsonProperty("CirconusAPIApp")
  private final String circonusAPIApp;

  @JsonProperty("CirconusAPIToken")
  private final String circonusAPIToken;

  @JsonProperty("CirconusAPIURL")
  private final String circonusAPIURL;

  @JsonProperty("CirconusBrokerID")
  private final String circonusBrokerID;

  @JsonProperty("CirconusBrokerSelectTag")
  private final String circonusBrokerSelectTag;

  @JsonProperty("CirconusCheckDisplayName")
  private final String circonusCheckDisplayName;

  @JsonProperty("CirconusCheckForceMetricActivation")
  private final String circonusCheckForceMetricActivation;

  @JsonProperty("CirconusCheckID")
  private final String circonusCheckID;

  @JsonProperty("CirconusCheckInstanceID")
  private final String circonusCheckInstanceID;

  @JsonProperty("CirconusCheckSearchTag")
  private final String circonusCheckSearchTag;

  @JsonProperty("CirconusCheckTags")
  private final String circonusCheckTags;

  @JsonProperty("CirconusSubmissionInterval")
  private final String circonusSubmissionInterval;

  @JsonProperty("CirconusSubmissionURL")
  private final String circonusSubmissionURL;

  @JsonProperty("DisableHostname")
  private final boolean disableHostname;

  @JsonProperty("DogstatsdAddr")
  private final String dogstatsdAddr;

  @JsonProperty("DogstatsdTags")
  private final List<String> dogstatsdTags;

  @JsonProperty("FilterDefault")
  private final boolean filterDefault;

  @JsonProperty("MetricsPrefix")
  private final String metricsPrefix;

  @JsonProperty("PrometheusRetentionTime")
  private final String prometheusRetentionTime;

  @JsonProperty("StatsdAddr")
  private final String statsdAddr;

  @JsonProperty("StatsiteAddr")
  private final String statsiteAddr;

  private Telemetry(Builder builder) {
    allowedPrefixes = builder.allowedPrefixes;
    blockedPrefixes = builder.blockedPrefixes;
    circonusAPIApp = builder.circonusAPIApp;
    circonusAPIToken = builder.circonusAPIToken;
    circonusAPIURL = builder.circonusAPIURL;
    circonusBrokerID = builder.circonusBrokerID;
    circonusBrokerSelectTag = builder.circonusBrokerSelectTag;
    circonusCheckDisplayName = builder.circonusCheckDisplayName;
    circonusCheckForceMetricActivation = builder.circonusCheckForceMetricActivation;
    circonusCheckID = builder.circonusCheckID;
    circonusCheckInstanceID = builder.circonusCheckInstanceID;
    circonusCheckSearchTag = builder.circonusCheckSearchTag;
    circonusCheckTags = builder.circonusCheckTags;
    circonusSubmissionInterval = builder.circonusSubmissionInterval;
    circonusSubmissionURL = builder.circonusSubmissionURL;
    disableHostname = builder.disableHostname;
    dogstatsdAddr = builder.dogstatsdAddr;
    dogstatsdTags = builder.dogstatsdTags;
    filterDefault = builder.filterDefault;
    metricsPrefix = builder.metricsPrefix;
    prometheusRetentionTime = builder.prometheusRetentionTime;
    statsdAddr = builder.statsdAddr;
    statsiteAddr = builder.statsiteAddr;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public List<String> getAllowedPrefixes() {
    return allowedPrefixes;
  }

  public List<String> getBlockedPrefixes() {
    return blockedPrefixes;
  }

  public String getCirconusAPIApp() {
    return circonusAPIApp;
  }

  public String getCirconusAPIToken() {
    return circonusAPIToken;
  }

  public String getCirconusAPIURL() {
    return circonusAPIURL;
  }

  public String getCirconusBrokerID() {
    return circonusBrokerID;
  }

  public String getCirconusBrokerSelectTag() {
    return circonusBrokerSelectTag;
  }

  public String getCirconusCheckDisplayName() {
    return circonusCheckDisplayName;
  }

  public String getCirconusCheckForceMetricActivation() {
    return circonusCheckForceMetricActivation;
  }

  public String getCirconusCheckID() {
    return circonusCheckID;
  }

  public String getCirconusCheckInstanceID() {
    return circonusCheckInstanceID;
  }

  public String getCirconusCheckSearchTag() {
    return circonusCheckSearchTag;
  }

  public String getCirconusCheckTags() {
    return circonusCheckTags;
  }

  public String getCirconusSubmissionInterval() {
    return circonusSubmissionInterval;
  }

  public String getCirconusSubmissionURL() {
    return circonusSubmissionURL;
  }

  public boolean isDisableHostname() {
    return disableHostname;
  }

  public String getDogstatsdAddr() {
    return dogstatsdAddr;
  }

  public List<String> getDogstatsdTags() {
    return dogstatsdTags;
  }

  public boolean isFilterDefault() {
    return filterDefault;
  }

  public String getMetricsPrefix() {
    return metricsPrefix;
  }

  public String getPrometheusRetentionTime() {
    return prometheusRetentionTime;
  }

  public String getStatsdAddr() {
    return statsdAddr;
  }

  public String getStatsiteAddr() {
    return statsiteAddr;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("AllowedPrefixes")
    private List<String> allowedPrefixes;

    @JsonProperty("BlockedPrefixes")
    private List<String> blockedPrefixes;

    @JsonProperty("CirconusAPIApp")
    private String circonusAPIApp;

    @JsonProperty("CirconusAPIToken")
    private String circonusAPIToken;

    @JsonProperty("CirconusAPIURL")
    private String circonusAPIURL;

    @JsonProperty("CirconusBrokerID")
    private String circonusBrokerID;

    @JsonProperty("CirconusBrokerSelectTag")
    private String circonusBrokerSelectTag;

    @JsonProperty("CirconusCheckDisplayName")
    private String circonusCheckDisplayName;

    @JsonProperty("CirconusCheckForceMetricActivation")
    private String circonusCheckForceMetricActivation;

    @JsonProperty("CirconusCheckID")
    private String circonusCheckID;

    @JsonProperty("CirconusCheckInstanceID")
    private String circonusCheckInstanceID;

    @JsonProperty("CirconusCheckSearchTag")
    private String circonusCheckSearchTag;

    @JsonProperty("CirconusCheckTags")
    private String circonusCheckTags;

    @JsonProperty("CirconusSubmissionInterval")
    private String circonusSubmissionInterval;

    @JsonProperty("CirconusSubmissionURL")
    private String circonusSubmissionURL;

    @JsonProperty("DisableHostname")
    private boolean disableHostname;

    @JsonProperty("DogstatsdAddr")
    private String dogstatsdAddr;

    @JsonProperty("DogstatsdTags")
    private List<String> dogstatsdTags;

    @JsonProperty("FilterDefault")
    private boolean filterDefault;

    @JsonProperty("MetricsPrefix")
    private String metricsPrefix;

    @JsonProperty("PrometheusRetentionTime")
    private String prometheusRetentionTime;

    @JsonProperty("StatsdAddr")
    private String statsdAddr;

    @JsonProperty("StatsiteAddr")
    private String statsiteAddr;

    private Builder() {}

    public Builder withAllowedPrefixes(List<String> val) {
      allowedPrefixes = val;
      return this;
    }

    public Builder withBlockedPrefixes(List<String> val) {
      blockedPrefixes = val;
      return this;
    }

    public Builder withCirconusAPIApp(String val) {
      circonusAPIApp = val;
      return this;
    }

    public Builder withCirconusAPIToken(String val) {
      circonusAPIToken = val;
      return this;
    }

    public Builder withCirconusAPIURL(String val) {
      circonusAPIURL = val;
      return this;
    }

    public Builder withCirconusBrokerID(String val) {
      circonusBrokerID = val;
      return this;
    }

    public Builder withCirconusBrokerSelectTag(String val) {
      circonusBrokerSelectTag = val;
      return this;
    }

    public Builder withCirconusCheckDisplayName(String val) {
      circonusCheckDisplayName = val;
      return this;
    }

    public Builder withCirconusCheckForceMetricActivation(String val) {
      circonusCheckForceMetricActivation = val;
      return this;
    }

    public Builder withCirconusCheckID(String val) {
      circonusCheckID = val;
      return this;
    }

    public Builder withCirconusCheckInstanceID(String val) {
      circonusCheckInstanceID = val;
      return this;
    }

    public Builder withCirconusCheckSearchTag(String val) {
      circonusCheckSearchTag = val;
      return this;
    }

    public Builder withCirconusCheckTags(String val) {
      circonusCheckTags = val;
      return this;
    }

    public Builder withCirconusSubmissionInterval(String val) {
      circonusSubmissionInterval = val;
      return this;
    }

    public Builder withCirconusSubmissionURL(String val) {
      circonusSubmissionURL = val;
      return this;
    }

    public Builder withDisableHostname(boolean val) {
      disableHostname = val;
      return this;
    }

    public Builder withDogstatsdAddr(String val) {
      dogstatsdAddr = val;
      return this;
    }

    public Builder withDogstatsdTags(List<String> val) {
      dogstatsdTags = val;
      return this;
    }

    public Builder withFilterDefault(boolean val) {
      filterDefault = val;
      return this;
    }

    public Builder withMetricsPrefix(String val) {
      metricsPrefix = val;
      return this;
    }

    public Builder withPrometheusRetentionTime(String val) {
      prometheusRetentionTime = val;
      return this;
    }

    public Builder withStatsdAddr(String val) {
      statsdAddr = val;
      return this;
    }

    public Builder withStatsiteAddr(String val) {
      statsiteAddr = val;
      return this;
    }

    public Telemetry build() {
      return new Telemetry(this);
    }
  }
}
