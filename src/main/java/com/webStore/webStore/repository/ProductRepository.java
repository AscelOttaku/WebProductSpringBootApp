package com.webStore.webStore.repository;

import com.webStore.webStore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductById(long id);
    List<Product> findAll();
    Product findProductByArrivedAtStoreTime(LocalDateTime localDateTime);
    Product findProductBySoldAtStoreTime(LocalDateTime localDateTime);
}
