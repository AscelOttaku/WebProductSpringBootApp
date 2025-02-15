package com.webStore.webStore.model;

public enum ProductState {
    SOLD {
        @Override
        public void buyProduct(Product product) {}
    }, IN_STORE {
        @Override
        public void buyProduct(Product product) {
            product.setProductState(SOLD);
        }
    };

    public abstract void buyProduct(Product product);
}
