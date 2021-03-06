package com.netifi.consul.v1.agent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.SingleUrlParameters;
import com.netifi.consul.v1.Utils;
import com.netifi.consul.v1.agent.model.AgentCheck;
import com.netifi.consul.v1.agent.model.AgentCheckRegistration;
import com.netifi.consul.v1.agent.model.AgentService;
import com.netifi.consul.v1.agent.model.AgentServiceRegistration;
import com.netifi.consul.v1.agent.model.CheckUpdate;
import com.netifi.consul.v1.agent.model.Self;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClientResponse;

public final class AgentConsulClient implements AgentClient {

  public static final String V_1_AGENT_SELF = "/v1/agent/self";
  public static final String V_1_AGENT_SERVICE = "/v1/agent/service/%s";
  public static final String V_1_AGENT_SERVICE_REGISTER = "/v1/agent/service/register";
  public static final String V_1_AGENT_SERVICE_DEREGISTER = "/v1/agent/service/deregister/%s";
  public static final String V_1_AGENT_SERVICES = "/v1/agent/services";
  public static final String V_1_CHECK_DEREGISTER = "/v1/agent/check/deregister/%s";
  public static final String V_1_CHECK_REGISTER = "/v1/agent/check/register";
  public static final String V_1_CHECKS = "/v1/agent/checks";
  public static final String V_1_MAINTENANCE_MODE = "/v1/agent/service/maintenance/%s";
  public static final String V_1_TTL_CHECK_FAIL = "/v1/agent/check/fail/%s";
  public static final String V_1_TTL_CHECK_PASS = "/v1/agent/check/pass/%s";
  public static final String V_1_TTL_CHECK_UPDATE = "/v1/agent/check/update/%s";
  public static final String V_1_TTL_CHECK_WARN = "/v1/agent/check/warn/%s";

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
        .uri(V_1_AGENT_SELF)
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
                          Self self = null;
                          String error = null;
                          try {
                            self = rawClient.getObjectMapper().readValue(s, Self.class);
                          } catch (IOException e) {
                            error = s;
                          }
                          return new Response<>(self, httpClientResponse, error);
                        }));
  }

  @Override
  public Flux<Response<Map<String, AgentCheck>>> getAgentChecks() {
    return rawClient
        .getHttpClient()
        .get()
        .uri(V_1_CHECKS)
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
                          Map<String, AgentCheck> checkList = null;
                          String error = null;
                          try {
                            checkList =
                                rawClient
                                    .getObjectMapper()
                                    .readValue(s, new TypeReference<Map<String, AgentCheck>>() {});
                          } catch (IOException e) {
                            error = s;
                          }
                          return new Response<>(checkList, httpClientResponse, error);
                        }));
  }

  @Override
  public Flux<Response<Map<String, AgentService>>> getAgentServices() {
    return rawClient
        .getHttpClient()
        .get()
        .uri(V_1_AGENT_SERVICES)
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
                          Map<String, AgentService> agentServiceList = null;
                          String error = null;
                          try {
                            agentServiceList =
                                rawClient
                                    .getObjectMapper()
                                    .readValue(
                                        s, new TypeReference<Map<String, AgentService>>() {});
                          } catch (IOException e) {
                            error = s;
                          }
                          return new Response<>(agentServiceList, httpClientResponse, error);
                        }));
  }

  @Override
  public Flux<Response<AgentService>> getAgentService(String serviceId) {
    return rawClient
        .getHttpClient()
        .get()
        .uri(String.format(V_1_AGENT_SERVICE, serviceId))
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
                          AgentService agentService = null;
                          String error = null;
                          try {
                            agentService =
                                rawClient.getObjectMapper().readValue(s, AgentService.class);
                          } catch (IOException e) {
                            error = s;
                          }
                          return new Response<>(agentService, httpClientResponse, error);
                        })));
  }

  @Override
  public Flux<Response<Void>> agentCheckRegister(AgentCheckRegistration agentCheckRegistration) {
    String checkPayload;
    try {
      checkPayload = serializeJson(agentCheckRegistration);
    } catch (JsonProcessingException e) {
      return Flux.just(new Response<>(null, e.getMessage()));
    }
    return rawClient
        .getHttpClient()
        .put()
        .uri(V_1_CHECK_REGISTER)
        .send(Mono.just(Unpooled.copiedBuffer(checkPayload.getBytes())))
        .response(this::voidRequestPublisher);
  }

  @Override
  public Flux<Response<Void>> agentCheckDeregister(String checkId) {
    return rawClient
        .getHttpClient()
        .put()
        .uri(String.format(V_1_CHECK_DEREGISTER, checkId))
        .response(this::voidRequestPublisher);
  }

  private Flux<Response<Void>> makePutRequest(String urlPath) {
    return rawClient.getHttpClient().put().uri(urlPath).response(this::voidRequestPublisher);
  }

  private Mono<Response<Void>> voidRequestPublisher(
      HttpClientResponse httpClientResponse, ByteBufFlux byteBufFlux) {
    return byteBufFlux
        .reduce("", (prev, now) -> prev + now.toString(Charset.defaultCharset()))
        .map(
            s -> {
              if (Utils.responseStatusNotOK(httpClientResponse)) {
                return new Response<>(null, httpClientResponse, Utils.ensureErrorString(s));
              }
              return new Response<>(null, httpClientResponse, null);
            });
  }

  @Override
  public Flux<Response<Void>> agentCheckUpdate(String checkId, CheckUpdate checkUpdate) {
    String checkUpdatePayload;
    try {
      checkUpdatePayload = serializeJson(checkUpdate);
    } catch (JsonProcessingException e) {
      return Flux.just(new Response<>(null, e.getMessage()));
    }
    return rawClient
        .getHttpClient()
        .put()
        .uri(String.format(V_1_TTL_CHECK_UPDATE, checkId))
        .send(Mono.just(Unpooled.copiedBuffer(checkUpdatePayload.getBytes())))
        .response(this::voidRequestPublisher);
  }

  @Override
  public Flux<Response<Void>> agentServiceRegister(
      AgentServiceRegistration agentServiceRegistration) {
    String servicePayload;
    try {
      servicePayload = serializeJson(agentServiceRegistration);
    } catch (JsonProcessingException e) {
      return Flux.just(new Response<>(null, e.getMessage()));
    }

    return rawClient
        .getHttpClient()
        .put()
        .uri(V_1_AGENT_SERVICE_REGISTER)
        .send(Mono.just(Unpooled.copiedBuffer(servicePayload.getBytes())))
        .response(this::voidRequestPublisher);
  }

  @Override
  public Flux<Response<Void>> agentServiceDeregister(String serviceId) {
    return rawClient
        .getHttpClient()
        .put()
        .uri(String.format(V_1_AGENT_SERVICE_DEREGISTER, serviceId))
        .response(this::voidRequestPublisher);
  }

  @Override
  public Flux<Response<Void>> agentServiceSetMaintenance(
      String serviceId, boolean maintenanceEnabled, String reason) {
    String urlPath = String.format(V_1_MAINTENANCE_MODE, serviceId);
    urlPath =
        Utils.generateUrl(
            urlPath, new SingleUrlParameters("enable", Boolean.toString(maintenanceEnabled)));
    if (reason != null) {
      urlPath = Utils.generateUrl(urlPath, new SingleUrlParameters("reason", reason));
    }
    return makePutRequest(urlPath);
  }

  private String serializeJson(Object object) throws JsonProcessingException {
    return rawClient.getObjectMapper().writeValueAsString(object);
  }
}
