package com.netifi.httpgateway.bridge.endpoint.ingress;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.model.agent.ImmutableRegCheck;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import java.time.Duration;
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

public class DefaultConsulIngressRegister implements IngressDiscoveryRegister {

  private static final int DEFAULT_TTL_PING = 3;
  private static final String DEFAULT_TTL_REGISTER = "5s";
  private final AgentClient agentClient;
  private final String serviceId;
  private final String serviceName;
  private final String serviceAddress;
  private final int servicePort;
  private Logger logger = LogManager.getLogger(DefaultConsulIngressRegister.class);
  private Disposable passCheckFlux;

  public DefaultConsulIngressRegister(
      String consulHost,
      int consulPort,
      String consulToken,
      String serviceId,
      String serviceName,
      String serviceAddress,
      int servicePort) {
    Consul.Builder consulBuilder =
        Consul.builder().withHostAndPort(HostAndPort.fromParts(consulHost, consulPort));

    if (consulToken != null && !consulToken.equals("")) {
      consulBuilder.withAclToken(consulToken);
    }

    this.agentClient = consulBuilder.build().agentClient();
    this.serviceId = serviceId;
    this.serviceName = serviceName;
    this.serviceAddress = serviceAddress;
    this.servicePort = servicePort;
  }

  @Override
  public void serviceRegister() {
    logger.debug("serviceRegister: " + serviceId);
    registerService();
    createPassFlux();
  }

  private void createPassFlux() {
    logger.debug("createPassFlux: " + serviceId);
    disposeOfPassCheckFlux();
    passCheck();
    this.passCheckFlux =
        Flux.interval(Duration.ofSeconds(DEFAULT_TTL_PING)).subscribe(c -> passCheck());
  }

  @Override
  public void serviceDeregister() {
    logger.debug("serviceDeregister: " + serviceId);
    disposeOfPassCheckFlux();
    agentClient.deregister(serviceId);
  }

  @Override
  public void failService() {
    logger.debug("failService: " + serviceId);
    disposeOfPassCheckFlux();
    failCheck();
  }

  @Override
  public void passService() {
    logger.debug("passService: " + serviceId);
    createPassFlux();
  }

  private void disposeOfPassCheckFlux() {
    logger.debug("disposeOfPassCheckFlux: " + serviceId);
    if (passCheckFlux != null && !passCheckFlux.isDisposed()) {
      passCheckFlux.dispose();
    }
  }

  private void registerService() {
    Registration registration =
        ImmutableRegistration.builder()
            .id(serviceId)
            .name(serviceName)
            .tags(Collections.singletonList("netifi-httpgateawy"))
            .address(serviceAddress)
            .port(servicePort)
            .check(ImmutableRegCheck.builder().ttl(DEFAULT_TTL_REGISTER).build())
            .build();
    agentClient.register(registration);
  }

  private void passCheck() {
    try {
      agentClient.pass(serviceId);
    } catch (NotRegisteredException e) {
      logger.error(
          "while trying to pass a check we apparently didn't register "
              + serviceId
              + " : "
              + e.getMessage());
      logger.error("we will try registering 1 more time");
      registerService();
    }
  }

  private void failCheck() {
    try {
      agentClient.fail(serviceId);
    } catch (NotRegisteredException e) {
      logger.error(
          "while trying to fail a check we apparently didn't register "
              + serviceId
              + " : "
              + e.getMessage());
    }
  }
}
