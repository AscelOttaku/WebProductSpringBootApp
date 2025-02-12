package com.webStore.webStore.service.IProductService;

import com.webStore.webStore.dto.ProductDTO;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SearchProduct {
    Optional<ProductDTO> findMostPopularProduct();
    Optional<ProductDTO> findMostUnpopularProduct();
    Optional<ProductDTO> getProductById(long id);
    Optional<ProductDTO> findProductByArrivedProductDate(LocalDateTime localDateTime);
    double getTotalEarnedPrice();
    Optional<ProductDTO> findProductByProductBySoldDate(LocalDateTime localDateTime);
    Optional<ProductDTO> findProductByProductNameIgnoreCase(String productName);
}
