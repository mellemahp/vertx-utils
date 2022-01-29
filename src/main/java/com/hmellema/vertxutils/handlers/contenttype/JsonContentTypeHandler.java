package com.hmellema.vertxutils.handlers.contenttype;


/**
 * Vert.x Handler for adding JSON content type header to a response.
 */
public class JsonContentTypeHandler extends ContentTypeHandler {

  private static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";

  public JsonContentTypeHandler() {
    super(JSON_CONTENT_TYPE);
  }
}
