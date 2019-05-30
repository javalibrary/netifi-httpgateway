package com.netifi.consul.v1.catalog;

import com.netifi.consul.v1.Response;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Flux;

public interface CatalogClient {

  Flux<Response<Map<String, List<String>>>> getCatalogServices(
      CatalogServicesRequest catalogServicesRequest);
}
