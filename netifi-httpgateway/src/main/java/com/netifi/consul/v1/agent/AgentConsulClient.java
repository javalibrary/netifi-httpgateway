package com.netifi.consul.v1.agent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.EmptyHttpClientResponse;
import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.SingleUrlParameters;
import com.netifi.consul.v1.Utils;
import com.netifi.consul.v1.agent.model.Check;
import com.netifi.consul.v1.agent.model.CheckUpdate;
import com.netifi.consul.v1.agent.model.NewCheck;
import com.netifi.consul.v1.agent.model.NewService;
import com.netifi.consul.v1.agent.model.Self;
import com.netifi.consul.v1.agent.model.Service;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.io.IOException;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
            (res, byteBufFlux) ->
                byteBufFlux
                    .asString()
                    .map(
                        s -> {
                          Self self = null;
                          String err = null;
                          try {
                            self = rawClient.getObjectMapper().readValue(s, Self.class);
                          } catch (IOException e) {
                            err = s;
                          }
                          return new Response<>(self, res, err);
                        }));
  }

  @Override
  public Flux<Response<List<Check>>> getAgentChecks() {
    return rawClient
        .getHttpClient()
        .get()
        .uri(V_1_CHECKS)
        .response(
            (res, byteBufFlux) ->
                byteBufFlux
                    .asString()
                    .map(
                        s -> {
                          try {
                            return new Response<>(
                                rawClient
                                    .getObjectMapper()
                                    .readValue(s, new TypeReference<List<Check>>() {}),
                                res);
                          } catch (IOException e) {
                            e.printStackTrace();
                          }
                          return null;
                        }));
  }

  @Override
  public Flux<Response<List<Service>>> getAgentServices() {
    return rawClient
        .getHttpClient()
        .get()
        .uri(V_1_AGENT_SERVICES)
        .response(
            (res, byteBufFlux) ->
                byteBufFlux
                    .asString()
                    .map(
                        s -> {
                          List<Service> serviceList = null;
                          String error = null;
                          try {
                            serviceList =
                                rawClient
                                    .getObjectMapper()
                                    .readValue(s, new TypeReference<List<Service>>() {});
                          } catch (IOException e) {
                            error = s;
                          }
                          return new Response<>(serviceList, res, error);
                        }));
  }

  @Override
  public Flux<Response<Service>> getAgentService(String serviceId) {
    return rawClient
        .getHttpClient()
        .get()
        .uri(String.format(V_1_AGENT_SERVICE, serviceId))
        .response(
            ((httpClientResponse, byteBufFlux) ->
                Flux.create(
                    fluxSink -> {
                      Service service;
                      String error;
                      if (!httpClientResponse.status().equals(HttpResponseStatus.OK)) {
                        error = "err: ";
                        // TODO: add any text we find from the byteBufFlux to the error message.
                      } else {
                        byteBufFlux
                            .asString()
                            .map(
                                s -> {
                                  try {
                                    service =
                                        rawClient.getObjectMapper().readValue(s, Service.class);
                                  } catch (IOException e) {
                                    error = e.getMessage();
                                  }
                                })
                            .subscribe();
                      }
                    })));
  }

  @Override
  public Flux<Response<Void>> agentCheckRegister(NewCheck newCheck) {
    String checkPayload;
    try {
      checkPayload = serializeJson(newCheck);
    } catch (JsonProcessingException e) {
      return Flux.just(new Response<>(null, new EmptyHttpClientResponse(), e.getMessage()));
    }
    return rawClient
        .getHttpClient()
        .put()
        .uri(V_1_CHECK_REGISTER)
        .send(Mono.just(Unpooled.copiedBuffer(checkPayload.getBytes())))
        .response(
            (httpClientResponse, byteBufFlux) ->
                Flux.create(
                    sink -> {
                      boolean[] responses = new boolean[1];
                      byteBufFlux
                          .asString()
                          .doOnNext(
                              s -> {
                                responses[0] = true;
                                if (httpClientResponse.status() != HttpResponseStatus.OK) {
                                  sink.next(new Response<>(null, httpClientResponse, s));
                                } else {
                                  sink.next(new Response<>(null, httpClientResponse, null));
                                }
                              })
                          .doOnComplete(
                              () -> {
                                if (!responses[0]) {
                                  sink.next(new Response<>(null, httpClientResponse, null));
                                }
                                sink.complete();
                              })
                          .subscribe();
                    }));
  }

  @Override
  public Flux<Response<Void>> agentCheckDeregister(String checkId) {
    return rawClient
        .getHttpClient()
        .put()
        .uri(String.format(V_1_CHECK_DEREGISTER, checkId))
        .response(
            ((httpClientResponse, byteBufFlux) ->
                Flux.create(
                    fluxSink -> {
                      String error = null;
                      if (!httpClientResponse.status().equals(HttpResponseStatus.OK)) {
                        error = "err: ";
                        // TODO: add any text we find from the byteBufFlux to the error message.
                      }
                      fluxSink.next(new Response<>(null, httpClientResponse, error));
                    })));
  }

  @Override
  public Flux<Response<Void>> agentCheckPass(String checkId) {
    return agentCheckPass(checkId, null);
  }

  @Override
  public Flux<Response<Void>> agentCheckPass(String checkId, String note) {
    return getCheckResponseFlux(V_1_TTL_CHECK_PASS, checkId, note);
  }

  @Override
  public Flux<Response<Void>> agentCheckWarn(String checkId) {
    return agentCheckWarn(checkId, null);
  }

  @Override
  public Flux<Response<Void>> agentCheckWarn(String checkId, String note) {
    return getCheckResponseFlux(V_1_TTL_CHECK_WARN, checkId, note);
  }

  @Override
  public Flux<Response<Void>> agentCheckFail(String checkId) {
    return agentCheckFail(checkId, null);
  }

  @Override
  public Flux<Response<Void>> agentCheckFail(String checkId, String note) {
    return getCheckResponseFlux(V_1_TTL_CHECK_FAIL, checkId, note);
  }

  private Flux<Response<Void>> getCheckResponseFlux(
      String baseURLFormat, String checkId, String note) {
    String urlPath = String.format(baseURLFormat, checkId);
    if (note != null) {
      SingleUrlParameters noteParam = new SingleUrlParameters("note", note);
      urlPath = Utils.generateUrl(urlPath, noteParam);
    }
    return rawClient
        .getHttpClient()
        .put()
        .uri(urlPath)
        .response(
            ((httpClientResponse, byteBufFlux) ->
                Flux.create(
                    fluxSink -> {
                      String error = null;
                      if (!httpClientResponse.status().equals(HttpResponseStatus.OK)) {
                        error = "err: ";
                        // TODO: add any text we find from the byteBufFlux to the error message.
                      }
                      fluxSink.next(new Response<>(null, httpClientResponse, error));
                    })));
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
        .response(
            ((httpClientResponse, byteBufFlux) ->
                Flux.create(
                    fluxSink -> {
                      String error = null;
                      if (!httpClientResponse.status().equals(HttpResponseStatus.OK)) {
                        error = "err: ";
                        // TODO: add any text we find from the byteBufFlux to the error message.
                      }
                      fluxSink.next(new Response<>(null, httpClientResponse, error));
                    })));
  }

  @Override
  public Flux<Response<Void>> agentServiceRegister(NewService newService) {
    String servicePayload;
    try {
      servicePayload = serializeJson(newService);
    } catch (JsonProcessingException e) {
      return Flux.just(new Response<>(null, e.getMessage()));
    }

    return rawClient
        .getHttpClient()
        .put()
        .uri(V_1_AGENT_SERVICE_REGISTER)
        .send(Mono.just(Unpooled.copiedBuffer(servicePayload.getBytes())))
        .response(
            ((httpClientResponse, byteBufFlux) ->
                Flux.create(
                    fluxSink -> {
                      String error = null;
                      if (!httpClientResponse.status().equals(HttpResponseStatus.OK)) {
                        error = "err: ";
                        // TODO: add any text we find from the byteBufFlux to the error message.
                      }
                      fluxSink.next(new Response<>(null, httpClientResponse, error));
                    })));
  }

  @Override
  public Flux<Response<Void>> agentServiceDeregister(String serviceId) {
    return rawClient
        .getHttpClient()
        .put()
        .uri(String.format(V_1_AGENT_SERVICE_DEREGISTER, serviceId))
        .response(
            ((httpClientResponse, byteBufFlux) ->
                Flux.create(
                    fluxSink -> {
                      String error = null;
                      if (!httpClientResponse.status().equals(HttpResponseStatus.OK)) {
                        error = "err: ";
                        // TODO: add any text we find from the byteBufFlux to the error message.
                      }
                      fluxSink.next(new Response<>(null, httpClientResponse, error));
                    })));
  }

  @Override
  public Flux<Response<Void>> agentServiceSetMaintenance(
      String serviceId, boolean maintenanceEnabled) {
    return agentServiceSetMaintenance(serviceId, maintenanceEnabled, null);
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
    return rawClient
        .getHttpClient()
        .put()
        .uri(urlPath)
        .response(
            ((httpClientResponse, byteBufFlux) ->
                Flux.create(
                    fluxSink -> {
                      String error = null;
                      if (!httpClientResponse.status().equals(HttpResponseStatus.OK)) {
                        error = "err: ";
                        // TODO: add any text we find from the byteBufFlux to the error message.
                      }
                      fluxSink.next(new Response<>(null, httpClientResponse, error));
                    })));
  }

  private String serializeJson(Object object) throws JsonProcessingException {
    return rawClient.getObjectMapper().writeValueAsString(object);
  }
}
