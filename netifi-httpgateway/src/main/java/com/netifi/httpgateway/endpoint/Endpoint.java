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
package com.netifi.httpgateway.endpoint;

import io.netty.handler.codec.http.HttpHeaders;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerResponse;

@FunctionalInterface
public interface Endpoint {
  Publisher<Void> apply(HttpHeaders headers, String json, HttpServerResponse response);

  default Publisher<Void> apply(HttpHeaders headers, HttpServerResponse response) {
    return apply(headers, "", response);
  }

  default Publisher<Void> apply(
      HttpHeaders headers, Publisher<String> json, HttpServerResponse response) {
    return Mono.error(new UnsupportedOperationException("streaming request not support"));
  }

  default boolean isRequestEmpty() {
    return false;
  }

  default boolean isRequestStreaming() {
    return false;
  }
  
  default boolean isResponseStreaming() { return false; }
}
