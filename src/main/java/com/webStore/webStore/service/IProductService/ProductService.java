package com.webStore.webStore.service.IProductService;

import com.webStore.webStore.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService extends SearchProduct, SearchForProductsByDate {
    void addProduct(ProductDTO productDTO);
    void updateProduct(ProductDTO productDTO);
    void deleteProduct(ProductDTO productDTO);
    Optional<List<ProductDTO>> getAllProducts();
    void deleteProductById(long id);
}