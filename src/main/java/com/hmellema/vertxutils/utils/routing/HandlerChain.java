package com.hmellema.vertxutils.routing;

import io.micrometer.core.instrument.config.InvalidConfigurationException;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.Operation;
import io.vertx.ext.web.openapi.RouterBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;

@Builder
public class HandlerChain {
    @Singular
    private final List<Handler<RoutingContext>> handlers;

    @Singular
    private final List<Handler<RoutingContext>> errorHandlers;

    /**
     * Adds all handlers and failure handlers in the chain to the specified
     * openApi operation
     * <p>
     * Note: The ordering of handlers is important as it determines the order of execution
     *
     * @param operation OpenApi operation to configure with the specified handlers
     */
    public void attach(@NonNull final Operation operation) {
        for (final Handler<RoutingContext> handler : handlers) {
            operation.handler(handler);
        }
        for (final Handler<RoutingContext> errorHandler : errorHandlers) {
            operation.failureHandler(errorHandler);
        }
    }


    /**
     * Adds all handlers in the chain as root handlers to the Router Builder
     * <p>
     * Note: RouterBuilder's do not support error handlers so adding an error handler to your chain
     * will break this call with an `InvalidConfigurationException`
     *
     * @param routerBuilder OpenApi router builder to configure with the specified handlers
     */
    public void attach(@NonNull final RouterBuilder routerBuilder) {
        if (!errorHandlers.isEmpty()) {
            throw new InvalidConfigurationException("Cannot configure Error handler on routerBuilder, but error handlers exist on chain");
        }
        for (final Handler<RoutingContext> handler : handlers) {
            routerBuilder.rootHandler(handler);
        }
    }
}
