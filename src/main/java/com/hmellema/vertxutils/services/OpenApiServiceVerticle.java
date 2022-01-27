package com.hmellema.vertxutils.services;

import com.hmellema.vertxutils.handlers.requestid.RequestIdHandler;
import com.hmellema.vertxutils.routing.OpenApiRouterConfiguration;
import com.hmellema.vertxutils.routing.OperationConfiguration;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.openapi.RouterBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OpenApiServiceVerticle extends AbstractVerticle {

  @NonNull
  private final HttpServerOptions serverOptions;

  @NonNull
  private final OpenApiRouterConfiguration routerConfiguration;

  @NonNull
  private final String openApiSpecLocation;

  @NonNull
  private final String serviceName;

  @Override
  public void start(final @NonNull Promise<Void> startPromise)
    throws Exception {
    RouterBuilder
      .create(vertx, openApiSpecLocation)
      .onSuccess(
        routerBuilder -> {
          log.info("Configuring {} Service", serviceName);
          routerConfiguration.configure(routerBuilder);

          log.info("Deploying {} Service", serviceName);
          Router router = routerBuilder.createRouter();
          deployHttpServerWithRouter(router, startPromise);
        }
      )
      .onFailure(Throwable::printStackTrace);
  }

  private void deployHttpServerWithRouter(
    @NonNull final Router router,
    @NonNull final Promise<Void> startPromise
  ) {
    vertx
      .createHttpServer(serverOptions)
      .requestHandler(router)
      .listen(
        serverOptions.getPort(),
        http -> httpListenHandler(http, startPromise)
      );
  }

  private void httpListenHandler(
    @NonNull final AsyncResult<HttpServer> http,
    @NonNull final Promise<Void> startPromise
  ) {
    if (http.succeeded()) {
      startPromise.complete();
      log.info(
        "HTTP server for service {} started on port {}",
        serviceName,
        serverOptions.getPort()
      );
    } else {
      startPromise.fail(http.cause());
    }
  }
}
