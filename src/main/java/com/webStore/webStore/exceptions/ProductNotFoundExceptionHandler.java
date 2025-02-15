package com.webStore.webStore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;

@ControllerAdvice
public class ProductNotFoundExceptionHandler {

    @ExceptionHandler(value = ProductNotFound.class)
    public ResponseEntity<RequiredExceptionBody> handleProductNotFoundException(ProductNotFound productNotFound) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        RequiredExceptionBody productNotFoundExceptionBody = setExceptionBody(productNotFound, httpStatus);

        return new ResponseEntity<>(productNotFoundExceptionBody, httpStatus);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<RequiredExceptionBody> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        RequiredExceptionBody productNotFoundExceptionBody = setExceptionBody(e, httpStatus);

        return new ResponseEntity<>(productNotFoundExceptionBody, httpStatus);
    }

    private RequiredExceptionBody setExceptionBody(RuntimeException e, HttpStatus status) {
        return new RequiredExceptionBody(
                e.getMessage(),
                status,
                ZonedDateTime.now(ZoneOffset.UTC),
                Arrays.toString(e.getStackTrace())
        );
    }
}
