package com.netifi.consul.v1.health;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.Utils;
import com.netifi.consul.v1.health.model.ServiceEntry;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import reactor.core.publisher.Flux;

public class HealthConsulClient implements HealthClient {

  private final ConsulRawClient rawClient;

  public HealthConsulClient() {
    this.rawClient = ConsulRawClient.Builder.builder().build();
  }

  public HealthConsulClient(ConsulRawClient rawClient) {
    this.rawClient = rawClient;
  }

  @Override
  public Flux<Response<List<ServiceEntry>>> listNodesForServices(
      ListNodesForServicesRequest listNodesForServicesRequest) {
    return rawClient
        .getHttpClient()
        .get()
        .uri(listNodesForServicesRequest.toURIString())
        .response(
            ((httpClientResponse, byteBufFlux) ->
                byteBufFlux
                    .reduce("", (prev, now) -> prev + now.toString(Charset.defaultCharset()))
                    .map(
                        s -> {
                          if (Utils.responseStatusNotOK(httpClientResponse)) {
                            return new Response<>(
                                null, httpClientResponse, Utils.ensureErrorString(s));
                          }
                          List<ServiceEntry> serviceList = null;
                          String error = null;
                          try {
                            serviceList =
                                rawClient
                                    .getObjectMapper()
                                    .readValue(s, new TypeReference<List<ServiceEntry>>() {});
                          } catch (IOException e) {
                            error = s;
                          }
                          return new Response<>(serviceList, httpClientResponse, error);
                        })));
  }
}
