package com.netifi.httpgateway.bridge.endpoint.ingress;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegCheck;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.retry.Retry;

@Component
public class ConsulIngressDiscoveryRegister implements IngressDiscoveryRegister {
  private static final Logger logger = LogManager.getLogger(ConsulIngressDiscoveryRegister.class);
  private static final Duration DEFAULT_TTL_PING = Duration.ofSeconds(3);
  private static final String DEFAULT_TTL_REGISTER = "5s";
  private final Consul consul;
  private final String address;

  @Autowired
  public ConsulIngressDiscoveryRegister(
      @Value("${netifi.httpgateway.publicIngressAddress}") String address, Consul consul) {
    this.address = address;
    this.consul = consul;
  }

  @Override
  public Disposable register(String serviceId, int port) {
    return new InnerDisposable(serviceId, port);
  }

  private class InnerDisposable extends AtomicBoolean implements Disposable {
    private final AgentClient agentClient;
    private final String uniqueServiceId;
    private final Disposable disposable;

    InnerDisposable(String serviceId, int port) {
      this.agentClient = consul.agentClient();
      this.uniqueServiceId = serviceId + "-" + ThreadLocalRandom.current().nextLong();

      Registration registration =
          ImmutableRegistration.builder()
              .id(uniqueServiceId)
              .name(serviceId)
              .tags(Collections.singletonList("netifi-httpgateawy"))
              .address(address)
              .port(port)
              .check(ImmutableRegCheck.builder().ttl(DEFAULT_TTL_REGISTER).build())
              .build();

      agentClient.register(registration);

      this.disposable =
          Flux.interval(DEFAULT_TTL_PING)
              .doOnNext(
                  l -> {
                    try {
                      agentClient.pass(serviceId);
                    } catch (Throwable t) {
                      logger.error("error passing registration", t);
                    }
                  })
              .retryWhen(
                  Retry.any()
                      .exponentialBackoffWithJitter(Duration.ofSeconds(1), Duration.ofSeconds(30)))
              .subscribe();
    }

    @Override
    public void dispose() {
      if (compareAndSet(false, true)) {
        try {
          agentClient.fail(uniqueServiceId);
        } catch (Throwable t) {
          logger.error("error failing registration", t);
        }
        disposable.dispose();
      }
    }

    @Override
    public boolean isDisposed() {
      return get();
    }
  }
}
