/**
 * Copyright 2018 Netifi Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netifi.httpgateway.rsocket;

import com.netifi.broker.BrokerClient;
import com.netifi.common.tags.Tag;
import com.netifi.common.tags.Tags;
import com.netifi.httpgateway.config.HTTPGatewaySettings;
import com.netifi.httpgateway.util.HttpUtil;
import io.netty.handler.codec.http.HttpHeaders;
import io.rsocket.RSocket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerClientRSocketSupplier implements RSocketSupplier {
  private final BrokerClient brokerClient;
  private final ConcurrentHashMap<String, RSocket> rsockets;

  @Autowired
  public BrokerClientRSocketSupplier(HTTPGatewaySettings settings) {
    BrokerClient.TcpBuilder builder =
        BrokerClient.tcp()
            .accessToken(settings.getAccess().getToken())
            .accessKey(settings.getAccess().getKey());

    if (settings.getSsl().isDisabled()) {
      builder = builder.disableSsl();
    }

    builder =
        builder
            .host(settings.getBroker().getHostname())
            .port(settings.getBroker().getPort())
            .group(settings.getGroup());

    if (settings.getDestination() != null && settings.getDestination().isEmpty()) {
      builder = builder.destination(settings.getDestination());
    }

    brokerClient = builder.build();

    this.rsockets = new ConcurrentHashMap<>();
  }

  @Override
  public RSocket apply(String rSocketKey, HttpHeaders headers) {
    String overrideGroup = headers.get(HttpUtil.OVERRIDE_GROUP);
    String overrideDestination = headers.get(HttpUtil.OVERRIDE_DESTINATION);
    List<String> allAsString = headers.getAllAsString(HttpUtil.OVERRIDE_TAG);

    Tags tags = toTags(allAsString);

    if (overrideGroup != null && !overrideGroup.isEmpty()) {
      if (overrideDestination != null && !overrideDestination.isEmpty()) {
        return rsockets.computeIfAbsent(
            overrideGroup, g -> brokerClient.destination(overrideDestination, overrideGroup));
      }

      return rsockets.computeIfAbsent(
          overrideGroup, s -> brokerClient.groupServiceSocket(overrideGroup, tags));
    }
    return rsockets.computeIfAbsent(rSocketKey, s -> brokerClient.groupServiceSocket(rSocketKey, tags));
  }

  private Tags toTags(List<String> allAsString) {
    if (allAsString == null || allAsString.isEmpty()) {
      return Tags.empty();
    } else {
      List<Tag> collect =
          allAsString
              .stream()
              .map(
                  s -> {
                    String[] split = s.split(":");
                    return Tag.of(split[0], split[1]);
                  })
              .collect(Collectors.toList());
      return Tags.of(collect);
    }
  }
}
