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
package com.netifi.httpgateway.config;

import com.netifi.spring.boot.BrokerClientProperties;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.stereotype.Component;

@Component
public class HTTPGatewaySettings extends BrokerClientProperties {

  @Valid
  private GatewayProperties gateway = new GatewayProperties();

  public static final class GatewayProperties {
    private Integer lowPort = 20_000;
    private Integer highPort = 60_000;

    private String bindAddress = "0.0.0.0";

    @Min(value = 0)
    @Max(value = 65_535)
    private Integer bindPort = 8080;

    private String group = "netifi.httpgateway";

    @Valid
    private DescriptorProperties descriptor = new DescriptorProperties();

    public DescriptorProperties getDescriptor() {
      return descriptor;
    }

    public void setDescriptor(
        DescriptorProperties descriptor) {
      this.descriptor = descriptor;
    }

    public String getBindAddress() {
      return bindAddress;
    }

    public void setBindAddress(String bindAddress) {
      this.bindAddress = bindAddress;
    }

    public Integer getBindPort() {
      return bindPort;
    }

    public void setBindPort(Integer bindPort) {
      this.bindPort = bindPort;
    }

    public String getGroup() {
      return group;
    }

    public void setGroup(String group) {
      this.group = group;
    }

    public Integer getLowPort() {
      return lowPort;
    }

    public void setLowPort(Integer lowPort) {
      this.lowPort = lowPort;
    }

    public Integer getHighPort() {
      return highPort;
    }

    public void setHighPort(Integer highPort) {
      this.highPort = highPort;
    }

    public static final class DescriptorProperties {
      private String directory = "/tmp/dsc";

      public String getDirectory() {
        return directory;
      }

      public void setDirectory(String directory) {
        this.directory = directory;
      }
    }
  }

  public GatewayProperties getGateway() {
    return gateway;
  }

  public void setGateway(GatewayProperties gateway) {
    this.gateway = gateway;
  }
}
