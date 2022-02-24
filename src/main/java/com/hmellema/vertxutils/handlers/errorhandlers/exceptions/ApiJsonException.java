package com.hmellema.vertxutils.handlers.errorhandlers.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class ApiJsonException {
  int httpCode;
  @NonNull String errorType;
  @NonNull String message;
  @Nullable
  String documentation;
}
