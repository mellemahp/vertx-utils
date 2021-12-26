package com.hmellema.vertxutils.handlers.contenttype;


/**
 * Vert.x Handler for adding JSON content type header to a response.
 */
public class JsonContentTypeHandler extends ContentTypeHandler {

  private static final String JSON_CONTENT_TYPE = "application/json";
  private static final String CONTENT_TYPE_HEADER = "Content-Type";

  public JsonContentTypeHandler() {
    super(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE);
  }
}
