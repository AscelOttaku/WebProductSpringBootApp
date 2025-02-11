package com.webStore.webStore.model;

public enum ProductState {
    SOLD {
        @Override
        boolean buyProduct() {
            return false;
        }
    }, IN_STORE {
        @Override
        boolean buyProduct() {
            return true;
        }
    };

    abstract boolean buyProduct();
}
