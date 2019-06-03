package com.netifi.httpgateway.bridge.endpoint;

import com.netifi.common.tags.Tags;
import com.netifi.httpgateway.util.Constants;
import com.netifi.spring.core.BrokerClientTagSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BridgeBrokerClientTagSupplier implements BrokerClientTagSupplier {
  private final String group;

  @Autowired
  public BridgeBrokerClientTagSupplier(@Value("${netifi.client.group}") String group) {
    this.group = group;
  }

  @Override
  public Tags get() {
    return Tags.of(Constants.HTTP_GATEWAY_KEY, group);
  }
}
