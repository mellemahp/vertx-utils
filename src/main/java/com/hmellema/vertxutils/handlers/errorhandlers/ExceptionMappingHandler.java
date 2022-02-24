package com.hmellema.vertxutils.handlers.errorhandlers;

import com.hmellema.vertxutils.handlers.errorhandlers.mapping.JsonExceptionMapping;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;

@Builder
public class ExceptionMappingHandler implements Handler<RoutingContext> {

  @Singular
  private final List<JsonExceptionMapping> mappings;

  @NonNull
  private final JsonExceptionMapping defaultMapping;

  @Override
  public void handle(RoutingContext routingContext) {
    Class<? extends Throwable> failureClass = routingContext.failure().getClass();
    JsonExceptionMapping mapping = mappings.stream()
        .filter(excMap -> excMap.isExpectedClassOrChild(failureClass))
        .findFirst()
        .orElse(defaultMapping);
    mapping.mapExceptionToResponse(routingContext);
  }
}
