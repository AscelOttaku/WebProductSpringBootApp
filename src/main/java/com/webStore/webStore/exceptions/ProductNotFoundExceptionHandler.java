package com.webStore.webStore.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RestControllerAdvice(annotations = RestController.class)
public class ProductNotFoundExceptionHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RequiredExceptionResponse handleProductNotFoundException(ProductNotFoundException productNotFound, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return setExceptionBody(productNotFound, httpStatus, request);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequiredExceptionResponse handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return setExceptionBody(e, httpStatus, request);
    }

    private RequiredExceptionResponse setExceptionBody(RuntimeException e, HttpStatus status, HttpServletRequest statusRequest) {
        return new RequiredExceptionResponse(
                e.getMessage(),
                status,
                ZonedDateTime.now(ZoneOffset.UTC),
                statusRequest.getRequestURI()
        );
    }
}
