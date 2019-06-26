package com.netifi.consul.v1.agent;

import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.agent.model.AgentCheck;
import com.netifi.consul.v1.agent.model.AgentCheckRegistration;
import com.netifi.consul.v1.agent.model.AgentService;
import com.netifi.consul.v1.agent.model.AgentServiceRegistration;
import com.netifi.consul.v1.agent.model.CheckUpdate;
import com.netifi.consul.v1.agent.model.Self;
import java.util.Map;
import reactor.core.publisher.Flux;

public interface AgentClient {
  // https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L313
  Flux<Response<Self>> getAgentSelf();

  // https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L389
  Flux<Response<Map<String, AgentCheck>>> getAgentChecks();

  // https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L412
  Flux<Response<Map<String, AgentService>>> getAgentServices();

  // https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L439
  Flux<Response<AgentService>> getAgentService(String serviceId);

  // https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L698
  Flux<Response<Void>> agentCheckRegister(AgentCheckRegistration agentCheckRegistration);

  // https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L711
  Flux<Response<Void>> agentCheckDeregister(String checkId);

  // https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L632
  Flux<Response<Void>> agentCheckUpdate(String checkId, CheckUpdate checkUpdate);

  // https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L573
  Flux<Response<Void>> agentServiceRegister(AgentServiceRegistration agentServiceRegistration);

  // https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L586
  Flux<Response<Void>> agentServiceDeregister(String serviceId);

  // https://github.com/hashicorp/consul/blob/v1.5.1/api/agent.go#L843-L868
  Flux<Response<Void>> agentServiceSetMaintenance(
      String serviceId, boolean maintenanceEnabled, String reason);
}
