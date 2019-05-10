package com.netifi.httpgateway.bridge.endpoint.ingress;

import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;

public class PortManager {

  private static final Object PRESENT = new Object();

  private final IntObjectMap<Object> reservedPorts;

  private final int lowPort;

  private final int hightPort;

  public PortManager(int lowPort, int hightPort) {
    this.reservedPorts = new IntObjectHashMap<>();
    this.lowPort = lowPort;
    this.hightPort = hightPort;
  }

  public synchronized int reservePort(String serviceName) {
    int h = serviceName.hashCode();

    int pi = (h % (hightPort - lowPort)) + lowPort;

    while (reservedPorts.containsKey(pi)) {
      pi++;
    }

    reservedPorts.put(pi, PRESENT);

    return pi;
  }

  public synchronized void releasePort(int port) {
    reservedPorts.remove(port);
  }
  
  public synchronized boolean isPortReserved(int port) {
    return reservedPorts.containsKey(port);
  }
  
}
