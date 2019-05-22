package com.netifi.httpgateway.consul;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.StatusClient;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.pszymczyk.consul.junit.ConsulResource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

public class SimpleHTTPTest {
  Logger logger = LogManager.getLogger();
  @Rule
  public MockServerRule mockServerRule = new MockServerRule(this);
  private MockServerClient mockServerClient;

  @ClassRule
  public static final ConsulResource consul = new ConsulResource();

  @Test
  public void testMockServer() throws IOException, NotRegisteredException, InterruptedException {
    Consul client = Consul.builder().withUrl("http"
        + "://localhost:" + consul.getHttpPort()).build();
    StatusClient statusClient = client.statusClient();

    assertTrue(mockServerClient.isRunning());
    mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/hello")).respond(
        HttpResponse.response("hiya!").withStatusCode(200));
    mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/health")).respond(
        HttpResponse.response("OK").withStatusCode(200));

    assertTrue(statusClient.getLeader() != null && !statusClient.getLeader().equals(""));

    AgentClient agentClient = client.agentClient();
    String serviceID = "mock-server-http-hello-1";
    Registration service = ImmutableRegistration.builder()
        .id(serviceID)
        .name("mock-server-http-hello")
        .port(mockServerClient.remoteAddress().getPort())
        .address(mockServerClient.remoteAddress().getHostName())
        .check(Registration.RegCheck.http("http:/" + mockServerClient.remoteAddress().toString() + "/health", 5L))
        .build();
    agentClient.register(service);
//    agentClient.pass(serviceID);
//    Flux.interval(Duration.ofSeconds(2)).subscribe(tick ->  {
//      try {
//        agentClient.pass(serviceID);
//      } catch (NotRegisteredException e) {
//        e.printStackTrace();
//      }
//    });

    OkHttpClient okHttpClient = new OkHttpClient();
    Request request = new Request.Builder().url("http"
        + ":/" + mockServerClient.remoteAddress().toString() + "/hello").build();
    assertEquals( 200, okHttpClient.newCall(request).execute().code());
    logger.error("HELLO WORLD");
    //TimeUnit.MINUTES.sleep(5);
  }
}
