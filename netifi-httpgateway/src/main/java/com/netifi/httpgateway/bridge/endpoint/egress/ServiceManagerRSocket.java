package com.netifi.httpgateway.bridge.endpoint.egress;

import com.netifi.httpgateway.bridge.codec.HttpRequestDecoder;
import com.netifi.httpgateway.bridge.codec.HttpResponseEncoder;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.EgressEndpointLoadBalancer;
import com.netifi.httpgateway.bridge.endpoint.egress.lb.EgressEndpointLoadBalancerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.util.ByteBufPayload;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClientUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages services as they are feed in from an external discovery service. A service contains a
 * group of endpoints that requests to the service as sent to.
 */
public class ServiceManagerRSocket extends AbstractRSocket {
  private final Map<String, EgressEndpointLoadBalancer> loadBalancers;
  private final EgressEndpointLoadBalancerFactory factory;

  public ServiceManagerRSocket(
      Flux<ServiceEvent> serviceManagerEvents, EgressEndpointLoadBalancerFactory factory) {
    this.loadBalancers = new ConcurrentHashMap<>();
    this.factory = factory;

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
    loadBalancers.computeIfAbsent(
        serviceEvent.getServiceId(),
        s -> factory.createNewLoadBalancer(serviceEvent.getEgressEndpointFactory()));
  }

  private void handleLeaveEvent(ServiceLeaveEvent serviceEvent) {
    EgressEndpointLoadBalancer removed = loadBalancers.remove(serviceEvent.getServiceId());

    if (removed != null) {
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
