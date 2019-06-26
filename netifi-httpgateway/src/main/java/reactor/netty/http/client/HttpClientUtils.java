package reactor.netty.http.client;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public class HttpClientUtils {
  public static HttpRequest getRequest(HttpClientResponse request) {
    return ((HttpClientOperations) request).nettyRequest;
  }

  public static HttpResponse getResponse(HttpClientResponse response) {
    HttpClientOperations.ResponseState responseState =
        ((HttpClientOperations) response).responseState;
    if (responseState != null) {
      return responseState.response;
    }
    throw new IllegalStateException("Response cannot be accessed without server response");
  }
}
