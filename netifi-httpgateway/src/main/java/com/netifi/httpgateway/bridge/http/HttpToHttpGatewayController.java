package com.netifi.httpgateway.bridge.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpToHttpGatewayController {
  private final int lowPort;
  private final int highPort;

  public HttpToHttpGatewayController(
      @Value("${netifi.client.gateway.lowPort}") int lowPort,
      @Value("${netifi.client.gateway.highPort}") int highPort) {
    this.lowPort = lowPort;
    this.highPort = highPort;
  }

  public void run(String... args) throws Exception {}
}
