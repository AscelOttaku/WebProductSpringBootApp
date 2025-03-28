package com.webStore.webStore.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record RequiredExceptionResponse(
        String message,
        HttpStatus httpStatus,
        ZonedDateTime zonedDateTime,
        String path) {
}
