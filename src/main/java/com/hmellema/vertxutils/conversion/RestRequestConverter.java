package com.hmellema.vertxutils.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Converts a json request to a RequestTypeT object.
 *
 * <p>The input Type should be auto-generated from the OpenAPI spec
 * to ensure responses follow the Api contract
 *
 * @param <RequestTypeT> Model type to convert JSON input into
 */
@Slf4j
@RequiredArgsConstructor
public class RestRequestConverter<RequestTypeT> {

  @NonNull
  private final Class<RequestTypeT> inputTypeClass;

  @NonNull
  private final ObjectMapper objectMapper;

  /**
   * Converts a JSON request into a RequestTypeT object.
   *
   * @param routingContext vert.x routing context
   * @return converted Request Object
   */
  public RequestTypeT convertToInputType(
      final @NonNull RoutingContext routingContext
  ) {
    JsonObject inputData = extractCombinedParams(routingContext);
    log.info("Input DATA: {}", inputData);
    return inputData.mapTo(inputTypeClass);
  }

  private JsonObject extractCombinedParams(
      final @NonNull RoutingContext context
  ) {
    JsonObject metadata = extractParameters(context);
    Optional
      .ofNullable(context.getBodyAsJson())
      .ifPresent(bodyJsonData -> metadata.mergeIn(bodyJsonData));

    return metadata;
  }

  private JsonObject extractParameters(final @NonNull RoutingContext context) {
    MultiMap allParams = MultiMap.caseInsensitiveMultiMap();
    allParams.addAll(context.queryParams());
    allParams.addAll(context.pathParams());

    return multiMapToJsonObject(allParams);
  }

  private JsonObject multiMapToJsonObject(final @NonNull MultiMap multimap) {
    JsonObject jsonData = new JsonObject();
    for (Map.Entry<String, String> entry : multimap.entries()) {
      jsonData.put(entry.getKey(), entry.getValue());
    }
    return jsonData;
  }
}
