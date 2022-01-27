package com.hmellema.vertxutils.routing;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.Operation;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

/**
 * This class is a helper class to speed up the process of configuring
 * a rest api Router. You can initialize it via Builder with a set of named 
 * rest operations to configure as well as any number of global handlers.
 */
@Builder
public class OpenApiRouterConfiguration {

  @NonNull
  private final Map<String, OperationConfiguration> operationMap;

  @Singular
  private final List<Handler<RoutingContext>> globalHandlers;
  
  /**
 * Configure a RouterBuilder with the specified global handlers and 
 * operation configurations.
 * @param routerBuilder routerBuilder to configure
 */
  public void configure(@NonNull final RouterBuilder routerBuilder) {
    configureGlobalHandlers(routerBuilder);
    configureOperations(routerBuilder);
  }

  private void configureGlobalHandlers(@NonNull final RouterBuilder routerBuilder) {
    for (Handler<RoutingContext> handler: globalHandlers) {
      routerBuilder.rootHandler(handler);
    }
  }

  private void configureOperations(@NonNull final RouterBuilder routerBuilder) {
    for (Map.Entry<String, OperationConfiguration> operationEntry : operationMap.entrySet()) {
      String operationName = operationEntry.getKey();
      OperationConfiguration operationConfiguration = operationEntry.getValue();
      Operation operation = routerBuilder.operation(operationName);
      operationConfiguration.configure(operation);
    }
  }
}
