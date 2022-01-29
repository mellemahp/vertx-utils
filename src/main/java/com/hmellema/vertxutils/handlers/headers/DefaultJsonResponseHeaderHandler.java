package com.hmellema.vertxutils.handlers.headers;

import static java.util.Collections.emptyMap;

import java.util.HashMap;
import java.util.Map;

public class DefaultJsonResponseHeaderHandler extends HeaderHandler {

  private static final String CONTENT_TYPE_OPTIONS_HEADER_NAME = "X-Content-Type-Options";
  private static final String CONTENT_TYPE_OPTIONS_HEADER_VALUE = "nosniff";
  private static final String FRAME_OPTIONS_HEADER_NAME = "X-Frame-Options";
  private static final String FRAME_OPTIONS_HEADER_VALUE = "DENY";

  private static final Map<String, String> headerMap = new HashMap<String, String>() {
    {
      put(CONTENT_TYPE_OPTIONS_HEADER_NAME, CONTENT_TYPE_OPTIONS_HEADER_VALUE);
      put(FRAME_OPTIONS_HEADER_NAME, FRAME_OPTIONS_HEADER_VALUE);
    }
  };

  public DefaultJsonResponseHeaderHandler() {
    super(headerMap, emptyMap());
  }
}
