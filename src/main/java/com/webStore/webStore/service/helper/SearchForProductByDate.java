package com.webStore.webStore.service.helper;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.model.Product;
import com.webStore.webStore.model.ProductState;
import com.webStore.webStore.service.serviceMapperDTO.ProductMapperDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

public enum SearchForProductByDate {
    MONTH {
        @Override
        public Optional<ProductDTO> findMostPopularProductByDate(int val, List<Product> products) {
                return products.stream()
                        .filter(product -> product.getProductState().equals(ProductState.SOLD) &&
                                product.getSoldAtStoreTime().getMonthValue() == val)
                        .collect(Collectors.groupingBy(Function.identity(), counting()))
                        .entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()));
        }
    }, YEAR {
        @Override
        public Optional<ProductDTO> findMostPopularProductByDate(int val, List<Product> products) {
            return products.stream()
                    .filter(product -> product.getProductState().equals(ProductState.SOLD) &&
                            product.getSoldAtStoreTime().getYear() == val)
                    .collect(Collectors.groupingBy(Function.identity(), counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()));
        }
    };

    public abstract Optional<ProductDTO> findMostPopularProductByDate(int val, List<Product> products);
}
