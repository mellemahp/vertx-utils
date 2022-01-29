package com.hmellema.vertxutils.handlers.headers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class HeaderHandler implements Handler<RoutingContext> {

  @NonNull
  private final Map<String, String> headerMapSingleValued;

  @NonNull
  private final Map<String, Iterable<String>> headerMapMultiValued;

  @Override
  public void handle(@NonNull RoutingContext context) {
    // Add all headers that have only a single string value
    for (Map.Entry<String, String> entry : headerMapSingleValued.entrySet()) {
      context.response().putHeader(entry.getKey(), entry.getValue());
    }
    // Add all headers with multiple values
    for (Map.Entry<String, Iterable<String>> entry : headerMapMultiValued.entrySet()) {
      context.response().putHeader(entry.getKey(), entry.getValue());
    }
    context.next();
  }
}
