package com.netifi.consul.v1;

import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.netty.http.client.HttpClientResponse;

public final class Response<T> {

  private final T value;
  private final HttpResponseStatus httpResponseStatus;
  private final Long consulIndex;
  private final Boolean consulKnownLeader;
  private final Long consulLastContact;
  private final String error;

  public Response(T value, String error) {
    this(value, null, error, null, null, null);
  }

  public Response(T value, HttpClientResponse httpClientResponse) {
    this(value, httpClientResponse, null);
  }

  public Response(T value, HttpClientResponse httpClientResponse, String error) {
    this(
        value,
        httpClientResponse.status(),
        error,
        parseConsulIndex(httpClientResponse),
        parseConsulKnownLeader(httpClientResponse),
        parseConsulLastContact(httpClientResponse));
  }

  public Response(
      T value,
      HttpResponseStatus httpResponseStatus,
      Long consulIndex,
      Boolean consulKnownLeader,
      Long consulLastContact) {
    this(value, httpResponseStatus, "", consulIndex, consulKnownLeader, consulLastContact);
  }

  public Response(
      T value,
      HttpResponseStatus httpResponseStatus,
      String error,
      Long consulIndex,
      Boolean consulKnownLeader,
      Long consulLastContact) {
    this.value = value;
    this.error = error;
    this.httpResponseStatus = httpResponseStatus;
    this.consulIndex = consulIndex;
    this.consulKnownLeader = consulKnownLeader;
    this.consulLastContact = consulLastContact;
  }

  private static Long parseConsulIndex(HttpClientResponse res) {
    Long consulIndex = null;
    String stringConsulIndex = res.responseHeaders().get("X-Consul-Index");
    if (stringConsulIndex != null && !stringConsulIndex.isEmpty()) {
      consulIndex = Long.parseLong(stringConsulIndex);
    }
    return consulIndex;
  }

  private static Boolean parseConsulKnownLeader(HttpClientResponse res) {
    Boolean consulKnownLeader = null;
    String stringConsulKnownLeader = res.responseHeaders().get("X-Consul-Knownleader");
    if (stringConsulKnownLeader != null && !stringConsulKnownLeader.isEmpty()) {
      consulKnownLeader = Boolean.parseBoolean(stringConsulKnownLeader);
    }
    return consulKnownLeader;
  }

  private static Long parseConsulLastContact(HttpClientResponse res) {
    Long consulLastContact = null;
    String stringConsulLastContact = res.responseHeaders().get("X-Consul-Lastcontact");
    if (stringConsulLastContact != null && !stringConsulLastContact.isEmpty()) {
      consulLastContact = Long.parseLong(stringConsulLastContact);
    }
    return consulLastContact;
  }

  public T getValue() {
    return value;
  }

  public Long getConsulIndex() {
    return consulIndex;
  }

  public Boolean isConsulKnownLeader() {
    return consulKnownLeader;
  }

  public Long getConsulLastContact() {
    return consulLastContact;
  }

  public boolean hasError() {
    return this.error != null;
  }

  public String getError() {
    return this.error;
  }

  public HttpResponseStatus getHttpResponseStatus() {
    return httpResponseStatus;
  }

  @Override
  public String toString() {
    return "Response{"
        + "value="
        + value
        + ", consulIndex="
        + this.consulIndex
        + ", consulKnownLeader="
        + this.consulKnownLeader
        + ", consulLastContact="
        + this.consulLastContact
        + ", error="
        + this.error
        + ", httpResponseStatus="
        + this.httpResponseStatus
        + '}';
  }
}
