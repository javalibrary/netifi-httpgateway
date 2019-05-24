package com.netifi.consul.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.json.JsonObjectDecoder;
import reactor.netty.http.client.HttpClient;

public class ConsulRawClient {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private static final JsonObjectDecoder jsonObjectDecoder = new JsonObjectDecoder();

  private static final String DEFAULT_HOST = "localhost";
  private static final int DEFAULT_PORT = 8500;
  private static final String DEFAULT_PATH = "";
  private static final HttpClient DEFAULT_HTTP_CLIENT =
      HttpClient.create().doOnResponse((res, conn) -> conn.addHandler(jsonObjectDecoder));

  private final String agentAddress;
  private final HttpClient httpClient;

  public static final class Builder {
    private String agentHost;
    private int agentPort;
    private String agentPath;
    private String token;
    private boolean useTokenBearerHeader;
    private boolean useTokenQueryParameter;
    private boolean useTokenXConsulTokenHeader;
    private HttpClient httpClient;

    public static ConsulRawClient.Builder builder() {
      return new ConsulRawClient.Builder();
    }

    private Builder() {
      this.agentHost = DEFAULT_HOST;
      this.agentPort = DEFAULT_PORT;
      this.agentPath = DEFAULT_PATH;
      this.httpClient = DEFAULT_HTTP_CLIENT;
    }

    public Builder withHost(String host) {
      this.agentHost = host;
      return this;
    }

    public Builder withPort(int port) {
      this.agentPort = port;
      return this;
    }

    public Builder withPath(String path) {
      this.agentPath = path;
      return this;
    }

    public Builder withToken(String token) {
      this.token = token;
      return this;
    }

    public Builder withHTTPClient(HttpClient httpClient) {
      this.httpClient = httpClient;
      return this;
    }

    public Builder useTokenBearerHeader() {
      this.useTokenBearerHeader = true;
      this.useTokenQueryParameter = false;
      this.useTokenXConsulTokenHeader = false;
      return this;
    }

    public Builder useTokenQueryParameter() {
      this.useTokenBearerHeader = false;
      this.useTokenQueryParameter = true;
      this.useTokenXConsulTokenHeader = false;
      return this;
    }

    public Builder useTokenXConsulTokenHeader() {
      this.useTokenBearerHeader = false;
      this.useTokenQueryParameter = false;
      this.useTokenXConsulTokenHeader = true;
      return this;
    }

    public ConsulRawClient build() {
      if ((this.token != null && !this.token.equals(""))
          && !(this.useTokenBearerHeader
              || this.useTokenQueryParameter
              || this.useTokenXConsulTokenHeader)) {
        this.useTokenXConsulTokenHeader = true;
      }

      if (this.useTokenBearerHeader) {
        this.httpClient = httpClient.headers(h -> h.add("Authorization", "Bearer " + this.token));
      } else if (this.useTokenQueryParameter) {
        // TODO: add a query parameter to the url with "token="+this.token
        // https://projectreactor.io/docs/netty/release/api/reactor/netty/http/client/HttpClient.html#doOnRequest-java.util.function.BiConsumer-
        // https://www.consul.io/api/index.html#authentication
      } else if (this.useTokenXConsulTokenHeader) {
        this.httpClient = httpClient.headers(h -> h.add("X-Consul-Token", this.token));
      }
      String path = "";
      if (agentPath != null && !agentPath.trim().isEmpty()) {
        path = "/" + agentPath;
      }
      String agentAddress = String.format("%s:%d%s", agentHost, agentPort, agentPath);
      this.httpClient = httpClient.baseUrl(agentAddress);
      return new ConsulRawClient(this.httpClient, agentAddress);
    }
  }

  public ConsulRawClient(HttpClient httpClient, String agentAddress) {
    this.httpClient = httpClient;
    this.agentAddress = agentAddress;
  }

  public HttpClient getHttpClient() {
    return this.httpClient;
  }

  public ObjectMapper getObjectMapper() {
    return this.objectMapper;
  }
}
