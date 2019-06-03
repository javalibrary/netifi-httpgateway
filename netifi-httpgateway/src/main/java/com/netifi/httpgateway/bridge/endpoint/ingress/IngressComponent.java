package com.netifi.httpgateway.bridge.endpoint.ingress;

import com.netifi.broker.BrokerClient;
import com.netifi.broker.info.BrokerInfoServiceClient;
import com.netifi.broker.rsocket.BrokerSocket;
import com.netifi.common.tags.Tags;
import com.netifi.httpgateway.bridge.endpoint.SslContextFactory;
import com.netifi.httpgateway.util.Constants;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

@Component
public class IngressComponent {
  private DefaultIngressGroupManager groupManager;
  private PortManager portManager;
  private SslContextFactory sslContextFactory;
  private IngressDiscoveryRegister ingressDiscoveryRegister;

  @Autowired
  public IngressComponent(
      BrokerClient brokerClient,
      MeterRegistry registry,
      IngressDiscoveryRegister ingressDiscoveryRegister) {
    this.portManager = new PortManager(Constants.DEFAULT_LOW_PORT, Constants.DEFAULT_HIGH_PORT);
    BrokerSocket rSocket =
        brokerClient.groupServiceSocket("com.netifi.broker.brokerServices", Tags.empty());

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
            null,
            ingressDiscoveryRegister);
  }
}
