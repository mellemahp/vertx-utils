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
      @NonNull final Class<RequestTypeT> inputTypeClass
  ) {
    this.requestConverter = new RestRequestConverter<>(inputTypeClass);
    this.responseConverter = new RestResponseConverter<>();
  }

  @Override
  public void handle(@NonNull final RoutingContext context) {
    RequestTypeT input = requestConverter.convertToInputType(context);
    this.execute(input, context);
  }

  protected void createResponse(@NonNull final ResponseTypeT response, 
                                @NonNull final RoutingContext context
  ) {
    responseConverter.createResponse(response, context);
  }

  public abstract void execute(RequestTypeT input, RoutingContext event);
}
