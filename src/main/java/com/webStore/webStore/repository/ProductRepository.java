package com.webStore.webStore.repository;

import com.webStore.webStore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductByArrivedAtStoreTime(LocalDateTime localDateTime);
    Optional<Product> findProductBySoldAtStoreTime(LocalDateTime localDateTime);
    Optional<Product> findByNameIgnoreCase(String productName);
    Optional<Product> findProductByNameAndPrice(String productName, double price);
}