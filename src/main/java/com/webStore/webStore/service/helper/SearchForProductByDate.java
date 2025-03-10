package com.webStore.webStore.service.helper;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.model.Product;
import com.webStore.webStore.model.ProductState;
import com.webStore.webStore.service.serviceMapperDTO.ProductMapperDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

public enum SearchForProductByDate {
    MONTH {
        @Override
        public Optional<ProductDTO> findMostPopularProductByDate(int val, List<Product> products) {
            ToIntFunction<Product> function = product -> product.getSoldAtStoreTime().getMonthValue();

            return getPopularProduct(val, products, function);
        }

        @Override
        public Optional<ProductDTO> findMostUnpopularProductByDate(int val, List<Product> products) {
            ToIntFunction<Product> function = product -> product.getSoldAtStoreTime().getMonthValue();

            return getUnpopularProduct(val, products, function);
        }
    }, YEAR {
        @Override
        public Optional<ProductDTO> findMostPopularProductByDate(int val, List<Product> products) {
            ToIntFunction<Product> function = product -> product.getSoldAtStoreTime().getYear();

            return getPopularProduct(val, products, function);
        }

        @Override
        public Optional<ProductDTO> findMostUnpopularProductByDate(int val, List<Product> products) {
            ToIntFunction<Product> function = product -> product.getSoldAtStoreTime().getYear();

            return getUnpopularProduct(val, products, function);
        }
    }, DAY {
        @Override
        public Optional<ProductDTO> findMostPopularProductByDate(int val, List<Product> products) {
            ToIntFunction<Product> function = product -> product.getSoldAtStoreTime().getDayOfMonth();

            return getPopularProduct(val, products, function);
        }

        @Override
        public Optional<ProductDTO> findMostUnpopularProductByDate(int val, List<Product> products) {
            ToIntFunction<Product> function = product -> product.getSoldAtStoreTime().getDayOfMonth();

            return getUnpopularProduct(val, products, function);
        }
    };

    protected static Optional<ProductDTO> getUnpopularProduct(int val, List<Product> products, ToIntFunction<Product> function) {
        return products.stream()
                .filter(product -> product.getProductState().equals(ProductState.SOLD) &&
                        isNotNull(product) && function.applyAsInt(product) == val)
                .collect(Collectors.groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .min(Map.Entry.<Product, Long>comparingByValue()
                        .thenComparingDouble(entry -> entry.getKey().getPrice()))
                .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()));
    }

    protected static Optional<ProductDTO> getPopularProduct(int val, List<Product> products, ToIntFunction<Product> function) {
        return products.stream()
                .filter(product -> product.getProductState().equals(ProductState.SOLD) &&
                        isNotNull(product) && function.applyAsInt(product) == val)
                .collect(Collectors.groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .max(Map.Entry.<Product, Long>comparingByValue()
                        .thenComparingDouble(entry -> entry.getKey().getPrice()))
                .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()));
    }

    private static boolean isNotNull(Product product) {
        return product.getSoldAtStoreTime() != null;
    }

    public abstract Optional<ProductDTO> findMostPopularProductByDate(int val, List<Product> products);

    public abstract Optional<ProductDTO> findMostUnpopularProductByDate(int val, List<Product> products);
}
