package com.netifi.httpgateway.bridge.endpoint.ingress;

import com.netifi.broker.BrokerClient;
import com.netifi.broker.info.BrokerInfoServiceClient;
import com.netifi.broker.rsocket.BrokerSocket;
import com.netifi.common.tags.Tags;
import com.netifi.httpgateway.bridge.endpoint.SslContextFactory;
import com.netifi.httpgateway.util.Constants;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

@Component
public class IngressComponent {
  private final DefaultIngressGroupManager groupManager;
  private final PortManager portManager;
  private final SslContextFactory sslContextFactory;
  private final IngressDiscoveryRegister ingressDiscoveryRegister;

  @Autowired
  public IngressComponent(
      BrokerClient brokerClient,
      MeterRegistry registry,
      SslContextFactory sslContextFactory,
      IngressDiscoveryRegister ingressDiscoveryRegister,
      @Value("${netifi.client.ssl.isDisabled}") boolean sslDisabled) {

    this.sslContextFactory = sslContextFactory;
    this.ingressDiscoveryRegister = ingressDiscoveryRegister;
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
            sslDisabled,
            registry,
            null,
            ingressDiscoveryRegister);
  }
}
