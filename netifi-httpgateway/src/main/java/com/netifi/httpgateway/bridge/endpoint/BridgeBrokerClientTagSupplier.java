package com.netifi.httpgateway.bridge.endpoint;

import com.netifi.common.tags.Tags;
import com.netifi.httpgateway.util.Constants;
import com.netifi.spring.core.BrokerClientTagSupplier;
import org.springframework.stereotype.Component;

@Component
public class BridgeBrokerClientTagSupplier implements BrokerClientTagSupplier {

  @Override
  public Tags get() {
    return Tags.of(Constants.HTTP_GATEWAY_KEY, Constants.HTTP_GATEWAY_VALUE);
  }
}
