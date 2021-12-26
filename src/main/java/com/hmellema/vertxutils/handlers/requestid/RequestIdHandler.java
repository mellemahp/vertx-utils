package com.hmellema.vertxutils.handlers.requestid;

import io.reactiverse.contextual.logging.ContextualData;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;

/**
 * Vert.x handler for generating and adding Request ID header to 
 * a response. This handler also adds that request ID as a contextual
 * data field so it can be logged.
 */
@NoArgsConstructor
public class RequestIdHandler implements Handler<RoutingContext> {

  private static final String REQUEST_ID_HEADER = "X-Request-ID";
  private static final String REQUEST_ID_NAME = "requestId";

  @Override
  public void handle(RoutingContext context) {
    String requestId = UUID.randomUUID().toString();
    ContextualData.put(REQUEST_ID_NAME, requestId);
    context.response().putHeader(REQUEST_ID_HEADER, requestId);
    context.next();
  }
}
