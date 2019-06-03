package com.netifi.httpgateway.util;

import static com.netifi.httpgateway.util.ProtoUtil.*;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.UnknownFieldSet;
import java.io.File;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.Test;

public class ProtoUtilTest {

  @Test
  public void testFieldToString() throws Exception {
    File file =
        new File(Thread.currentThread().getContextClassLoader().getResource("test.dsc").toURI());

    DescriptorProtos.FileDescriptorSet set =
        DescriptorProtos.FileDescriptorSet.parseFrom(Files.readAllBytes(file.toPath()));

    DescriptorProtos.MethodDescriptorProto descriptor =
        set.getFileList()
            .stream()
            .filter(proto -> "com.netifi.demo.helloworld".equals(proto.getPackage()))
            .flatMap(proto -> proto.getServiceList().stream())
            .filter(
                serviceDescriptorProto ->
                    "HelloWorldService".equals(serviceDescriptorProto.getName()))
            .flatMap(serviceDescriptorProto -> serviceDescriptorProto.getMethodList().stream())
            .filter(
                methodDescriptorProto ->
                    "SayHelloWithMaxConcurrent".equals(methodDescriptorProto.getName()))
            .findFirst()
            .get();

    UnknownFieldSet.Field field =
        descriptor.getOptions().getUnknownFields().getField(NETIFI_METHOD_OPTIONS);

    String s = fieldToString(field, NETIFI_METHOD_OPTIONS__URL);
    Assert.assertEquals("/max", s);
  }

  @Test
  public void testFieldToInteger() throws Exception {
    File file =
        new File(Thread.currentThread().getContextClassLoader().getResource("test.dsc").toURI());

    DescriptorProtos.FileDescriptorSet set =
        DescriptorProtos.FileDescriptorSet.parseFrom(Files.readAllBytes(file.toPath()));

    DescriptorProtos.MethodDescriptorProto descriptor =
        set.getFileList()
            .stream()
            .filter(proto -> "com.netifi.demo.helloworld".equals(proto.getPackage()))
            .flatMap(proto -> proto.getServiceList().stream())
            .filter(
                serviceDescriptorProto ->
                    "HelloWorldService".equals(serviceDescriptorProto.getName()))
            .flatMap(serviceDescriptorProto -> serviceDescriptorProto.getMethodList().stream())
            .filter(
                methodDescriptorProto ->
                    "SayHelloWithMaxConcurrent".equals(methodDescriptorProto.getName()))
            .findFirst()
            .get();

    UnknownFieldSet.Field field =
        descriptor.getOptions().getUnknownFields().getField(NETIFI_METHOD_OPTIONS);

    int maxConcurrency = fieldToInteger(field, NETIFI_METHOD_OPTIONS__MAX_CONCURRENCY);
    Assert.assertEquals(10, maxConcurrency);
  }

  @Test
  public void testFieldToLong() throws Exception {
    File file =
        new File(Thread.currentThread().getContextClassLoader().getResource("test.dsc").toURI());

    DescriptorProtos.FileDescriptorSet set =
        DescriptorProtos.FileDescriptorSet.parseFrom(Files.readAllBytes(file.toPath()));

    DescriptorProtos.MethodDescriptorProto descriptor =
        set.getFileList()
            .stream()
            .filter(proto -> "com.netifi.demo.helloworld".equals(proto.getPackage()))
            .flatMap(proto -> proto.getServiceList().stream())
            .filter(
                serviceDescriptorProto ->
                    "HelloWorldService".equals(serviceDescriptorProto.getName()))
            .flatMap(serviceDescriptorProto -> serviceDescriptorProto.getMethodList().stream())
            .filter(
                methodDescriptorProto ->
                    "SayHelloWithMaxConcurrent".equals(methodDescriptorProto.getName()))
            .findFirst()
            .get();

    UnknownFieldSet.Field field =
        descriptor.getOptions().getUnknownFields().getField(NETIFI_METHOD_OPTIONS);

    long timeoutMillis = fieldToLong(field, NETIFI_METHOD_OPTIONS__TIMEOUT_MILLIS);
    Assert.assertEquals(2000, timeoutMillis);
  }
}
