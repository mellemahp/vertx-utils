package com.hmellema.vertxutils.eventbus.service;

import io.vertx.core.eventbus.DeliveryOptions;

/**
 *
 */
public interface ServiceProxy {
  String ACTION_HEADER = "action";

  /**
   * @param value
   * @param deliveryOptions
   */
  default void setActionHeader(String value, DeliveryOptions deliveryOptions) {
    deliveryOptions.addHeader(ACTION_HEADER, value);
  }
}
