package com.netifi.consul.v1.catalog;

import com.netifi.consul.v1.ConsulRequest;
import com.netifi.consul.v1.NodeMetaParameters;
import com.netifi.consul.v1.SingleUrlParameters;
import com.netifi.consul.v1.UrlParameters;
import com.netifi.consul.v1.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class CatalogServicesRequest implements ConsulRequest {

  private final String DEFAULT_URI_PATH = "/v1/catalog/services";
  private final String datacenter;
  private final Map<String, String> nodeMeta;

  public CatalogServicesRequest(String datacenter, Map<String, String> nodeMeta) {
    this.datacenter = datacenter;
    this.nodeMeta = nodeMeta;
  }

  public String getDatacenter() {
    return datacenter;
  }

  public Map<String, String> getNodeMeta() {
    return nodeMeta;
  }

  public static class Builder {
    private String datacenter;
    private Map<String, String> nodeMeta;

    private Builder() {}

    public Builder withDatacenter(String datacenter) {
      this.datacenter = datacenter;
      return this;
    }

    public Builder withNodeMeta(Map<String, String> nodeMeta) {
      if (nodeMeta == null) {
        this.nodeMeta = null;
      } else {
        this.nodeMeta = Collections.unmodifiableMap(nodeMeta);
      }

      return this;
    }

    public CatalogServicesRequest build() {
      return new CatalogServicesRequest(datacenter, nodeMeta);
    }
  }

  public static Builder newBuilder() {
    return new CatalogServicesRequest.Builder();
  }

  @Override
  public String toURIString() {
    List<UrlParameters> params = new ArrayList<>();

    if (datacenter != null) {
      params.add(new SingleUrlParameters("dc", datacenter));
    }

    if (nodeMeta != null) {
      params.add(new NodeMetaParameters(nodeMeta));
    }

    return Utils.generateUrl(DEFAULT_URI_PATH, params);
  }
}
