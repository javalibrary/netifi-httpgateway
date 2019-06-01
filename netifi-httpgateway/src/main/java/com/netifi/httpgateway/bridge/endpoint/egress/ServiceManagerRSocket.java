package com.netifi.httpgateway.bridge.endpoint.egress;

import com.netifi.broker.BrokerClient;
import com.netifi.httpgateway.bridge.codec.HttpRequestDecoder;
import com.netifi.httpgateway.bridge.codec.HttpResponseEncoder;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.EgressEndpointLoadBalancer;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.EgressEndpointLoadBalancerFactory;
import com.netifi.httpgateway.bridge.endpoint.source.BridgeEndpointSourceServer;
import com.netifi.httpgateway.bridge.endpoint.source.EndpointJoinEvent;
import com.netifi.httpgateway.bridge.endpoint.source.EndpointLeaveEvent;
import com.netifi.httpgateway.bridge.endpoint.source.Event;
import io.micrometer.core.instrument.MeterRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.util.ByteBufPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.*;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClientUtils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages services as they are feed in from an external discovery service. A service contains a
 * group of endpoints that requests to the service as sent to.
 */
public class ServiceManagerRSocket extends AbstractRSocket {
  private static final Logger logger = LoggerFactory.getLogger(ServiceManagerRSocket.class);
  private final Map<String, EgressEndpointLoadBalancer> loadBalancers;
  private final FluxProcessor<Event, Event> eventProcessor;
  private final EgressEndpointLoadBalancerFactory factory;

  public ServiceManagerRSocket(
      Flux<ServiceEvent> serviceManagerEvents,
      EgressEndpointLoadBalancerFactory factory,
      BrokerClient brokerClient,
      MeterRegistry registry) {
    this.eventProcessor = DirectProcessor.<Event>create().serialize();
    this.loadBalancers = new ConcurrentHashMap<>();
    this.factory = factory;

    DefaultBridgeEndpointSource source =
        new DefaultBridgeEndpointSource(loadBalancers::keySet, eventProcessor);

    brokerClient.addService(
        new BridgeEndpointSourceServer(source, Optional.of(registry), Optional.empty()));

    Disposable disposable = serviceManagerEvents.doOnNext(this::handleEvent).subscribe();

    onClose().doFinally(s -> disposable.dispose()).subscribe();
  }

  private void handleEvent(ServiceEvent serviceEvent) {
    if (serviceEvent instanceof ServiceJoinEvent) {
      handleJoinEvent((ServiceJoinEvent) serviceEvent);
    } else {
      handleLeaveEvent((ServiceLeaveEvent) serviceEvent);
    }
  }

  @SuppressWarnings("unchecked")
  private void handleJoinEvent(ServiceJoinEvent serviceEvent) {
    EgressEndpointLoadBalancer old =
        loadBalancers.computeIfAbsent(
            serviceEvent.getServiceId(),
            s -> {
              logger.info("adding new egress service with id {}", s);
              return factory.createNewLoadBalancer(serviceEvent.getEgressEndpointFactory());
            });

    if (old == null) {
      eventProcessor.onNext(
          Event.newBuilder()
              .setJoinEvent(
                  EndpointJoinEvent.newBuilder()
                      .setServiceName(serviceEvent.getServiceId())
                      .build())
              .build());
    }
  }

  private void handleLeaveEvent(ServiceLeaveEvent serviceEvent) {
    EgressEndpointLoadBalancer removed = loadBalancers.remove(serviceEvent.getServiceId());

    if (removed != null) {
      logger.info("removing Egress service with id {}", serviceEvent.getServiceId());
      eventProcessor.onNext(
          Event.newBuilder()
              .setLeaveEvent(
                  EndpointLeaveEvent.newBuilder()
                      .setServiceName(serviceEvent.getServiceId())
                      .build())
              .build());
      removed.dispose();
    }
  }

  @Override
  public double availability() {
    return loadBalancers.size();
  }

  @Override
  public Mono<Payload> requestResponse(Payload payload) {
    try {
      String serviceName = payload.getMetadataUtf8();
      HttpRequestDecoder decoder = new HttpRequestDecoder();
      UnicastProcessor<Object> in = UnicastProcessor.create();
      decoder.channelRead(ByteBufAllocator.DEFAULT, payload.sliceData(), in);
      decoder.channelReadComplete(in);

      HttpRequest request = (HttpRequest) in.poll();

      EgressEndpoint egressEndpoint = loadBalancers.get(serviceName).select();

      return egressEndpoint.request(
          client -> {
            Flux<ByteBuf> inbound =
                client
                    .request(request.method())
                    .uri(request.uri())
                    .send(
                        (req, outbound) -> {
                          req.requestHeaders().setAll(request.headers());
                          return outbound.sendObject(in);
                        })
                    .responseConnection(
                        (resp, conn) -> {
                          HttpResponseEncoder encoder = new HttpResponseEncoder();
                          UnicastProcessor<ByteBuf> out = UnicastProcessor.create();

                          HttpResponse response = HttpClientUtils.getResponse(resp);
                          encoder.encode(ByteBufAllocator.DEFAULT, response, out);

                          return conn.inbound()
                              .receive()
                              .aggregate()
                              .flatMapMany(
                                  buf -> {
                                    encoder.encode(ByteBufAllocator.DEFAULT, buf, out);
                                    out.onComplete();
                                    return out;
                                  });
                        });

            return ByteBufFlux.fromInbound(inbound)
                .aggregate()
                .map(buf -> ByteBufPayload.create(buf.retain()));
          });
    } catch (Throwable t) {
      return Mono.error(t);
    }
  }

  interface ServiceEvent {
    String getServiceId();
  }

  interface ServiceJoinEvent extends ServiceEvent {
    <E extends EgressEndpoint, F extends EgressEndpointFactory<E>>
        EgressEndpointFactorySupplier<E, F> getEgressEndpointFactory();
  }

  interface ServiceLeaveEvent extends ServiceEvent {}
}
