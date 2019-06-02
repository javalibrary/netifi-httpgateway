package com.netifi.httpgateway.bridge.endpoint.egress.pool;

import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpoint;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactory;
import com.netifi.httpgateway.bridge.endpoint.egress.EgressEndpointFactorySupplier;
import io.rsocket.Closeable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.retry.Retry;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A pool of factories that are used to create connections to services
 *
 * @param <E>
 */
public abstract class EgressEndpointFactoryPool<
        E extends EgressEndpoint, F extends EgressEndpointFactory<E>>
    extends AtomicBoolean implements Closeable, Disposable {
  private final Logger logger = LogManager.getLogger(EgressEndpointFactoryPool.class);
  private final MonoProcessor<Void> onClose;

  EgressEndpointFactoryPool() {
    this.onClose = MonoProcessor.create();
  }

  void init(EgressEndpointFactorySupplier<E, F> egressEndpointFactorySupplier) {
    Disposable disposable =
        egressEndpointFactorySupplier
            .get()
            .doOnNext(this::handleEndpointFactorySet)
            .doOnError(throwable -> logger.error("error streaming endpoint factories", throwable))
            .retryWhen(
                Retry.any()
                    .exponentialBackoffWithJitter(Duration.ofMillis(100), Duration.ofSeconds(30)))
            .subscribe();
    onClose.doFinally(s -> disposable.dispose()).subscribe();
  }

  public abstract void handleEndpointFactorySet(Set<F> incoming);

  /**
   * Returns a factory from the pool
   *
   * @return Optional of a factory that can return Optional.empty if there is no factories present
   */
  public abstract Optional<F> lease();

  /**
   * Returns a factory back to the pool for re-use
   *
   * @param endpointFactory a factory that is to be re-used
   */
  public abstract void release(F endpointFactory);

  /**
   * Current number of factories available to be leased
   *
   * @return size of available factories in the pool
   */
  public abstract int size();

  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean isDisposed() {
    return get();
  }

  @Override
  public Mono<Void> onClose() {
    return onClose;
  }

  @Override
  public void dispose() {
    if (compareAndSet(false, true)) {
      onClose.onComplete();
    }
  }
}
