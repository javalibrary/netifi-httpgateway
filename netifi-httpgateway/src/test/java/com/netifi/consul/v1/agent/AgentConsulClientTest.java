package com.netifi.consul.v1.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.agent.model.Check;
import com.netifi.consul.v1.agent.model.NewCheck;
import com.netifi.consul.v1.agent.model.NewService;
import com.netifi.consul.v1.agent.model.Self;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Map;
import org.junit.ClassRule;
import org.junit.Test;

public class AgentConsulClientTest {

  @ClassRule
  public static final ConsulResource consul =
      new ConsulResource(ConsulStarterBuilder.consulStarter().withConsulVersion("1.5.0").build());

  @Test
  public void getAgentData() {
    AgentConsulClient agentConsulClient =
        new AgentConsulClient(
            ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Response<Self> self = agentConsulClient.getAgentSelf().blockFirst();
    assertNotNull(self);
    assertFalse(self.hasError());
    assertEquals(self.getValue().getConfig().getDatacenter(), "dc1");
  }

  @Test
  public void registerInvalidCheck() {
    NewCheck newCheck = new NewCheck();
    newCheck.setName("testCheck");
    AgentConsulClient agentConsulClient =
        new AgentConsulClient(
            ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Response response = agentConsulClient.agentCheckRegister(newCheck).blockFirst();
    assertNotNull(response);
    assertTrue(response.hasError());
    assertEquals(HttpResponseStatus.BAD_REQUEST, response.getHttpResponseStatus());
    assertTrue(response.getError().contains("TTL must be > 0"));
  }

  @Test
  public void registerCheck() {
    NewCheck newCheck = new NewCheck();
    newCheck.setName("testCheck");
    newCheck.setTtl("3s");
    AgentConsulClient agentConsulClient =
        new AgentConsulClient(
            ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Response response = agentConsulClient.agentCheckRegister(newCheck).blockFirst();
    assertNotNull(response);
    assertFalse(response.hasError());
    assertEquals(HttpResponseStatus.OK, response.getHttpResponseStatus());
  }

  @Test
  public void registerServiceAndCheck() {
    NewService newService1 = new NewService();
    newService1.setName("newService");
    newService1.setId("newService-1");
    newService1.setAddress("localhost");
    newService1.setPort(8080);

    NewService newService2 = new NewService();
    newService2.setName("newService");
    newService2.setId("newService-2");
    newService2.setAddress("localhost");
    newService2.setPort(8080);

    NewCheck newCheck1 = new NewCheck();
    newCheck1.setName("testCheck");
    newCheck1.setId("testCheck-1");
    newCheck1.setTtl("3s");
    newCheck1.setServiceId(newService1.getId());

    NewCheck newCheck2 = new NewCheck();
    newCheck2.setName("testCheck");
    newCheck2.setId("testCheck-2");
    newCheck2.setTtl("3s");
    newCheck2.setServiceId(newService2.getId());

    AgentConsulClient agentConsulClient =
        new AgentConsulClient(
            ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Response newService1Response = agentConsulClient.agentServiceRegister(newService1).blockFirst();
    assertNotNull(newService1Response);
    assertFalse(newService1Response.hasError());

    Response newService2Response = agentConsulClient.agentServiceRegister(newService2).blockFirst();
    assertNotNull(newService2Response);
    assertFalse(newService2Response.hasError());

    Response newCheck1Response = agentConsulClient.agentCheckRegister(newCheck1).blockFirst();
    assertNotNull(newCheck1Response);
    assertFalse(newCheck1Response.hasError());

    Response newCheck2Response = agentConsulClient.agentCheckRegister(newCheck2).blockFirst();
    assertNotNull(newCheck2Response);
    assertFalse(newCheck2Response.hasError());

    Response<Map<String, Check>> agentChecks = agentConsulClient.getAgentChecks().blockFirst();
    assertNotNull(agentChecks);
    assertFalse(agentChecks.hasError());
    assertNotNull(agentChecks.getValue());
    assertEquals(2, agentChecks.getValue().size());
    assertEquals(
        1,
        agentChecks.getValue().keySet().stream()
            .filter(
                key ->
                    agentChecks.getValue().get(key).getServiceId().equals(newCheck1.getServiceId()))
            .count());
    assertEquals(
        1,
        agentChecks.getValue().keySet().stream()
            .filter(
                key ->
                    agentChecks.getValue().get(key).getServiceId().equals(newCheck2.getServiceId()))
            .count());
  }
}
