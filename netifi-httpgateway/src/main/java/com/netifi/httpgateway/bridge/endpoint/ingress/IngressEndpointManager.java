package com.netifi.httpgateway.bridge.endpoint.ingress;

import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.rsocket.RSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class IngressEndpointManager {
  private static final Object PRESENT = new Object();

  private final Logger logger = LogManager.getLogger(IngressEndpoint.class);

  private final ConcurrentHashMap<String, IngressEndpoint> ingressEndpoints;

  private final IntObjectMap<Object> reservedPorts;

  private final SslContextFactory sslContextFactory;

  private final boolean disableSsl;

  private final int lowPort;

  private final int hightPort;

  public IngressEndpointManager(
      SslContextFactory sslContextFactory, boolean disableSSL, int lowPort, int highPort) {
    this.ingressEndpoints = new ConcurrentHashMap<>();
    this.sslContextFactory = sslContextFactory;
    this.disableSsl = disableSSL;
    this.lowPort = lowPort;
    this.hightPort = highPort;
    this.reservedPorts = new IntObjectHashMap<>();
  }

  public void register(String serviceName, RSocket target) {
    ingressEndpoints.compute(
        serviceName,
        (s, ingressEndpoint) -> {
          if (ingressEndpoint != null) {
            logger.warn("can't add service named {}, already present", serviceName);
            return ingressEndpoint;
          } else {
            int port = reservePort(serviceName);

            IngressEndpoint endpoint =
                new DefaultIngressEndpoint(
                    sslContextFactory, serviceName, disableSsl, port, target);

            endpoint.onClose().doFinally(signalType -> releasePort(port)).subscribe();

            logger.info(
                "adding endpoint for service named {} on port {} - ssl disabled = {}",
                serviceName,
                port,
                disableSsl);

            return endpoint;
          }
        });
  }

  public void unregister(String serviceName) {
    IngressEndpoint ingressEndpoint = ingressEndpoints.remove(serviceName);
    if (ingressEndpoint != null) {
      logger.info("remove ingress endpoint for service name {}", serviceName);
      ingressEndpoint.dispose();
    }
  }

  private synchronized int reservePort(String serviceName) {
    int h = serviceName.hashCode();

    int pi = (h % (hightPort - lowPort)) + lowPort;

    while (reservedPorts.containsKey(pi)) {
      pi++;
    }

    reservedPorts.put(pi, PRESENT);

    return pi;
  }

  /*
    •	Get the hash code of the service name as h
  •	Determine initial port as Pi = (h % (highPort – lowPort)) + lowPort
  •	While increment Pi while there are collusions
  o	If Pi + 1 > highPort, set Pi to lowPort

     */
  private synchronized void releasePort(int port) {
    reservedPorts.remove(port);
  }
}
