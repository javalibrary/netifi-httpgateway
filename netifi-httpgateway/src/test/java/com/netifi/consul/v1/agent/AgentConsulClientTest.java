package com.netifi.consul.v1.agent;

import static org.junit.Assert.assertEquals;

import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.agent.model.Self;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
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
    assertEquals(self.getValue().getConfig().getDatacenter(), "dc1");
  }
}
