package com.webStore.webStore.service.IProductService;

import com.webStore.webStore.dto.ProductDTO;

import java.util.Optional;

public interface SearchForProductsByDate {
    Optional<ProductDTO> findMostPopularProductByYear(int year);
    Optional<ProductDTO> findMostPopularProductByMonth(int category);
    Optional<ProductDTO> findMostUnPopularProductByYear(int year);
    Optional<ProductDTO> findMostUnPopularProductByMonth(int category);
    Optional<ProductDTO> findMostPopularProductByDay(int day);
    Optional<ProductDTO> findMostUnPopularProductByDay(int day);
}
