package com.webStore.webStore.service.serviceMapperDTO;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.model.Product;

public class ProductMapperDTO {
    public static Product mapProductDTOToProduct(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .photoUrl(productDTO.getPhotoUrl())
                .productState(productDTO.getProductState())
                .arrivedAtStoreTime(productDTO.getArrivedAtStoreTime())
                .soldAtStoreTime(productDTO.getSoldAtStoreTime())
                .build();
    }

    public static ProductDTO mapToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .photoUrl(product.getPhotoUrl())
                .productState(product.getProductState())
                .arrivedAtStoreTime(product.getArrivedAtStoreTime())
                .soldAtStoreTime(product.getSoldAtStoreTime())
                .build();
    }
}
