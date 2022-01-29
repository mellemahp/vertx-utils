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

  private static final String CONTENT_TYPE_HEADER = "Content-Type";

  @NonNull
  private final String contentTypeHeaderValue;

  @Override
  public void handle(@NonNull RoutingContext context) {
    context.response().putHeader(CONTENT_TYPE_HEADER, contentTypeHeaderValue);
    context.next();
  }
}
