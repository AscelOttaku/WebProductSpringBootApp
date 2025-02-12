package com.webStore.webStore.dto;

import com.webStore.webStore.model.ProductState;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {
    private long id;
    private String name;
    private double price;
    private String photoUrl;
    private ProductState productState;
    private LocalDateTime arrivedAtStoreTime;
    private LocalDateTime soldAtStoreTime;
}
