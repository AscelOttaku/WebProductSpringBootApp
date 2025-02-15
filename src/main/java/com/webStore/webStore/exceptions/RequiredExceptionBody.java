package com.webStore.webStore.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public record RequiredExceptionBody(
        String message,
        HttpStatus httpStatus,
        ZonedDateTime zonedDateTime,
        Throwable throwable,
        String path) {
}
