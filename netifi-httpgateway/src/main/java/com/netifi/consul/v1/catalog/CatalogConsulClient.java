package com.netifi.consul.v1.catalog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.Utils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Flux;

public class CatalogConsulClient implements CatalogClient {

  private final ConsulRawClient rawClient;

  public CatalogConsulClient() {
    this.rawClient = ConsulRawClient.Builder.builder().build();
  }

  public CatalogConsulClient(ConsulRawClient rawClient) {
    this.rawClient = rawClient;
  }

  @Override
  public Flux<Response<Map<String, List<String>>>> getCatalogServices(
      CatalogServicesRequest catalogServicesRequest) {
    return rawClient
        .getHttpClient()
        .get()
        .uri(catalogServicesRequest.toURIString())
        .response(
            (httpClientResponse, byteBufFlux) ->
                byteBufFlux
                    .reduce("", (prev, now) -> prev + now.toString(Charset.defaultCharset()))
                    .map(
                        s -> {
                          if (Utils.responseStatusNotOK(httpClientResponse)) {
                            return new Response<>(
                                null, httpClientResponse, Utils.ensureErrorString(s));
                          }
                          Map<String, List<String>> serviceMap = null;
                          String error = null;
                          try {
                            serviceMap =
                                rawClient
                                    .getObjectMapper()
                                    .readValue(
                                        s, new TypeReference<Map<String, List<String>>>() {});
                          } catch (IOException e) {
                            error = s;
                          }
                          return new Response<>(serviceMap, httpClientResponse, error);
                        }));
  }
}
