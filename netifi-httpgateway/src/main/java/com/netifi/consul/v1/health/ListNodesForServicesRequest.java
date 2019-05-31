package com.netifi.consul.v1.health;

import com.netifi.consul.v1.ConsulRequest;
import com.netifi.consul.v1.NodeMetaParameters;
import com.netifi.consul.v1.QueryParams;
import com.netifi.consul.v1.SingleUrlParameters;
import com.netifi.consul.v1.TagsParameters;
import com.netifi.consul.v1.UrlParameters;
import com.netifi.consul.v1.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ListNodesForServicesRequest implements ConsulRequest {

  private final String DEFAULT_URI_PATH = "/v1/health/service/%s";
  private final String serviceName;
  private final String datacenter;
  private final String near;
  private final String[] tags;
  private final Map<String, String> nodeMeta;
  private final boolean passing;
  private final QueryParams queryParams;
  private final String filter;

  private ListNodesForServicesRequest(
      String serviceName,
      String datacenter,
      String near,
      String[] tags,
      Map<String, String> nodeMeta,
      boolean passing,
      QueryParams queryParams,
      String filter) {
    this.serviceName = serviceName;
    this.datacenter = datacenter;
    this.near = near;
    this.tags = tags;
    this.nodeMeta = nodeMeta;
    this.passing = passing;
    this.queryParams = queryParams;
    this.filter = filter;
  }

  public String getServiceName() {
    return this.serviceName;
  }

  public String getDatacenter() {
    return this.datacenter;
  }

  public String getNear() {
    return this.near;
  }

  public String getTag() {
    return this.tags != null && this.tags.length > 0 ? this.tags[0] : null;
  }

  public String[] getTags() {
    return this.tags;
  }

  public Map<String, String> getNodeMeta() {
    return this.nodeMeta;
  }

  public boolean isPassing() {
    return this.passing;
  }

  public QueryParams getQueryParams() {
    return this.queryParams;
  }

  public String getFilter() {
    return this.filter;
  }

  @Override
  public String toURIString() {
    Objects.requireNonNull(this.serviceName, "serviceName must be set");
    String urlPath = String.format(DEFAULT_URI_PATH, this.serviceName);
    List<UrlParameters> params = new ArrayList<>();

    if (datacenter != null) {
      params.add(new SingleUrlParameters("dc", datacenter));
    }

    if (near != null) {
      params.add(new SingleUrlParameters("near", near));
    }

    if (tags != null) {
      params.add(new TagsParameters(tags));
    }

    if (nodeMeta != null) {
      params.add(new NodeMetaParameters(nodeMeta));
    }

    params.add(new SingleUrlParameters("passing", String.valueOf(passing)));

    if (queryParams != null) {
      params.add(queryParams);
    }
    params.add(new SingleUrlParameters("filter", this.filter));

    return Utils.generateUrl(urlPath, params);
  }

  public static class Builder {
    private String serviceName;
    private String datacenter;
    private String near;
    private String[] tags;
    private Map<String, String> nodeMeta;
    private boolean passing;
    private QueryParams queryParams;
    private String filter;

    private Builder() {}

    public Builder setServiceName(String serviceName) {
      this.serviceName = serviceName;
      return this;
    }

    public Builder setDatacenter(String datacenter) {
      this.datacenter = datacenter;
      return this;
    }

    public Builder setNear(String near) {
      this.near = near;
      return this;
    }

    public Builder setTag(String tag) {
      this.tags = new String[] {tag};
      return this;
    }

    public Builder setTags(String[] tags) {
      this.tags = tags;
      return this;
    }

    public Builder setNodeMeta(Map<String, String> nodeMeta) {
      this.nodeMeta = nodeMeta != null ? Collections.unmodifiableMap(nodeMeta) : null;
      return this;
    }

    public Builder setPassing(boolean passing) {
      this.passing = passing;
      return this;
    }

    public Builder setQueryParams(QueryParams queryParams) {
      this.queryParams = queryParams;
      return this;
    }

    public Builder setFilter(String filter) {
      this.filter = filter;
      return this;
    }

    public ListNodesForServicesRequest build() {
      return new ListNodesForServicesRequest(
          serviceName, datacenter, near, tags, nodeMeta, passing, queryParams, filter);
    }
  }

  public static Builder newBuilder() {
    return new Builder();
  }
}
