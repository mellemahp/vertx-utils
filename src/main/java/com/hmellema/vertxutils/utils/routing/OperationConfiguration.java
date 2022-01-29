package com.hmellema.vertxutils.routing;

import com.hmellema.vertxutils.handlers.contenttype.ContentTypeHandler;
import com.hmellema.vertxutils.handlers.headers.HeaderHandler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.Operation;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.ValidationHandler;
import java.util.List;
import javax.xml.validation.ValidatorHandler;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

/**
 * This class is a helper class to speed up the process of configuring
 * a rest api Operation. It is initialized with the set of handlers you
 * want to use on that Operation via a Builder class and then used to configure
 * the relevant rest operation.
 */
@Builder(builderClassName = "LombokBuilder")
public class OperationConfiguration {

  @NonNull
  private final ContentTypeHandler contentTypeHandler;

  @Singular
  private final List<HeaderHandler> headerHandlers;

  @Singular
  private final List<ValidationHandler> validators;

  @Singular
  private final List<Handler<RoutingContext>> handlers;

  @Singular
  private final List<Handler<RoutingContext>> failureHandlers;

  /**
  * Adds all configured handlers to the specified rest operation.
  * @param operation operation to configure handlers for
  */
  public void configure(@NonNull final Operation operation) {
    operation.handler(contentTypeHandler);
    addHeaderHandlers(operation);
    addValidators(operation);
    addBaseHandlers(operation);
    addFailureHandlers(operation);
  }

  private void addHeaderHandlers(@NonNull final Operation operation) {
    for (HeaderHandler headerHandler : headerHandlers) {
      operation.handler(headerHandler);
    }
  }

  private void addValidators(@NonNull final Operation operation) {
    for (ValidationHandler validator : validators) {
      operation.handler(validator);
    }
  }

  private void addBaseHandlers(@NonNull final Operation operation) {
    for (Handler<RoutingContext> handler : handlers) {
      operation.handler(handler);
    }
  }

  private void addFailureHandlers(@NonNull final Operation operation) {
    for (Handler<RoutingContext> failureHandler : failureHandlers) {
      operation.failureHandler(failureHandler);
    }
  }
}
