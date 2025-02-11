package com.webStore.webStore.repository;

import com.webStore.webStore.model.Product;

import java.util.List;

public interface ProductRepository {
    void createProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
    Product getProduct(long id);
    List<Product> getAllProducts();
}
