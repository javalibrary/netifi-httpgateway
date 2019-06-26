package com.netifi.httpgateway;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.Exceptions;

@Component
public class ConsulClientProvider {
  private final Logger logger = LogManager.getLogger(ConsulClientProvider.class);
  private final String host;
  private final int port;

  private Consul consul;
  private ExecutorService consulExecutorService;

  @Autowired
  public ConsulClientProvider(
      @Value("${netifi.httpgateway.consul.host}") String host,
      @Value("${netifi.httpgateway.consul.port}") int port) {
    this.host = host;
    this.port = port;
    this.consulExecutorService =
        Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "consul-thread"));
  }

  @Bean
  public ConsulSupplier consul() {
    return this::getConsul;
  }

  private synchronized Consul getConsul() {
    try {
      if (consul == null) {
        HostAndPort hostAndPort = HostAndPort.fromParts(host, port);
        this.consul =
            Consul.builder()
                .withExecutorService(consulExecutorService)
                .withHostAndPort(hostAndPort)
                .build();
      }
      return consul;
    } catch (Throwable t) {
      logger.error("error getting consul client", t);
      consul = null;
      throw Exceptions.propagate(t);
    }
  }

  @FunctionalInterface
  public interface ConsulSupplier extends Supplier<Consul> {}
}
