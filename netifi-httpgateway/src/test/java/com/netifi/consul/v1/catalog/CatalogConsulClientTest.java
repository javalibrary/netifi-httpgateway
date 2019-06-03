package com.netifi.consul.v1.catalog;

import static org.junit.Assert.*;

import com.netifi.consul.v1.ConsulRawClient;
import com.netifi.consul.v1.Response;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
import java.util.List;
import java.util.Map;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import reactor.core.publisher.Flux;

@Ignore
public class CatalogConsulClientTest {

  @Rule
  public ConsulResource consul =
      new ConsulResource(ConsulStarterBuilder.consulStarter().withConsulVersion("1.5.1").build());

  @Test
  public void testForDefaultCatalogServices() {
    CatalogConsulClient catalogConsulClient =
        new CatalogConsulClient(
            ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
    Flux<Response<Map<String, List<String>>>> responseFlux =
        catalogConsulClient.getCatalogServices(CatalogServicesRequest.newBuilder().build());
    Response<Map<String, List<String>>> res = responseFlux.blockFirst();
    Map<String, List<String>> services = res.getValue();
    assertNotNull(services);
    assertEquals(1, services.size());
    assertTrue(services.get("consul").isEmpty());
  }

  @Test
  public void testForAddingService() {
    CatalogConsulClient catalogConsulClient =
        new CatalogConsulClient(
            ConsulRawClient.Builder.builder().withPort(consul.getHttpPort()).build());
  }
}
