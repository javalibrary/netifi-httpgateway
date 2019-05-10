package com.netifi.httpgateway.bridge.endpoint.ingress;

import com.netifi.httpgateway.bridge.codec.HttpRequestEncoder;
import com.netifi.httpgateway.bridge.codec.HttpResponseDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpResponse;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.util.ByteBufPayload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.publisher.UnicastProcessor;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import reactor.netty.http.server.HttpServerUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class DefaultIngressEndpoint extends AtomicBoolean implements IngressEndpoint {
  private final Logger logger = LogManager.getLogger(DefaultIngressEndpoint.class);

  private final String serviceName;

  private final ByteBuf serviceNameByteBuf;

  private final int port;

  private final RSocket target;

  private final MonoProcessor<Void> onClose;

  public DefaultIngressEndpoint(
      SslContextFactory sslContextFactory,
      String serviceName,
      boolean disableSSL,
      int port,
      RSocket target) {
    this.serviceName = serviceName;
    this.port = port;
    this.target = target;
    this.onClose = MonoProcessor.create();
    this.serviceNameByteBuf = Unpooled.buffer();

    ByteBufUtil.writeUtf8(serviceNameByteBuf, serviceName);

    HttpServer httpServer = HttpServer.create().port(port).handle(this::handle);

    if (!disableSSL) {
      httpServer =
          httpServer.secure(
              sslContextSpec -> sslContextSpec.sslContext(sslContextFactory.getSslServer()));
    }

    httpServer
        .bind()
        .subscribe(
            disposableServer ->
                onClose
                    .doFinally(
                        signalType -> {
                          if (!disposableServer.isDisposed()) {
                            disposableServer.dispose();
                          }
                        })
                    .subscribe(),
            throwable -> {
              logger.error(
                  String.format(
                      "error binding ingress endpoint for service %s on port %d - ssl disabled = %b",
                      serviceName, port, disableSSL),
                  throwable);
              dispose();
            });
  }

  private Mono<Void> handle(HttpServerRequest req, HttpServerResponse resp) {
    HttpRequestEncoder encoder = new HttpRequestEncoder();
    UnicastProcessor<ByteBuf> in = UnicastProcessor.create();
    encoder.encode(ByteBufAllocator.DEFAULT, HttpServerUtils.getRequest(req), in);

    return req.receive()
        .aggregate()
        .flatMap(
            inbound -> {
              encoder.encode(ByteBufAllocator.DEFAULT, inbound, in);
              in.onComplete();

              return ByteBufFlux.fromInbound(in)
                  .aggregate()
                  .map(buf -> ByteBufPayload.create(buf.retain(), serviceNameByteBuf.retain()))
                  .flatMap(target::requestResponse)
                  .map(Payload::sliceData)
                  .flatMap(
                      data -> {
                        HttpResponseDecoder decoder = new HttpResponseDecoder();
                        UnicastProcessor<Object> out = UnicastProcessor.create();
                        decoder.channelRead(ByteBufAllocator.DEFAULT, data, out);
                        decoder.channelReadComplete(out);

                        HttpResponse response = (HttpResponse) out.poll();
                        return resp.status(response.status())
                            .headers(response.headers())
                            .sendObject(out)
                            .then();
                      });
            })
        .doOnError(throwable -> logger.error("error handling request", throwable));
  }

  @Override
  public boolean isDisposed() {
    return get();
  }

  @Override
  public void dispose() {
    set(true);
    onClose.onComplete();
  }

  public String getServiceName() {
    return serviceName;
  }

  public int getPort() {
    return port;
  }

  @Override
  public Mono<Void> onClose() {
    return onClose;
  }
}
