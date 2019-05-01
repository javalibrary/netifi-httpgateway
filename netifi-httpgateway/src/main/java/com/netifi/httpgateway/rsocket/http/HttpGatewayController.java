/**
 * Copyright 2018 Netifi Inc.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netifi.httpgateway.rsocket.http;

import com.netifi.httpgateway.rsocket.endpoint.Endpoint;
import com.netifi.httpgateway.rsocket.endpoint.registry.EndpointRegistry;
import com.netifi.httpgateway.util.HttpUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.nio.charset.Charset;

@Component
public class HttpGatewayController implements CommandLineRunner {
  private static final Logger logger = LogManager.getLogger(HttpGatewayController.class);
  private static final Charset CHARSET = Charset.forName("UTF-8");

  private final String bindAddress;

  private final int bindPort;

  private final EndpointRegistry registry;

  @Autowired
  public HttpGatewayController(
      @Value("${netifi.client.gateway.bindAddress}") String bindAddress,
      @Value("${netifi.client.gateway.bindPort}") int bindPort,
      EndpointRegistry registry) {
    this.bindAddress = bindAddress;
    this.bindPort = bindPort;
    this.registry = registry;
  }

  @Override
  public void run(String... args) throws Exception {
    logger.info("Starting Netifi HTTP Gateway");
    logger.info("Binding to Address {}", bindAddress);
    logger.info("Binding to Port {}", bindPort);
    HttpServer.create()
        .host(bindAddress)
        .port(bindPort)
        .compress(true)
        .handle(this::handle)
        .bindNow();
  }

  private Publisher<Void> handle(HttpServerRequest request, HttpServerResponse response) {
    String uri = HttpUtil.stripTrailingSlash(request.uri());
    //              transportHeaders.get().forEach(response::addHeader);
    Endpoint endpoint = registry.lookup(uri);

    if (endpoint == null) {
      logger.error("no endpoint found for uri {}", uri);
      return response.sendNotFound();
    } else if (endpoint.isResponseStreaming() || endpoint.isRequestStreaming()) {
      return endpoint.apply(request.requestHeaders(), response);
    } else if (request.method() == HttpMethod.POST) {
      return handlePost(uri, endpoint, request, response);
    } else if (request.method() == HttpMethod.GET && endpoint.isRequestEmpty()) {
      return handleGet(uri, endpoint, request, response);
    } else {
      response.status(HttpResponseStatus.METHOD_NOT_ALLOWED);
      return response.send();
    }
  }

  private Publisher<Void> handlePost(
      String uri, Endpoint endpoint, HttpServerRequest request, HttpServerResponse response) {
    HttpHeaders headers = request.requestHeaders();
    if (!isContentTypeJson(headers)) {
      logger.error("request to endpoint for uri {} didn't contain json", uri);
      response.status(HttpResponseStatus.BAD_REQUEST);
      return response.send();
    }
    return request
        .receiveContent()
        .flatMap(
            content -> {
              ByteBuf bytes = content.content();
              if (!bytes.isReadable()) {
                logger.error("request to uri {} was empty", uri);
              }
              String json = bytes.toString(CHARSET);

              return endpoint.apply(headers, json, response);
            });
  }

  private Publisher<Void> handleGet(
      String uri, Endpoint endpoint, HttpServerRequest request, HttpServerResponse response) {
    HttpHeaders headers = request.requestHeaders();
    return endpoint.apply(headers, response);
  }

  protected boolean isContentTypeJson(HttpHeaders headers) {
    boolean found = false;
    for (String header : headers.getAllAsString("Content-Type")) {
      if (header.contains("application/json")) {
        found = true;
        break;
      }
    }

    return found;
  }
}
