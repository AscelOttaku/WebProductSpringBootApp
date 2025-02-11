package com.webStore.webStore.service;

import com.webStore.webStore.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void addProduct(ProductDTO productDTO);
    void updateProduct(ProductDTO productDTO);
    void deleteProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProduct();
    Optional<ProductDTO> findMostPopularProduct();
    Optional<ProductDTO> findMostUnpopularProduct();
    List<ProductDTO> getAllProducts();
}
