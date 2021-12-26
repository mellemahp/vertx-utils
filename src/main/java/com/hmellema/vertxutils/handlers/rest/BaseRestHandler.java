package com.hmellema.vertxutils.handlers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmellema.vertxutils.conversion.RestRequestConverter;
import com.hmellema.vertxutils.conversion.RestResponseConverter;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.NonNull;

/**
 * Base handler for rest API routes.
 * 
 * <p>The request/response Types should be auto-generated from the OpenAPI spec
 * to ensure responses follow the Api contract
 * 
 * @param <RequestTypeT> Model type to convert to a json request into
 * @param <ResponseTypeT> Model type to convert to a json reponse
 */
public abstract class BaseRestHandler<RequestTypeT, ResponseTypeT>
    implements Handler<RoutingContext> {

  @NonNull
  private final RestRequestConverter<RequestTypeT> requestConverter;

  @NonNull
  private final RestResponseConverter<ResponseTypeT> responseConverter;

  protected BaseRestHandler(
      @NonNull final Class<RequestTypeT> inputTypeClass,
      @NonNull final ObjectMapper objectMapper
  ) {
    this.requestConverter =
      new RestRequestConverter<>(inputTypeClass, objectMapper);
    this.responseConverter = new RestResponseConverter<>(objectMapper);
  }

  @Override
  public void handle(@NonNull final RoutingContext context) {
    RequestTypeT input = requestConverter.convertToInputType(context);
    ResponseTypeT output = this.execute(input, context);
    responseConverter.createResponse(output, context);
  }

  public abstract ResponseTypeT execute(RequestTypeT input, RoutingContext event);
}
