package com.netifi.httpgateway;

import com.netifi.httpgateway.bridge.http.HttpToHttpGatewayController;
import com.netifi.httpgateway.rsocket.http.HttpToRSocketGatewayController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GatewayCommandLineRunner implements CommandLineRunner {
  private final HttpToRSocketGatewayController httpToRSocketGatewayController;
  private final HttpToHttpGatewayController httpToHttpGatewayController;

  @Autowired
  public GatewayCommandLineRunner(
      HttpToRSocketGatewayController httpToRSocketGatewayController,
      HttpToHttpGatewayController httpToHttpGatewayController) {
    this.httpToRSocketGatewayController = httpToRSocketGatewayController;
    this.httpToHttpGatewayController = httpToHttpGatewayController;
  }

  @Override
  public void run(String... args) throws Exception {
    httpToRSocketGatewayController.run(args);
    httpToHttpGatewayController.run(args);
  }
}
