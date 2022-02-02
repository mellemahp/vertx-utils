package com.hmellema.vertxutils.handlers.requestid;

public final class RequestIdConstants {
    public static final String REQUEST_ID_HEADER = "X-Request-ID";
    public static final String REQUEST_ID_NEW_TEMPLATE = "Root=%s";
    public static final String REQUEST_ID_EXISTING_TEMPLATE = "Root=%s;Sub=%s";
    public static final String ROOT_REQUEST_ID_MDC_NAME = "requestId.Root";
    public static final String SUB_REQUEST_ID_MDC_NAME = "requestId.Sub";
}
