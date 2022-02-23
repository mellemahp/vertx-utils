package com.hmellema.vertxutils.handlers.rest;

import com.hmellema.vertxutils.conversion.RestRequestConverter;
import com.hmellema.vertxutils.utils.web.ContentType;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
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
  private final ContentType contentType;

  protected BaseRestHandler(
          @NonNull final Class<RequestTypeT> inputTypeClass,
          @NonNull final ContentType contentType
  ) {
    this.requestConverter = new RestRequestConverter<>(inputTypeClass);
    this.contentType = contentType;
  }

  @Override
  public void handle(@NonNull final RoutingContext context) {
    RequestTypeT input = requestConverter.convertToInputType(context);
    contentType.setContentType(context);
    execute(input, context)
            .onSuccess(res -> context.response().end(JsonObject.mapFrom(res).encodePrettily()))
            .onFailure(context::fail);
  }

  public abstract Future<ResponseTypeT> execute(RequestTypeT input, RoutingContext event);
}
