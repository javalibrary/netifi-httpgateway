package com.netifi.httpgateway.bridge.endpoint.egress.lb;

import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpoint;
import io.rsocket.Closeable;
import reactor.core.Disposable;

public interface EgressEndpointLoadBalancer extends Closeable, Disposable {
  /**
   * Selects a {@link EgressEndpoint} in a load balanced manager
   *
   * @return the selected endpoint
   */
  EgressEndpoint select();
  
}
