package com.hmellema.vertxutils.dagger;

import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import javax.inject.Provider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DaggerVerticleFactory implements VerticleFactory {

  private static final String PREFIX = "Dagger";

  @NonNull
  private final Map<String, Provider<Verticle>> verticleMap;

  @Override
  public String prefix() {
    return PREFIX;
  }

  /**
   * Creates a verticle from a Dagger Impl of that verticle allowing 
   * for injection.
   * @param verticleName Name of verticle to create
   * @param classLoader classloader to use for creating verticle
   * @param promise promise for callable verticle
   */
  @Override
  public void createVerticle(
      final String verticleName,
      final ClassLoader classLoader,
      final Promise<Callable<Verticle>> promise
  ) {
    final String identifier = VerticleFactory.removePrefix(verticleName);
    try {
      Verticle verticle = Optional
          .ofNullable(verticleMap.get(identifier))
          .orElseThrow(
            () ->
            new NoSuchElementException(
              "No provider for verticle type found for: " + verticleName
            )
        )
          .get();
      promise.complete(() -> verticle);
    } catch (Exception e) {
      promise.fail(e);
    }
  }
}
