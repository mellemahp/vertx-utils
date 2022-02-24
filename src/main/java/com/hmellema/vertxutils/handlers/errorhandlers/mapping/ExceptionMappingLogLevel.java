package com.hmellema.vertxutils.handlers.errorhandlers.mapping;

import lombok.NonNull;
import org.slf4j.Logger;

public enum ExceptionMappingLogLevel {
  TRACE {
    @Override
    public void logWithStackTrack(@NonNull Logger logger,
                                  @NonNull String message,
                                  @NonNull Throwable failure) {
      logger.trace(message, failure);
    }

    @Override
    public void logWithMessageOnly(@NonNull Logger logger,
                                   @NonNull String message,
                                   @NonNull Throwable failure) {
      logger.trace(MESSAGE_TEMPLATE, message, failure.getMessage());
    }
  },
  DEBUG {
    @Override
    public void logWithStackTrack(@NonNull Logger logger,
                                  @NonNull String message,
                                  @NonNull Throwable failure) {
      logger.debug(message, failure);
    }

    @Override
    public void logWithMessageOnly(@NonNull Logger logger,
                                   @NonNull String message,
                                   @NonNull Throwable failure) {
      logger.debug(MESSAGE_TEMPLATE, message, failure.getMessage());
    }
  },
  INFO {
    @Override
    public void logWithStackTrack(@NonNull Logger logger,
                                  @NonNull String message,
                                  @NonNull Throwable failure) {
      logger.info(message, failure);
    }

    @Override
    public void logWithMessageOnly(@NonNull Logger logger,
                                   @NonNull String message,
                                   @NonNull Throwable failure) {
      logger.info(MESSAGE_TEMPLATE, message, failure.getMessage());
    }
  },
  WARN {
    @Override
    public void logWithStackTrack(@NonNull Logger logger,
                                  @NonNull String message,
                                  @NonNull Throwable failure) {
      logger.warn(message, failure);
    }

    @Override
    public void logWithMessageOnly(@NonNull Logger logger,
                                   @NonNull String message,
                                   @NonNull Throwable failure) {
      logger.warn(MESSAGE_TEMPLATE, message, failure.getMessage());
    }
  },
  ERROR {
    @Override
    public void logWithStackTrack(@NonNull Logger logger,
                                  @NonNull String message,
                                  @NonNull Throwable failure
    ) {
      logger.error(message, failure);
    }

    @Override
    public void logWithMessageOnly(@NonNull Logger logger,
                                   @NonNull String message,
                                   @NonNull Throwable failure
    ) {
      logger.error(MESSAGE_TEMPLATE, message, failure.getMessage());
    }
  };

  private static final String MESSAGE_TEMPLATE = "Message: {}, Error: {}";

  protected abstract void logWithStackTrack(Logger logger, String message, Throwable failure);

  protected abstract void logWithMessageOnly(Logger logger, String message, Throwable failure);

  public void logError(@NonNull Logger logger,
                       @NonNull String message,
                       @NonNull Throwable failure,
                       boolean logStackTrace
  ) {
    if (logStackTrace) {
      this.logWithStackTrack(logger, message, failure);
    } else {
      this.logWithMessageOnly(logger, message, failure);
    }
  }
}


