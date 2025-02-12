package com.webStore.webStore.service.IProductService;

import com.webStore.webStore.dto.ProductDTO;

import java.util.List;

public interface ProductService extends SearchProduct, SearchForProductsByDate {
    void addProduct(ProductDTO productDTO);
    void updateProduct(ProductDTO productDTO);
    void deleteProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    void deleteProductById(long id);
}