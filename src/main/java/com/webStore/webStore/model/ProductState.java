package com.webStore.webStore.model;

public enum ProductState {
    SOLD {
        @Override
        public boolean buyProduct(Product product) {
            return false;
        }
    }, IN_STORE {
        @Override
        public boolean buyProduct(Product product) {
            product.setProductState(SOLD);
            return true;
        }
    };

    public abstract boolean buyProduct(Product product);
}
