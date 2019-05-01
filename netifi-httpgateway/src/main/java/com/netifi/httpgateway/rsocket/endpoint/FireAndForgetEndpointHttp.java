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
package com.netifi.httpgateway.rsocket.endpoint;

import com.google.protobuf.Descriptors;
import com.google.protobuf.util.JsonFormat;
import com.netifi.httpgateway.rsocket.RSocketSupplier;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import org.reactivestreams.Publisher;
import reactor.netty.http.server.HttpServerResponse;

import java.time.Duration;

public class FireAndForgetEndpointHttp extends HttpAbstractEndpoint<Void> {
  public FireAndForgetEndpointHttp(
      String service,
      String method,
      Descriptors.Descriptor request,
      Descriptors.Descriptor response,
      String defaultGroup,
      RSocketSupplier rSocketSupplier,
      boolean hasTimeout,
      Duration timeout,
      int maxConcurrency,
      JsonFormat.TypeRegistry typeRegistry) {
    super(
        service,
        method,
        request,
        response,
        defaultGroup,
        rSocketSupplier,
        hasTimeout,
        timeout,
        maxConcurrency,
        typeRegistry);
  }

  @Override
  Publisher<Void> doApply(RSocket rSocket, Payload request) {
    return rSocket.fireAndForget(request);
  }

  @Override
  Publisher<Void> doHandleResponse(Void source, HttpServerResponse response) {
    return response.send();
  }
}
