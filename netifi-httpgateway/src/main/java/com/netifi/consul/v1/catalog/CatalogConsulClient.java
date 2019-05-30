package com.netifi.consul.v1.catalog;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.Response;
import java.io.IOException;
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
            (res, byteBufFlux) ->
                byteBufFlux
                    .asString()
                    .map(
                        s -> {
                          Map<String, List<String>> serviceMap = null;
                          String err = null;
                          try {
                            serviceMap = rawClient
                                    .getObjectMapper()
                                    .readValue(
                                          s, new TypeReference<Map<String, List<String>>>() {});
                          } catch (IOException e) {
                            // This can fail and it's probably ok.
                            err = s;
                          }
                          return new Response<>(serviceMap, res, err);
                        }));
  }
}
