package com.netifi.consul.v1;

import java.net.URISyntaxException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netifi.httpgateway.util.HttpUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.ByteBufMono;
import reactor.netty.Connection;
import reactor.netty.NettyOutbound;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientForm;
import reactor.netty.http.client.HttpClientRequest;
import reactor.netty.http.client.HttpClientResponse;

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
        this.httpClient = new TokenAwareHttpClient(httpClient);
      } else if (this.useTokenXConsulTokenHeader) {
        this.httpClient = httpClient.headers(h -> h.add("X-Consul-Token", this.token));
      }
      String path = "";
      if (agentPath != null && !agentPath.trim().isEmpty()) {
        path = "/" + agentPath;
      }
      String agentAddress = String.format("%s:%d%s", agentHost, agentPort, path);
      this.httpClient = httpClient.baseUrl(agentAddress);
      return new ConsulRawClient(this.httpClient, agentAddress);
    }

    private class TokenAwareHttpClient extends HttpClient {

      private final HttpClient delegate;

      private TokenAwareHttpClient(HttpClient delegate) {
        this.delegate = delegate;
      }

      @Override
      public RequestSender request(HttpMethod method) {
        final RequestSender requestSenderDelegate = this.delegate.request(method);
        return new TokenAwareRequestSender(requestSenderDelegate);
      }

      private class TokenAwareRequestSender implements RequestSender {

        private final RequestSender delegate;

        private TokenAwareRequestSender(RequestSender delegate) {
          this.delegate = delegate;
        }

        @Override
        public ResponseReceiver<?> send(Publisher<? extends ByteBuf> body) {
          return delegate.send(body);
        }

        @Override
        public ResponseReceiver<?> send(BiFunction<? super HttpClientRequest, ? super NettyOutbound, ? extends Publisher<Void>> sender) {
          return delegate.send(sender);
        }

        @Override
        public ResponseReceiver<?> sendForm(BiConsumer<? super HttpClientRequest, HttpClientForm> formCallback,
            @Nullable Consumer<Flux<Long>> progress) {
          return delegate.sendForm(formCallback, progress);
        }

        @Override
        public Mono<HttpClientResponse> response() {
          return delegate.response();
        }

        @Override
        public <V> Flux<V> response(BiFunction<? super HttpClientResponse, ? super ByteBufFlux, ? extends Publisher<V>> receiver) {
          return delegate.response(receiver);
        }

        @Override
        public <V> Flux<V> responseConnection(BiFunction<? super HttpClientResponse, ? super Connection, ? extends Publisher<V>> receiver) {
          return delegate.responseConnection(receiver);
        }

        @Override
        public ByteBufFlux responseContent() {
          return delegate.responseContent();
        }

        @Override
        public <V> Mono<V> responseSingle(BiFunction<? super HttpClientResponse, ? super ByteBufMono, ? extends Mono<V>> receiver) {
          return delegate.responseSingle(receiver);
        }

        @Override
        public RequestSender uri(String uri) {
          try {
            return delegate.uri(HttpUtil.appendUri(uri, "token=" + token).toString());
          }
          catch (URISyntaxException e) {
            throw new RuntimeException(e);
          }
        }

        @Override
        public RequestSender uri(Mono<String> uri) {
          return delegate.uri(uri.handle((uri1, sink) -> {
            try {
              sink.next(HttpUtil.appendUri(uri1, "token=" + token).toString());
            }
            catch (URISyntaxException e) {
              sink.error(new RuntimeException(e));
            }
          }));
        }
      }
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
