package com.hmellema.vertxutils.handlers.requestid;

import static com.hmellema.vertxutils.handlers.requestid.RequestIdConstants.*;

import io.reactiverse.contextual.logging.ContextualData;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryContext;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Inbound Event bus interceptor for adding request id values to contextual data
 *
 * @param <T> Type used in message payload (i.e. type used by codec, typically JsonObject)
 */
@NoArgsConstructor
public class RequestIdInboundInterceptor<T>
  implements Handler<DeliveryContext<T>> {

  @Override
  public void handle(@NonNull DeliveryContext<T> event) {
    String requestIdRoot = event.message().headers().get(ROOT_REQUEST_ID_MDC_NAME);
    String requestIdSub = event.message().headers().get(SUB_REQUEST_ID_MDC_NAME);
    
    if (requestIdRoot != null) {
      ContextualData.put(ROOT_REQUEST_ID_MDC_NAME, requestIdRoot);
    }
    if (requestIdSub != null) {
      ContextualData.put(SUB_REQUEST_ID_MDC_NAME, requestIdSub);
    }
    event.next();
  }
}
