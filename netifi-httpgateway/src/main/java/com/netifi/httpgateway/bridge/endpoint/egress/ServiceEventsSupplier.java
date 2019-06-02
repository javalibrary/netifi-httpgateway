package com.netifi.httpgateway.bridge.endpoint.egress;

import reactor.core.publisher.Flux;

import java.util.function.Supplier;

public interface ServiceEventsSupplier extends Supplier<Flux<ServiceEventsSupplier.ServiceEvent>> {
  interface ServiceEvent {
    String getServiceId();
  }
  
  interface ServiceJoinEvent extends ServiceEvent {
    <E extends EgressEndpoint, F extends EgressEndpointFactory<E>>
    EgressEndpointFactorySupplier<E, F> getEgressEndpointFactory();
  }
  
  interface ServiceLeaveEvent extends ServiceEvent {}
}
