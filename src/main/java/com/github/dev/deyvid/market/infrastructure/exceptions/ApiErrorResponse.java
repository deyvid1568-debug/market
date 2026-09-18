package com.github.dev.deyvid.market.infrastructure.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> validationErrors
) {
    public ApiErrorResponse(int status, String error, String message) {
        this(LocalDateTime.now(), status, error, message, null);
    }
}