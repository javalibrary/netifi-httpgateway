package com.netifi.httpgateway.bridge.endpoint.egress;

import io.rsocket.Closeable;
import io.rsocket.Payload;
import java.util.function.Function;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public interface EgressEndpoint extends Closeable, Disposable {
  String getEgressEndpointId();

  Mono<Payload> request(Function<HttpClient, Mono<Payload>> handle);
}
