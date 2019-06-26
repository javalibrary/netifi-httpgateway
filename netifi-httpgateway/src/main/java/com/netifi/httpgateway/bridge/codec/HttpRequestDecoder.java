/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.netifi.httpgateway.bridge.codec;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;

/** Decodes {@link ByteBuf}s into {@link HttpRequest}s and {@link HttpContent}s. */
public class HttpRequestDecoder extends HttpObjectDecoder {

  /**
   * Creates a new instance with the default {@code maxInitialLineLength (4096)}, {@code
   * maxHeaderSize (8192)}, and {@code maxChunkSize (8192)}.
   */
  public HttpRequestDecoder() {}

  /** Creates a new instance with the specified parameters. */
  public HttpRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
    super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true);
  }

  public HttpRequestDecoder(
      int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
    super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders);
  }

  public HttpRequestDecoder(
      int maxInitialLineLength,
      int maxHeaderSize,
      int maxChunkSize,
      boolean validateHeaders,
      int initialBufferSize) {
    super(
        maxInitialLineLength,
        maxHeaderSize,
        maxChunkSize,
        true,
        validateHeaders,
        initialBufferSize);
  }

  @Override
  protected HttpMessage createMessage(String[] initialLine) {
    return new DefaultHttpRequest(
        HttpVersion.valueOf(initialLine[2]),
        HttpMethod.valueOf(initialLine[0]),
        initialLine[1],
        validateHeaders);
  }

  @Override
  protected HttpMessage createInvalidMessage() {
    return new DefaultFullHttpRequest(
        HttpVersion.HTTP_1_0, HttpMethod.GET, "/bad-request", validateHeaders);
  }

  @Override
  protected boolean isDecodingRequest() {
    return true;
  }
}
