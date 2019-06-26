package com.netifi.consul.v1;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.cookie.Cookie;
import java.util.Map;
import java.util.Set;
import reactor.netty.http.client.HttpClientResponse;
import reactor.util.context.Context;

public class EmptyHttpClientResponse implements HttpClientResponse {

  @Override
  public Context currentContext() {
    return null;
  }

  @Override
  public String[] redirectedFrom() {
    return new String[0];
  }

  @Override
  public HttpHeaders responseHeaders() {
    return null;
  }

  @Override
  public HttpResponseStatus status() {
    return null;
  }

  @Override
  public Map<CharSequence, Set<Cookie>> cookies() {
    return null;
  }

  @Override
  public boolean isKeepAlive() {
    return false;
  }

  @Override
  public boolean isWebsocket() {
    return false;
  }

  @Override
  public HttpMethod method() {
    return null;
  }

  @Override
  public String uri() {
    return null;
  }

  @Override
  public HttpVersion version() {
    return null;
  }
}
