package com.netifi.httpgateway.rsocket.endpoint;

import com.google.protobuf.Empty;
import com.netifi.httpgateway.endpoint.source.BlockingEndpointSource;
import com.netifi.httpgateway.endpoint.source.ProtoDescriptor;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class DummyClass implements BlockingEndpointSource {
  @Override
  public Iterable<ProtoDescriptor> streamProtoDescriptors(Empty message, ByteBuf metadata) {
    return null;
  }
}
