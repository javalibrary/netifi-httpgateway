package com.netifi.httpgateway.rsocket.http;

import com.google.protobuf.Empty;
import com.netifi.broker.BrokerClient;
import com.netifi.demo.helloworld.HelloWorldServiceServer;
import com.netifi.httpgateway.config.BrokerClientSettings;
import com.netifi.httpgateway.rsocket.endpoint.factory.DefaultEndpointFactory;
import com.netifi.httpgateway.rsocket.endpoint.factory.EndpointFactory;
import com.netifi.httpgateway.rsocket.endpoint.registry.DefaultEndpointRegistry;
import com.netifi.httpgateway.rsocket.endpoint.registry.EndpointRegistry;
import com.netifi.httpgateway.endpoint.source.EndpointSource;
import com.netifi.httpgateway.endpoint.source.ProtoDescriptor;
import com.netifi.httpgateway.rsocket.BrokerClientRSocketSupplier;
import com.netifi.httpgateway.rsocket.RSocketSupplier;
import io.netty.buffer.ByteBuf;
import org.junit.Ignore;
import org.junit.rules.ExternalResource;
import reactor.core.publisher.DirectProcessor;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Ignore
public class HttpGatewayControllerRule extends ExternalResource {
  private static final long ACCESS_KEY = 9007199254740991L;
  private static final String ACCESS_TOKEN = "kTBDVtfRBO4tHOnZzSyY5ym2kfY=";

  private final HttpToRSocketGatewayController httpToRSocketGatewayController;
  private final DirectProcessor<ProtoDescriptor> processor;
  private final int bindPort;

  public HttpGatewayControllerRule() {
    this.bindPort = ThreadLocalRandom.current().nextInt(30_001, 40_000);
    this.processor = DirectProcessor.create();
    BrokerClientSettings brokerClientSettings = new BrokerClientSettings();
    brokerClientSettings.setAccessKey(ACCESS_KEY);
    brokerClientSettings.setAccessToken(ACCESS_TOKEN);
    brokerClientSettings.setSslDisabled(true);
    brokerClientSettings.setGroup("netifiGatewayTestGroup");
    brokerClientSettings.setBrokerHostname("localhost");
    brokerClientSettings.setBrokerPort(8001);
    RSocketSupplier rSocketSupplier = new BrokerClientRSocketSupplier(brokerClientSettings);
    EndpointSource endpointSource = (Empty message, ByteBuf metadata) -> processor;
    EndpointFactory endpointFactory = new DefaultEndpointFactory(endpointSource, rSocketSupplier);
    EndpointRegistry endpointRegistry = new DefaultEndpointRegistry(endpointFactory);

    this.httpToRSocketGatewayController = new HttpToRSocketGatewayController("0.0.0.0", bindPort, endpointRegistry);
  }

  @Override
  protected void before() throws Throwable {
    httpToRSocketGatewayController.run((String[]) null);

    Thread.sleep(1_000);

    BrokerClient brokerClient =
        BrokerClient.tcp()
            .group("helloGroup")
            .destination("test_destination")
            .accessKey(ACCESS_KEY)
            .accessToken(ACCESS_TOKEN)
            .disableSsl()
            .host("0.0.0.0")
            .port(8001)
            .build();

    brokerClient.addService(
        new HelloWorldServiceServer(
            new HelloWorldServiceImpl(), Optional.empty(), Optional.empty()));
  }

  public int getBindPort() {
    return bindPort;
  }

  public void emitProtoDescriptior(ProtoDescriptor descriptor) {
    processor.onNext(descriptor);
  }
}
