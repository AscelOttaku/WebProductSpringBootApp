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
                        .max(Map.Entry.<Product, Long>comparingByValue()
                                .thenComparingDouble(entry -> entry.getKey().getPrice()))
                        .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()));
        }

        @Override
        public Optional<ProductDTO> findMostUnpopularProductByDate(int val, List<Product> products) {
            return products.stream()
                    .filter(product -> product.getProductState().equals(ProductState.SOLD) &&
                            product.getArrivedAtStoreTime().getMonthValue() >= val)
                    .collect(Collectors.groupingBy(Function.identity(), counting()))
                    .entrySet().stream()
                    .min(Map.Entry.<Product, Long>comparingByValue()
                            .thenComparingDouble(entry -> entry.getKey().getPrice()))
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
                    .max(Map.Entry.<Product, Long>comparingByValue()
                            .thenComparingDouble(entry -> entry.getKey().getPrice()))
                    .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()));
        }

        @Override
        public Optional<ProductDTO> findMostUnpopularProductByDate(int val, List<Product> products) {
            return products.stream()
                    .filter(product -> product.getProductState().equals(ProductState.SOLD) &&
                            product.getArrivedAtStoreTime().getYear() >= val)
                    .collect(Collectors.groupingBy(Function.identity(), counting()))
                    .entrySet().stream()
                    .min(Map.Entry.<Product, Long>comparingByValue()
                            .thenComparingDouble(entry -> entry.getKey().getPrice()))
                    .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()));
        }
    }, DAY {
        @Override
        public Optional<ProductDTO> findMostPopularProductByDate(int val, List<Product> products) {
            return products.stream()
                    .filter(product -> product.getProductState().equals(ProductState.SOLD) &&
                            product.getSoldAtStoreTime().getDayOfMonth() == val)
                    .collect(Collectors.groupingBy(Function.identity(), counting()))
                    .entrySet().stream()
                    .max(Map.Entry.<Product, Long>comparingByValue()
                            .thenComparingDouble(product -> product.getKey().getPrice()))
                    .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()));
        }

        @Override
        public Optional<ProductDTO> findMostUnpopularProductByDate(int val, List<Product> products) {
            return products.stream()
                    .filter(product -> product.getProductState().equals(ProductState.SOLD) &&
                            product.getSoldAtStoreTime().getDayOfMonth() >= val)
                    .collect(Collectors.groupingBy(Function.identity(), counting()))
                    .entrySet().stream()
                    .min(Map.Entry.<Product, Long>comparingByValue()
                            .thenComparingDouble(product -> product.getKey().getPrice()))
                    .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()));
        }
    };

    public abstract Optional<ProductDTO> findMostPopularProductByDate(int val, List<Product> products);
    public abstract Optional<ProductDTO> findMostUnpopularProductByDate(int val, List<Product> products);
}
