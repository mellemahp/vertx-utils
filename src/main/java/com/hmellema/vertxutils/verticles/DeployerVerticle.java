package com.hmellema.vertxutils.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * A simple Verticle that deploys a list of other verticles
 * 
 * Deploy this verticle with vertx to trigger the deployment 
 * of all verticles provided in the verticle list 
 * 
 */
@Slf4j
@RequiredArgsConstructor
public class DeployerVerticle extends AbstractVerticle {

  @NonNull
  private final List<Verticle> verticles;
    
  
  @Override
  public void start(final @NonNull Promise<Void> startPromise)
      throws Exception {
    deployAllDependentVerticles().onComplete(res -> {
      if (res.failed()) {
        log.error("One or more verticles failed to deploy", res.cause());
        startPromise.fail(res.cause());
      } else {
        log.info("Successfully deployed base verticles");
        startPromise.complete();
      }
    });
  }

  private CompositeFuture deployAllDependentVerticles() {
    return CompositeFuture.all(verticles.stream()
        .map(vert -> vertx.deployVerticle(vert))
        .collect(Collectors.toList())
    );
  }
}
