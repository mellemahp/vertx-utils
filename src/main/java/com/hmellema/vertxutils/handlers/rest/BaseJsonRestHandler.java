package com.hmellema.vertxutils.handlers.rest;

import com.hmellema.vertxutils.utils.web.ContentType;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.NonNull;

public abstract class BaseJsonRestHandler<RequestTypeT, ResponseTypeT> extends BaseRestHandler<RequestTypeT, ResponseTypeT>{

    protected BaseJsonRestHandler(@NonNull Class<RequestTypeT> inputTypeClass) {
        super(inputTypeClass, ContentType.APPLICATION_JSON_UTF8);
    }

    @Override
    protected void returnResponse(ResponseTypeT result, RoutingContext context){
        context.response().end(JsonObject.mapFrom(result).encodePrettily());
    }
}
