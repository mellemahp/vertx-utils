package com.hmellema.vertxutils.handlers.requestid;

import io.reactiverse.contextual.logging.ContextualData;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Vert.x handler for generating and adding Request ID header to 
 * a response. This handler also adds that request ID as a contextual
 * data field so it can be logged.
 * 
 * If a customer has already provided an X-request ID header in their request
 * then that is used as the Root Id and a Sub id is generated and added to the 
 * request logs 
 * 
 */
@NoArgsConstructor
public class RequestIdHandler implements Handler<RoutingContext> {

  private static final String REQUEST_ID_HEADER = "X-Request-ID";
  private static final String REQUEST_ID_NEW_TEMPLATE = "Root=%s";
  private static final String REQUEST_ID_EXISTING_TEMPLATE = "Root=%s;Sub=%s";
  private static final String ROOT_REQUEST_ID_MDC_NAME = "requestId.Root";
  private static final String SUB_REQUEST_ID_MDC_NAME = "requestId.Sub";

  @Override
  public void handle(@NonNull RoutingContext context) {
    Optional<String> requestIdOptional = getRequestIdFromHeader(context);
    String localRequestId = generateRequestId();
    String requestHeaderValue;
    if (requestIdOptional.isPresent()) {
      String requestIdroot = requestIdOptional.get();
      ContextualData.put(ROOT_REQUEST_ID_MDC_NAME, requestIdroot);
      ContextualData.put(SUB_REQUEST_ID_MDC_NAME, localRequestId);
      requestHeaderValue = String.format(REQUEST_ID_EXISTING_TEMPLATE, requestIdroot, localRequestId);
    } else {
      ContextualData.put(ROOT_REQUEST_ID_MDC_NAME, localRequestId);
      requestHeaderValue = String.format(REQUEST_ID_NEW_TEMPLATE, localRequestId);
    }
    context.response().putHeader(REQUEST_ID_HEADER, requestHeaderValue);
    context.next();
  }

  private Optional<String> getRequestIdFromHeader(@NonNull final RoutingContext context) {
    return Optional.ofNullable(context.request().getHeader(REQUEST_ID_HEADER));
  }

  private String generateRequestId() {
    return UUID.randomUUID().toString();
  }
}
