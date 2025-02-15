package com.webStore.webStore.service.IProductService;

import com.webStore.webStore.dto.ProductDTO;

public interface SearchForProductsByDate {
    ProductDTO findMostPopularProductByYear(int year);
    ProductDTO findMostPopularProductByMonth(int category);
    ProductDTO findMostUnPopularProductByYear(int year);
    ProductDTO findMostUnPopularProductByMonth(int category);
    ProductDTO findMostPopularProductByDay(int day);
    ProductDTO findMostUnPopularProductByDay(int day);
}
