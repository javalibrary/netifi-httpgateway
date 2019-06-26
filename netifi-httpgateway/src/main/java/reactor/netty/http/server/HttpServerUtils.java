package reactor.netty.http.server;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public class HttpServerUtils {
  public static HttpRequest getRequest(HttpServerRequest request) {
    return ((HttpServerOperations) request).nettyRequest;
  }

  public static HttpResponse getResponse(HttpServerResponse response) {
    return ((HttpServerOperations) response).nettyResponse;
  }
}
