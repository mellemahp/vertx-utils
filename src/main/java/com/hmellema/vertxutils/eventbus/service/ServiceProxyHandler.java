package com.hmellema.vertxutils.eventbus.service;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import java.util.function.Function;

/**
 * @param <RequestTypeT>
 */
public interface ServiceProxyHandler<RequestTypeT> extends Handler<Void> {
  int DEFAULT_NO_HANDLER_CODE = 400;
  String ACTION_HEADER = "action";
  String NO_ACTION_ERROR_MSG = "action not specified";

  default String getAction(Message<RequestTypeT> msg) {
    String action = msg.headers().get(ACTION_HEADER);
    if (action == null) {
      throw new IllegalStateException(NO_ACTION_ERROR_MSG);
    }
    return action;
  }

  default void handle(Message<RequestTypeT> msg) {
    String action = getAction(msg);
    Function<Message<RequestTypeT>, Void> handler = selectFunction(action);
    if (handler == null) {
      msg.fail(DEFAULT_NO_HANDLER_CODE, "No handler found for action: " + action);
    } else {
      handler.apply(msg);
    }
  }

  Function<Message<RequestTypeT>, Void> selectFunction(String action);

}
