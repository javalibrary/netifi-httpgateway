package com.netifi.httpgateway.bridge.endpoint.egress;

import com.netifi.broker.BrokerClient;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.EgressEndpointLoadBalancerFactory;
import com.netifi.httpgateway.util.Constants;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EgressComponent {
  private final MeterRegistry registry;
  private final BrokerClient brokerClient;
  private final EgressEndpointLoadBalancerFactory loadBalancerFactory;

  @Autowired
  public EgressComponent(
      ServiceEventsSupplier eventsSupplier,
      EgressEndpointLoadBalancerFactory loadBalancerFactory,
      MeterRegistry registry,
      BrokerClient brokerClient) {
    this.registry = registry;
    this.brokerClient = brokerClient;
    this.loadBalancerFactory = loadBalancerFactory;

    ServiceManagerRSocket serviceManagerRSocket =
        new ServiceManagerRSocket(eventsSupplier, loadBalancerFactory, brokerClient, registry);

    brokerClient.addNamedRSocket(Constants.HTTP_BRIDGE_NAMED_SOCKET_NAME, serviceManagerRSocket);
  }
}
