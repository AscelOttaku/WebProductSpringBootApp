package com.webStore.webStore.repository;

import com.webStore.webStore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductByArrivedAtStoreTime(LocalDateTime localDateTime);
    Product findProductBySoldAtStoreTime(LocalDateTime localDateTime);
    Product findByNameIgnoreCase(String productName);
    Product findProductByNameAndPrice(String productName, double price);
}