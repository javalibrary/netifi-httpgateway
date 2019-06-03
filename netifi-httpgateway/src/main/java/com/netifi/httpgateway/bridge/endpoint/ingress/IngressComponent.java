package com.netifi.httpgateway.bridge.endpoint.ingress;

import com.netifi.broker.BrokerClient;
import com.netifi.broker.info.BrokerInfoServiceClient;
import com.netifi.broker.rsocket.BrokerSocket;
import com.netifi.common.tags.Tags;
import com.netifi.httpgateway.bridge.endpoint.SslContextFactory;
import com.netifi.httpgateway.config.BrokerClientSettings;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

@Component
public class IngressComponent {
  private DefaultIngressGroupManager groupManager;
  private PortManager portManager;
  private SslContextFactory sslContextFactory;

  public IngressComponent(BrokerClient brokerClient, MeterRegistry registry) {
    this.portManager =
        new PortManager(
            BrokerClientSettings.DEFAULT_LOW_PORT, BrokerClientSettings.DEFAULT_HIGH_PORT);
    BrokerSocket rSocket = brokerClient.groupServiceSocket("", Tags.empty());

    BrokerInfoServiceClient brokerInfoServiceClient =
        new BrokerInfoServiceClient(rSocket, registry);

    groupManager =
        new DefaultIngressGroupManager(
            brokerInfoServiceClient,
            Schedulers.single(),
            brokerClient,
            portManager,
            sslContextFactory,
            false,
            registry,
            null);
  }
}
