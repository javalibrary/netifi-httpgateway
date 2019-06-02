package com.netifi.httpgateway;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ConsulClientProvider {
  private final Consul consul;

  private ExecutorService consulExecutorService;

  @Autowired
  public ConsulClientProvider(
      @Value("netifi.httpgateway.consul.host") String host,
      @Value("netifi.httpgateway.consul.port") int port) {
    this.consulExecutorService =
        Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "consul-thread"));
    HostAndPort hostAndPort = HostAndPort.fromParts(host, port);
    this.consul =
        Consul.builder()
            .withExecutorService(consulExecutorService)
            .withHostAndPort(hostAndPort)
            .build();
  }

  @Bean
  public Consul consul() {
    return consul;
  }
}
