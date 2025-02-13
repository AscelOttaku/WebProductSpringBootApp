package com.webStore.webStore.exceptions;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound() {}

    public ProductNotFound(String message) {
        super(message);
    }
}
