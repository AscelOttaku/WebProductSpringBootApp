package com.webStore.webStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private final ProductState productState;
    private LocalDateTime arrivedAtStoreTime;
    private LocalDateTime soldAtStoreTime;

    public Product() {
        this.productState = ProductState.IN_STORE;
    }
}
