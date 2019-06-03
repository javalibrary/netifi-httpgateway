package com.netifi.httpgateway.bridge.endpoint.ingress;

import com.netifi.httpgateway.ConsulClientProvider;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegCheck;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.retry.Retry;

@Component
public class ConsulIngressDiscoveryRegister implements IngressDiscoveryRegister {
  private static final Logger logger = LogManager.getLogger(ConsulIngressDiscoveryRegister.class);
  private static final Duration DEFAULT_TTL_PING = Duration.ofSeconds(3);
  private static final String DEFAULT_TTL_REGISTER = "5s";
  private final Supplier<Consul> consulSupplier;
  private final String address;
  private final Scheduler scheduler;

  @Autowired
  public ConsulIngressDiscoveryRegister(
      @Value("${netifi.httpgateway.publicIngressAddress}") String address,
      ConsulClientProvider.ConsulSupplier consulSupplier) {
    this.address = address;
    this.consulSupplier = consulSupplier;
    this.scheduler = Schedulers.newSingle("consul-ingress-discovery-register");
  }

  @Override
  public Disposable register(String serviceId, int port) {
    return new InnerDisposable(serviceId, port);
  }

  private class InnerDisposable extends AtomicBoolean implements Disposable {
    private final String uniqueServiceId;
    private final Disposable disposable;

    InnerDisposable(String serviceId, int port) {
      this.uniqueServiceId = serviceId + "-" + ThreadLocalRandom.current().nextLong();
      this.disposable =
          Flux.defer(
                  () -> {
                    final Consul consul = consulSupplier.get();
                    final AgentClient agentClient = consul.agentClient();
                    final Registration registration =
                        ImmutableRegistration.builder()
                            .id(uniqueServiceId)
                            .name(serviceId)
                            .tags(Collections.singletonList("netifi-httpgateawy"))
                            .address(address)
                            .port(port)
                            .check(ImmutableRegCheck.builder().ttl(DEFAULT_TTL_REGISTER).build())
                            .build();

                    agentClient.register(registration);

                    return Flux.interval(DEFAULT_TTL_PING, scheduler)
                        .doOnNext(
                            l -> {
                              try {
                                agentClient.pass(uniqueServiceId);
                              } catch (Throwable t) {
                                logger.error("error passing consul registration", t);
                              }
                            })
                        .doFinally(
                            s -> {
                              try {
                                agentClient.fail(uniqueServiceId);
                              } catch (Throwable t) {
                                logger.error("error failing consul registration", t);
                              }
                            });
                  })
              .doOnError(t -> logger.error("error registering ingress with consul", t))
              .retryWhen(
                  Retry.any()
                      .exponentialBackoffWithJitter(Duration.ofSeconds(1), Duration.ofSeconds(30)))
              .subscribe();
    }

    @Override
    public void dispose() {
      if (compareAndSet(false, true)) {
        disposable.dispose();
      }
    }

    @Override
    public boolean isDisposed() {
      return get();
    }
  }
}
