package com.netifi.httpgateway.rsocket.endpoint.source;

import org.junit.Ignore;

@Ignore
public class FileSystemEndpointSourceTest {
  /*
  @Test
  public void testPathToByteString() throws Exception {

    File file =
        new File(Thread.currentThread().getContextClassLoader().getResource("test.dsc").toURI());

    File tempDir = Files.createTempDir();
    File tempFile = File.createTempFile("testPathToByteString", "dsc", tempDir);

    FileUtils.copyFile(file, tempFile);

    FileSystemEndpointSource source = new FileSystemEndpointSource(tempDir.getAbsolutePath());

    ByteString bytes = source.pathToByteString(tempFile.toPath());
    byte[] fromByteString = bytes.toByteArray();

    InputStream stream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream("test.dsc");

    byte[] fromStream = IOUtils.readFully(stream, fromByteString.length);

    Assert.assertArrayEquals(fromByteString, fromStream);
  }

  @Test
  public void testStreamExistingFiles() throws Exception {
    File file =
        new File(Thread.currentThread().getContextClassLoader().getResource("test.dsc").toURI());

    File tempDir = Files.createTempDir();
    File tempFile = File.createTempFile("testPathToByteString", ".dsc", tempDir);

    FileUtils.copyFile(file, tempFile);

    FileSystemEndpointSource source = new FileSystemEndpointSource(tempDir.getAbsolutePath());

    ProtoDescriptor protoDescriptor =
        source
            .streamProtoDescriptors(Empty.getDefaultInstance(), Unpooled.EMPTY_BUFFER)
            .take(1)
            .blockLast(Duration.ofSeconds(10));

    Assert.assertEquals(tempFile.getAbsolutePath(), protoDescriptor.getName());

    ByteString bytes = source.pathToByteString(tempFile.toPath());

    Assert.assertTrue(bytes.equals(protoDescriptor.getDescriptorBytes()));

    Assert.assertEquals(ProtoDescriptor.EventType.ADD, protoDescriptor.getType());
  }

  @Test
  public void testEmitsAddEvents() throws Exception {
    File file =
        new File(Thread.currentThread().getContextClassLoader().getResource("test.dsc").toURI());

    File tempFile = File.createTempFile("testEmitsAddEvents", ".dsc");
    FileUtils.copyFile(file, tempFile);

    File tempDir = Files.createTempDir();

    FileSystemEndpointSource source = new FileSystemEndpointSource(tempDir.getAbsolutePath());

    StepVerifier.create(
            source.streamProtoDescriptors(Empty.getDefaultInstance(), Unpooled.EMPTY_BUFFER))
        .then(
            () -> {
              try {
                FileUtils.copyFileToDirectory(tempFile, tempDir);
              } catch (Exception e) {
                throw Exceptions.propagate(e);
              }
            })
        .assertNext(
            protoDescriptor ->
                Assert.assertEquals(ProtoDescriptor.EventType.ADD, protoDescriptor.getType()))
        .thenCancel()
        .verify();
  }

  @Test
  public void testEmitsDeleteEvents() throws Exception {
    File file =
      new File(Thread.currentThread().getContextClassLoader().getResource("test.dsc").toURI());

    File tempDir = Files.createTempDir();
    File tempFile = File.createTempFile("testEmitsDeleteEvents", ".dsc", tempDir);

    FileUtils.copyFile(file, tempFile);

    FileSystemEndpointSource source = new FileSystemEndpointSource(tempDir.getAbsolutePath());

    StepVerifier.create(
      source.streamProtoDescriptors(Empty.getDefaultInstance(), Unpooled.EMPTY_BUFFER))
      .assertNext(
        protoDescriptor ->
          Assert.assertEquals(ProtoDescriptor.EventType.ADD, protoDescriptor.getType()))
      .then(
        () -> {
          try {
            FileUtils.forceDelete(tempFile);
          } catch (Exception e) {
            throw Exceptions.propagate(e);
          }
        })
      .assertNext(
        protoDescriptor ->
          Assert.assertEquals(ProtoDescriptor.EventType.DELETE, protoDescriptor.getType()))
      .thenCancel()
      .verify();
  }*/
}
