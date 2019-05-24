package com.netifi.consul.v1.agent;

import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.agent.model.Self;
import java.io.IOException;
import java.util.function.Function;
import reactor.core.publisher.Flux;

public final class AgentConsulClient implements AgentClient {

  private final ConsulRawClient rawClient;

  public AgentConsulClient() {
    this.rawClient = ConsulRawClient.Builder.builder().build();
  }

  public AgentConsulClient(ConsulRawClient rawClient) {
    this.rawClient = rawClient;
  }

  @Override
  public Flux<Response<Self>> getAgentSelf() {
    return rawClient
        .getHttpClient()
        .get()
        .uri("/v1/agent/self")
        .response(
            (res, byteBufFlux) ->
                byteBufFlux
                    .asString()
                    .map(
                        s -> {
                          Long consulIndex = null;
                          Boolean consulKnownLeader = null;
                          Long consulLastContact = null;
                          String stringConsulIndex = res.responseHeaders().get("X-Consul-Index");
                          if (stringConsulIndex != null && !stringConsulIndex.equals("")) {
                            consulIndex = Long.parseLong(stringConsulIndex);
                          }
                          String stringConsulKnownLeader =
                              res.responseHeaders().get("X-Consul-Knownleader");
                          if (stringConsulKnownLeader != null
                              && !stringConsulKnownLeader.equals("")) {
                            consulKnownLeader = Boolean.parseBoolean(stringConsulKnownLeader);
                          }
                          String stringConsulLastContact =
                              res.responseHeaders().get("X-Consul-Lastcontact");
                          if (stringConsulLastContact != null
                              && !stringConsulLastContact.equals("")) {
                            consulLastContact = Long.parseLong(stringConsulLastContact);
                          }
                          try {
                            return new Response<>(
                                rawClient.getObjectMapper().readValue(s, Self.class),
                                consulIndex,
                                consulKnownLeader,
                                consulLastContact);
                          } catch (IOException e) {
                            e.printStackTrace();
                          }
                          return null;
                        }));
  }
}
