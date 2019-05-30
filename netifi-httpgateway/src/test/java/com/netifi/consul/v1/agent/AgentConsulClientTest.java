package com.netifi.consul.v1.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.agent.model.NewCheck;
import com.netifi.consul.v1.agent.model.Self;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.junit.ClassRule;
import org.junit.Test;

public class AgentConsulClientTest {

  @ClassRule
  public static final ConsulResource consul =
      new ConsulResource(ConsulStarterBuilder.consulStarter().withConsulVersion("1.5.0").build());

  @Test
  public void getAgentData() {
    AgentConsulClient agentConsulClient = new AgentConsulClient(ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Response<Self> self = agentConsulClient.getAgentSelf().blockLast();
    assertNotNull(self);
    assertEquals(self.getValue().getConfig().getDatacenter(), "dc1");
  }

  @Test
  public void registerCheck() {
    NewCheck newCheck = new NewCheck();
    newCheck.setName("testCheck");
    AgentConsulClient agentConsulClient = new AgentConsulClient(ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Response response = agentConsulClient.agentCheckRegister(newCheck).blockFirst();
    assertNotNull(response);
    assertFalse(response.hasError());
    assertEquals(HttpResponseStatus.ACCEPTED, response.getHttpResponseStatus());
  }
}
