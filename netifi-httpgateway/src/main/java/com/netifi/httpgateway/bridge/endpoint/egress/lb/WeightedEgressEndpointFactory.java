package com.netifi.httpgateway.bridge.endpoint.egress.lb;

import com.netifi.common.stats.Quantile;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactory;
import io.micrometer.core.instrument.MeterRegistry;
import io.netty.handler.ssl.SslContext;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import reactor.core.publisher.MonoProcessor;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

public class WeightedEgressEndpointFactory
    implements EgressEndpointFactory<WeightedEgressEndpoint> {
  private final String egressEndpointId;
  private final AtomicBoolean disposed = new AtomicBoolean();
  private final String host;
  private final int port;
  private final SslContext sslContext;
  private final boolean disableSSL;
  private final Quantile lowerQuantile;
  private final Quantile higherQuantile;
  private final String serviceName;
  private final MeterRegistry registry;
  private WeightedEgressEndpoint weightedEgressEndpoint;

  public WeightedEgressEndpointFactory(
      String serviceName,
      String egressEndpointId,
      String host,
      int port,
      SslContext sslContext,
      boolean disableSSL,
      Quantile lowerQuantile,
      Quantile higherQuantile,
      MeterRegistry registry) {
    this.egressEndpointId = egressEndpointId;
    this.host = host;
    this.port = port;
    this.sslContext = sslContext;
    this.disableSSL = disableSSL;
    this.lowerQuantile = lowerQuantile;
    this.higherQuantile = higherQuantile;
    this.serviceName = serviceName;
    this.registry = registry;
  }

  @Override
  public synchronized WeightedEgressEndpoint get() {
    if (weightedEgressEndpoint == null) {
      weightedEgressEndpoint = createEndpoint();
    }
    return weightedEgressEndpoint;
  }

  private synchronized WeightedEgressEndpoint createEndpoint() {
    MonoProcessor<Void> onConnectionClose = MonoProcessor.create();
    TcpClient tcpClient = TcpClient.create().host(host).port(port);

    if (!disableSSL) {
      tcpClient = tcpClient.secure(sslContext);
    }

    WeightedEgressEndpoint weightedEgressEndpoint =
        new WeightedEgressEndpoint(
            serviceName,
            egressEndpointId,
            host,
            port,
            HttpClient.from(tcpClient),
            onConnectionClose,
            lowerQuantile,
            higherQuantile,
            registry);

    this.weightedEgressEndpoint = weightedEgressEndpoint;

    return weightedEgressEndpoint;
  }

  @Override
  public void dispose() {
    if (disposed.compareAndSet(false, true)) {
      WeightedEgressEndpoint weightedEgressEndpoint;
      synchronized (this) {
        weightedEgressEndpoint = this.weightedEgressEndpoint;
      }
      if (weightedEgressEndpoint != null) {
        weightedEgressEndpoint.dispose();
      }
    }
  }

  @Override
  public boolean isDisposed() {
    return disposed.get();
  }

  @Override
  public String getEgressEndpointId() {
    return egressEndpointId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WeightedEgressEndpointFactory that = (WeightedEgressEndpointFactory) o;
    return egressEndpointId.equals(that.egressEndpointId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(egressEndpointId);
  }
}
