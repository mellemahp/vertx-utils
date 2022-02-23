package com.hmellema.vertxutils.routing;

import io.vertx.ext.web.openapi.RouterBuilder;
import lombok.Builder;
import lombok.NonNull;

import java.util.Set;

/**
 * This class is a helper class to speed up the process of configuring
 * a rest api Router. You can initialize it via Builder with a set of named
 * rest operations to configure as well as a chain of global handlers that will execute
 * before the operation.
 */
@Builder
public class OpenApiRouterConfiguration {

    @NonNull
    private final Set<OperationConfiguration> operationSet;

    @NonNull
    private final HandlerChain globalHandlers;

    /**
     * Configure a RouterBuilder with the specified global handlers and
     * operation configurations.
     *
     * @param routerBuilder routerBuilder to configure
     */
    public void configure(@NonNull final RouterBuilder routerBuilder) {
        globalHandlers.attach(routerBuilder);
        for (final OperationConfiguration operationConfiguration : operationSet) {
            operationConfiguration.configure(routerBuilder);
        }
    }
}
