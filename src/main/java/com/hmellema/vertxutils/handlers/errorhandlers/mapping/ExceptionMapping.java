package com.hmellema.vertxutils.handlers.errorhandlers.mapping;


import io.micrometer.core.instrument.Counter;
import io.vertx.ext.web.RoutingContext;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.function.Function;

@Slf4j
@SuperBuilder
public abstract class ExceptionMapping {
  private static final String DEFAULT_LOG_MESSAGE =
      "Encountered Exception while processing request";

  final int httpCode;
  final boolean printStackTrace;
  @NonNull
  final ExceptionMappingLogLevel logLevel;
  @Nullable
  final String logMessage;
  @Nullable
  final Counter counterToIncrement;
  @Nullable
  final Function<Throwable, String> messageMapper;
  @NonNull Class<? extends Throwable> classToMatch;
  @NonNull Class<?> outputType;

  public boolean isExpectedClassOrChild(Class<? extends Throwable> failureClass) {
    return classToMatch.isAssignableFrom(failureClass);
  }

  public void mapExceptionToResponse(@NonNull final RoutingContext routingContext) {
    Throwable failure = routingContext.failure();
    logFailure(failure);
    incrementCounter();
    routingContext.response()
        .setStatusCode(httpCode)
        .end(getResponseOutput(failure));
  }

  protected abstract String getResponseOutput(Throwable failure);

  private void incrementCounter() {
    if (counterToIncrement != null) {
      counterToIncrement.increment();
    }
  }

  private void logFailure(@NonNull final Throwable failure) {
    final String errorLogMessage = (logMessage != null) ? logMessage : DEFAULT_LOG_MESSAGE;
    logLevel.logError(log, errorLogMessage, failure, printStackTrace);
  }

  protected String mapMessage(@NonNull final Throwable throwable) {
    if (messageMapper != null) {
      return messageMapper.apply(throwable);
    }
    return throwable.getMessage();
  }
}