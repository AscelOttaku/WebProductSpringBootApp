package com.webStore.webStore.dto;

import com.webStore.webStore.model.ProductState;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {
    private String name;
    private double price;
    private ProductState productState;
    private LocalDateTime arrivedAtStoreTime;
    private LocalDateTime soldAtStoreTime;
}
