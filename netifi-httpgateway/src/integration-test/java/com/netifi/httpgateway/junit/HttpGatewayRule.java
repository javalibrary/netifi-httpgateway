package com.netifi.httpgateway.junit;

import com.arakelian.docker.junit.DockerRule;
import com.arakelian.docker.junit.model.ImmutableDockerConfig;

public class HttpGatewayRule extends DockerRule {
  public HttpGatewayRule(String imageName) {
    super(
        ImmutableDockerConfig.builder()
            .name("netifi-httpgateway-under-test")
            .image(imageName)
            .ports("7001", "8001", "8101")
            .addContainerConfigurer(
                builder -> {
                  builder.env(
                      "NETIFI_HTTPGATEWAY_OPTS="
                          + "'-Dnetifi.authentication.0.accessKey=9007199254740991' "
                          + "'-Dnetifi.authentication.0.accessToken=kTBDVtfRBO4tHOnZzSyY5ym2kfY=' "
                          + "'-Dnetifi.broker.admin.accessKey=9007199254740991' "
                          + "'-Dnetifi.broker.admin.accessToken=kTBDVtfRBO4tHOnZzSyY5ym2kfY=' ");
                })
            .build());
  }
}
