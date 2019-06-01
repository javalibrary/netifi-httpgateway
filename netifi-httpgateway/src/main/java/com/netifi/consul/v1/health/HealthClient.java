package com.netifi.consul.v1.health;

import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.health.model.HealthService;
import java.util.List;
import reactor.core.publisher.Flux;

public interface HealthClient {
  public Flux<Response<List<HealthService>>> listNodesForServices(
      ListNodesForServicesRequest listNodesForServicesRequest);
}
