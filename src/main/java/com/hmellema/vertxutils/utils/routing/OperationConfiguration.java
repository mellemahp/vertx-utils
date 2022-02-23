package com.hmellema.vertxutils.routing;

import io.vertx.ext.web.openapi.Operation;
import io.vertx.ext.web.openapi.RouterBuilder;
import lombok.Builder;
import lombok.NonNull;

/**
 * This class is a helper class to speed up the process of configuring
 * a rest api Operation. It is initialized with the chain of handlers you
 * want to use on that Operation via a Builder class and then used to configure
 * the relevant rest operation via the OpenApi router
 */
@Builder
public class OperationConfiguration {

    @NonNull
    private final String operationName;

    @NonNull
    private final HandlerChain handlerChain;

    /**
     * Adds all configured handlers to the specified rest operation.
     *
     * @param routerBuilder routerBuilder to configure operation on
     */
    public void configure(@NonNull final RouterBuilder routerBuilder) {
        Operation operation = routerBuilder.operation(operationName);
        handlerChain.attach(operation);
    }
}
