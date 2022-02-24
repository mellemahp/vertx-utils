package com.hmellema.vertxutils.handlers.errorhandlers.mapping;

import com.hmellema.vertxutils.handlers.errorhandlers.exceptions.ApiJsonException;
import com.hmellema.vertxutils.handlers.errorhandlers.mapping.ExceptionMapping;
import io.vertx.core.json.JsonObject;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SuperBuilder
public class JsonExceptionMapping extends ExceptionMapping {

  @Override
  protected String getResponseOutput(Throwable failure) {
    return JsonObject.mapFrom(getApiJsonException(failure)).encodePrettily();
  }

  private ApiJsonException getApiJsonException(@NonNull final Throwable failure) {
    return ApiJsonException.builder()
        .errorType(outputType.getSimpleName())
        .message(mapMessage(failure))
        .httpCode(httpCode)
        .build();
  }
}
