package com.netifi.httpgateway.bridge.endpoint.egress;

import com.google.protobuf.Empty;
import com.netifi.httpgateway.bridge.endpoint.source.BridgeEndpointSource;
import com.netifi.httpgateway.bridge.endpoint.source.EndpointJoinEvent;
import com.netifi.httpgateway.bridge.endpoint.source.Event;
import io.netty.buffer.ByteBuf;
import java.util.Set;
import java.util.function.Supplier;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;

public class DefaultBridgeEndpointSource implements BridgeEndpointSource {
  private Supplier<Set<String>> serviceNameSupplier;

  private FluxProcessor<Event, Event> processor;

  public DefaultBridgeEndpointSource(
      Supplier<Set<String>> serviceNameSupplier, FluxProcessor<Event, Event> processor) {
    this.serviceNameSupplier = serviceNameSupplier;
    this.processor = processor;
  }

  @Override
  public Flux<Event> streamEndpointEvents(Empty message, ByteBuf metadata) {
    Flux<Event> initial =
        Flux.fromStream(serviceNameSupplier.get().stream())
            .map(
                serviceNames ->
                    Event.newBuilder()
                        .setJoinEvent(
                            EndpointJoinEvent.newBuilder().setServiceName(serviceNames).build())
                        .build());

    return processor.startWith(initial).onBackpressureBuffer(256, BufferOverflowStrategy.ERROR);
  }
}
