package com.hmellema.vertxutils.utils.web;

import io.vertx.ext.web.RoutingContext;
import lombok.NonNull;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public enum ContentType {
    APPLICATION_JSON_UTF8("application/json", StandardCharsets.UTF_8),
    TEXT_HTML_UTF8("text/html", StandardCharsets.UTF_8);

    // TODO: Add additional content types

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CONTENT_TYPE_TEMPLATE = "%s; %s";
    private static final String CHARSET_TEMPLATE = "charset=%s";

    private final String headerValue;

    ContentType(@NonNull final String mediaType, @NonNull final Charset charset) {
        this.headerValue = String.format(CONTENT_TYPE_TEMPLATE,
                mediaType,
                String.format(CHARSET_TEMPLATE, charset.toString().toLowerCase())
        );
    }

    public void setContentType(@NonNull final RoutingContext routingContext) {
        routingContext.response().putHeader(CONTENT_TYPE_HEADER, this.headerValue);
    }
}
