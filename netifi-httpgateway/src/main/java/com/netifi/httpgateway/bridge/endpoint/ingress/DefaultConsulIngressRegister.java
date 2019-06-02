package com.netifi.httpgateway.bridge.endpoint.ingress;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;
import java.time.Duration;
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

public class DefaultConsulIngressRegister implements IngressDiscoveryRegister {

  private static final int DEFAULT_TTL_PING = 3;
  private static final int DEFAULT_TTL_REGISTER = 5;
  private Logger logger = LogManager.getLogger(DefaultConsulIngressRegister.class);

  private AgentClient agentClient;
  private String serviceId;
  private String serviceName;
  private int servicePort;
  private Disposable registerFlux;


  public DefaultConsulIngressRegister(String consulHost, int consulPort, String consulToken, String serviceId, String serviceName, int servicePort) {
    Consul.Builder consulBuilder = Consul.builder().withHostAndPort(HostAndPort.fromParts(consulHost, consulPort));

    if (consulToken != null && !consulToken.equals("")) {
      consulBuilder.withAclToken(consulToken);
    }

    this.agentClient = consulBuilder.build().agentClient();
    this.serviceId = serviceId;
    this.serviceName = serviceName;
    this.servicePort = servicePort;
  }

  @Override
  public void serviceRegister() {
    logger.debug("serviceRegister: "+serviceId);
     agentClient.register(servicePort, DEFAULT_TTL_REGISTER, serviceName, serviceId,
         Collections.singletonList("netifi-httpgateawy"), null);
    try {
      agentClient.pass(serviceId);
    } catch (NotRegisteredException e) {
      logger.error("we apparently didn't register even though we just tried("+ serviceId +"): "  + e.getMessage());
    }
    createPassFlux();
  }

  private void createPassFlux() {
    logger.debug("createPassFlux: "+serviceId);
    disposeOfRegisterFlux();
    this.registerFlux = Flux.interval(Duration.ofSeconds(DEFAULT_TTL_PING)).subscribe(c -> {
      try {
        agentClient.pass(serviceId);
      } catch (NotRegisteredException e) {
        logger.error("apparently we still never registered, so I'm going to try again("+ serviceId +"): "  + e.getMessage());
        agentClient.register(servicePort, 5, serviceName, serviceId,
            Collections.singletonList("netifi-httpgateway"), null);
        try {
          agentClient.pass(serviceId);
        } catch (NotRegisteredException ex) {
          logger.error("everything is miserable - we apparently need some retry code ("+ serviceId +"): "  + e.getMessage());
        }
      }
    });
  }

  @Override
  public void serviceDeregister() {
    logger.debug("serviceDeregister: "+serviceId);
    disposeOfRegisterFlux();
    agentClient.deregister(serviceId);
  }

  @Override
  public void failService() {
    logger.debug("failService: "+serviceId);
    disposeOfRegisterFlux();
    try {
      agentClient.fail(serviceId);
    } catch (NotRegisteredException e) {
      logger.error("could not fail service(" + serviceId + "): " + e.getMessage());
    }
  }

  @Override
  public void passService() {
    logger.debug("passService: "+serviceId);
    createPassFlux();
  }

  @Override
  public void dispose() {
    logger.debug("dispose: "+serviceId);
    serviceDeregister();
    disposeOfRegisterFlux();
  }

  private void disposeOfRegisterFlux() {
    logger.debug("disposeOfRegisterFlux: "+serviceId);
    if (registerFlux != null && !registerFlux.isDisposed()) {
      registerFlux.dispose();
    }
  }


}
