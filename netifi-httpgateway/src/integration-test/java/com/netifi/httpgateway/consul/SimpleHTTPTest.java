package com.netifi.httpgateway.consul;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.netifi.httpgateway.junit.BrokerRule;
import com.netifi.httpgateway.junit.HttpGatewayRule;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.StatusClient;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.State;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.orbitz.consul.model.health.HealthCheck;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
import java.io.IOException;
import java.util.List;
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
  @Rule public MockServerRule mockServerRule = new MockServerRule(this);
  private MockServerClient mockServerClient;

  @Rule public BrokerRule brokerRule = new BrokerRule(System.getProperty("docker.broker.image"));

  @Rule
  public HttpGatewayRule httpGatewayRule =
      new HttpGatewayRule(System.getProperty("docker.httpgateway.image"));

  @ClassRule
  public static final ConsulResource consul =
      new ConsulResource(ConsulStarterBuilder.consulStarter().withConsulVersion("1.5.0").build());

  @Test
  public void testMockServer() throws IOException, NotRegisteredException, InterruptedException {
    Consul client =
        Consul.builder().withUrl("http" + "://localhost:" + consul.getHttpPort()).build();
    StatusClient statusClient = client.statusClient();

    assertTrue(mockServerClient.isRunning());
    mockServerClient
        .when(HttpRequest.request().withMethod("GET").withPath("/hello"))
        .respond(HttpResponse.response("hiya!").withStatusCode(200));
    mockServerClient
        .when(HttpRequest.request().withMethod("GET").withPath("/health"))
        .respond(HttpResponse.response("OK").withStatusCode(200));

    assertTrue(statusClient.getLeader() != null && !statusClient.getLeader().equals(""));

    AgentClient agentClient = client.agentClient();
    String serviceID = "mock-server-http-hello-1";
    String serviceName = "mock-server-http-hello";
    Registration service =
        ImmutableRegistration.builder()
            .id(serviceID)
            .name(serviceName)
            .port(mockServerClient.remoteAddress().getPort())
            .address(mockServerClient.remoteAddress().getHostName())
            .check(
                Registration.RegCheck.http(
                    "http:/" + mockServerClient.remoteAddress().toString() + "/health", 5L))
            .build();
    agentClient.register(service);
    TimeUnit.SECONDS.sleep(5L);

    HealthClient healthClient = client.healthClient();
    ConsulResponse<List<HealthCheck>> cr = healthClient.getServiceChecks(serviceName);
    List<HealthCheck> checkList = cr.getResponse();
    assertEquals(1, checkList.size());
    HealthCheck healthCheck = checkList.get(0);
    assertEquals(State.PASS.getName(), healthCheck.getStatus());

    OkHttpClient okHttpClient = new OkHttpClient();
    Request request =
        new Request.Builder()
            .url("http" + ":/" + mockServerClient.remoteAddress().toString() + "/hello")
            .build();
    assertEquals(200, okHttpClient.newCall(request).execute().code());
  }
}
