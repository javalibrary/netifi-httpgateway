package com.netifi.consul.v1.agent;

import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.agent.model.Self;
import reactor.core.publisher.Flux;

public interface AgentClient {
  public Flux<Response<Self>> getAgentSelf();
}
