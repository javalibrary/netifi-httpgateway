package com.netifi.consul.v1.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.agent.model.AgentCheck;
import com.netifi.consul.v1.agent.model.AgentCheckRegistration;
import com.netifi.consul.v1.agent.model.AgentService;
import com.netifi.consul.v1.agent.model.AgentServiceRegistration;
import com.netifi.consul.v1.agent.model.Self;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;

public class AgentConsulClientTest {

  @Rule
  public ConsulResource consul =
      new ConsulResource(ConsulStarterBuilder.consulStarter().withConsulVersion("1.5.0").build());

  @Test
  public void getAgentData() {
    AgentConsulClient agentConsulClient =
        new AgentConsulClient(
            ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Response<Self> self = agentConsulClient.getAgentSelf().blockFirst();
    assertNotNull(self);
    assertFalse(self.hasError());
    assertEquals(self.getValue().getSelfConfig().getDatacenter(), "dc1");
  }

  @Test
  public void registerInvalidCheck() {
    AgentCheckRegistration agentCheckRegistration =
        AgentCheckRegistration.newBuilder().withName("testCheck").build();
    AgentConsulClient agentConsulClient =
        new AgentConsulClient(
            ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Response response = agentConsulClient.agentCheckRegister(agentCheckRegistration).blockFirst();
    assertNotNull(response);
    assertTrue(response.hasError());
    assertEquals(HttpResponseStatus.BAD_REQUEST, response.getHttpResponseStatus());
    assertTrue(response.getError().contains("TTL must be > 0"));
  }

  @Test
  public void registerCheck() {
    AgentCheckRegistration agentCheckRegistration =
        AgentCheckRegistration.newBuilder().withName("testCheck").withTtl("3s").build();
    AgentConsulClient agentConsulClient =
        new AgentConsulClient(
            ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Response response = agentConsulClient.agentCheckRegister(agentCheckRegistration).blockFirst();
    assertNotNull(response);
    assertFalse(response.hasError());
    assertEquals(HttpResponseStatus.OK, response.getHttpResponseStatus());
  }

  @Test
  public void registerServiceAndCheck() {
    AgentServiceRegistration agentServiceRegistration1 =
        AgentServiceRegistration.newBuilder()
            .withName("newService")
            .withId("newService-1")
            .withAddress("localhost")
            .withPort(8080)
            .build();
    AgentServiceRegistration agentServiceRegistration2 =
        AgentServiceRegistration.newBuilder()
            .withName("newService")
            .withId("newService-2")
            .withAddress("localhost")
            .withPort(8080)
            .build();

    AgentCheckRegistration agentCheckRegistration1 =
        AgentCheckRegistration.newBuilder()
            .withId("testCheck-1")
            .withServiceId(agentServiceRegistration1.getId())
            .withName("testCheck")
            .withTtl("3s")
            .build();
    AgentCheckRegistration agentCheckRegistration2 =
        AgentCheckRegistration.newBuilder()
            .withId("testCheck-2")
            .withServiceId(agentServiceRegistration2.getId())
            .withName("testCheck")
            .withTtl("3s")
            .build();

    AgentConsulClient agentConsulClient =
        new AgentConsulClient(
            ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Response newService1Response =
        agentConsulClient.agentServiceRegister(agentServiceRegistration1).blockFirst();
    assertNotNull(newService1Response);
    assertFalse(newService1Response.hasError());

    Response newService2Response =
        agentConsulClient.agentServiceRegister(agentServiceRegistration2).blockFirst();
    assertNotNull(newService2Response);
    assertFalse(newService2Response.hasError());

    Response newCheck1Response =
        agentConsulClient.agentCheckRegister(agentCheckRegistration1).blockFirst();
    assertNotNull(newCheck1Response);
    assertFalse(newCheck1Response.hasError());

    Response newCheck2Response =
        agentConsulClient.agentCheckRegister(agentCheckRegistration2).blockFirst();
    assertNotNull(newCheck2Response);
    assertFalse(newCheck2Response.hasError());

    Response<Map<String, AgentCheck>> agentChecks = agentConsulClient.getAgentChecks().blockFirst();
    assertNotNull(agentChecks);
    assertFalse(agentChecks.hasError());
    assertNotNull(agentChecks.getValue());
    assertEquals(2, agentChecks.getValue().size());
    assertEquals(
        1,
        agentChecks.getValue().keySet().stream()
            .filter(
                key ->
                    agentChecks
                        .getValue()
                        .get(key)
                        .getServiceId()
                        .equals(agentServiceRegistration1.getId()))
            .count());
    assertEquals(
        1,
        agentChecks.getValue().keySet().stream()
            .filter(
                key ->
                    agentChecks
                        .getValue()
                        .get(key)
                        .getServiceId()
                        .equals(agentServiceRegistration2.getId()))
            .count());

    Response<Map<String, AgentService>> serviceList =
        agentConsulClient.getAgentServices().blockFirst();
    assertNotNull(serviceList);
    assertFalse(serviceList.hasError());
    assertNotNull(serviceList.getValue());
    assertEquals(2, serviceList.getValue().size());
  }
}
