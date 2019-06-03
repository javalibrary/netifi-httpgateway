package com.netifi.httpgateway.bridge.endpoint.ingress;

import org.junit.Assert;
import org.junit.Test;

public class PortManagerTest {
  @Test
  public void testReservePort() {
    PortManager manager = new PortManager(100, 200);

    int foo = manager.reservePort("foo");

    Assert.assertTrue(foo < 200);
    Assert.assertTrue(foo > 100);

    int bar = manager.reservePort("bar");

    Assert.assertNotEquals(foo, bar);

    Assert.assertTrue(manager.isPortReserved(foo));
    Assert.assertTrue(manager.isPortReserved(bar));

    manager.releasePort(foo);

    Assert.assertFalse(manager.isPortReserved(foo));
    Assert.assertTrue(manager.isPortReserved(bar));
  }
}
