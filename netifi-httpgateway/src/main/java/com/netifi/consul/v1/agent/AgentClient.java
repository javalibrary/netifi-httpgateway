package com.netifi.consul.v1.agent;

import com.netifi.consul.v1.Response;
import com.netifi.consul.v1.agent.model.Check;
import com.netifi.consul.v1.agent.model.NewCheck;
import com.netifi.consul.v1.agent.model.NewService;
import com.netifi.consul.v1.agent.model.Self;
import com.netifi.consul.v1.agent.model.Service;
import java.util.List;
import reactor.core.publisher.Flux;

public interface AgentClient {
  Flux<Response<Self>> getAgentSelf();
  Flux<Response<List<Check>>> getAgentChecks();
  Flux<Response<List<Service>>> getAgentServices();
  Flux<Response<Void>> agentCheckRegister(NewCheck newCheck);
  Flux<Response<Void>> agentCheckDeregister(String checkId);
  Flux<Response<Void>> agentCheckPass(String checkId);
  Flux<Response<Void>> agentCheckPass(String checkId, String note);
  Flux<Response<Void>> agentCheckWarn(String checkId);
  Flux<Response<Void>> agentCheckWarn(String checkId, String note);
  Flux<Response<Void>> agentCheckFail(String checkId);
  Flux<Response<Void>> agentCheckFail(String checkId, String note);
  Flux<Response<Void>> agentServiceRegister(NewService newService);
  Flux<Response<Void>> agentServiceDeregister(String serviceId);
  Flux<Response<Void>> agentServiceSetMaintenance(String serviceId, boolean maintenanceEnabled);
  Flux<Response<Void>> agentServiceSetMaintenance(String serviceId, boolean maintenanceEnabled, String reason);
}
