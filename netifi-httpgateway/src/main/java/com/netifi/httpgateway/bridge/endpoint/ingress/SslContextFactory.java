package com.netifi.httpgateway.bridge.endpoint.ingress;

import com.netifi.broker.info.Broker;
import io.netty.handler.ssl.OpenSsl;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import reactor.core.Exceptions;

import java.security.SecureRandom;

@Component
public class SslContextFactory {
  private static final Logger logger = LogManager.getLogger(Broker.class);

  final SslContext sslContext;
  final SslContext sslServer;

  public SslContextFactory() {
    try {
      final SslProvider sslProvider;
      if (OpenSsl.isAvailable()) {
        logger.info("Native SSL provider is available; will use native provider.");
        sslProvider = SslProvider.OPENSSL_REFCNT;
      } else {
        logger.info("Native SSL provider not available; will use JDK SSL provider.");
        sslProvider = SslProvider.JDK;
      }

      logger.info("Using self-signed SSL certificate.");
      SecureRandom random = new SecureRandom();
      SelfSignedCertificate ssc = new SelfSignedCertificate("netifi.io", random, 1024);
      sslServer =
          SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
              .sslProvider(sslProvider)
              .build();
      sslContext =
          SslContextBuilder.forClient()
              .trustManager(InsecureTrustManagerFactory.INSTANCE)
              .sslProvider(sslProvider)
              .build();
    } catch (Exception e) {
      logger.error("error creating SSL contextes");
      throw Exceptions.propagate(e);
    }
  }

  public SslContext getSslContext() {
    return sslContext;
  }

  public SslContext getSslServer() {
    return sslServer;
  }
}
