package com.webStore.webStore.service.IProductService;

import com.webStore.webStore.dto.ProductDTO;

import java.time.LocalDateTime;

public interface SearchProduct {
    ProductDTO findMostPopularProduct();
    ProductDTO findMostUnpopularProduct();
    ProductDTO getProductById(long id);
    ProductDTO findProductByArrivedProductDate(LocalDateTime localDateTime);
    double getTotalEarnedPrice();
    ProductDTO findProductByProductSoldDate(LocalDateTime localDateTime);
    ProductDTO findProductByProductNameIgnoreCase(String productName);
    ProductDTO findProductByNameAndPrice(String productName, double price);
}
