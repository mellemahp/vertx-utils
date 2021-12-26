package com.hmellema.vertxutils.handlers.contenttype;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Base class for creating handlers that add a content type header to responses.
 */
@RequiredArgsConstructor
public abstract class ContentTypeHandler implements Handler<RoutingContext> {

  @NonNull
  private final String contentTypeHeaderKey;

  @NonNull
  private final String contentTypeHeaderValue;

  @Override
  public void handle(RoutingContext context) {
    context.response().putHeader(contentTypeHeaderKey, contentTypeHeaderValue);
    context.next();
  }
}
