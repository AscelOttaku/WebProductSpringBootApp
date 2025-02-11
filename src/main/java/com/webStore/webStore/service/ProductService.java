package com.webStore.webStore.service;

import com.webStore.webStore.dto.ProductDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    void addProduct(ProductDTO productDTO);
    void updateProduct(ProductDTO productDTO);
    void deleteProduct(ProductDTO productDTO);
    Optional<ProductDTO> findMostPopularProduct();
    Optional<ProductDTO> findMostUnpopularProduct();
    List<ProductDTO> getAllProducts();
    Optional<ProductDTO> getProductById(long id);
    Optional<ProductDTO> findProductByArrivedProductDate(LocalDateTime localDateTime);
    double getTotalEarnedPrice();
    Optional<ProductDTO> findProductByProductBySoldDate(LocalDateTime localDateTime);
}