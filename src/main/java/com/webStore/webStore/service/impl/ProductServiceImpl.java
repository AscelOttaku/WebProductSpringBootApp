package com.webStore.webStore.service.impl;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.model.Product;
import com.webStore.webStore.repository.ProductRepository;
import com.webStore.webStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        productRepository.createProduct(mapProductDTOToProduct(productDTO));
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        productRepository.updateProduct(mapProductDTOToProduct(productDTO));
    }

    @Override
    public void deleteProduct(ProductDTO productDTO) {
        productRepository.deleteProduct(mapProductDTOToProduct(productDTO));
    }

    @Override
    public List<ProductDTO> getAllProduct() {
       var products = productRepository.getAllProducts();

       return products.stream()
               .map(this::mapToProductDTO)
               .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> findMostPopularProduct() {
        var products = productRepository.getAllProducts();

        var mostPopularProduct = products.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());

        return mostPopularProduct.map(productLongEntry -> mapToProductDTO(productLongEntry.getKey()));
    }

    @Override
    public Optional<ProductDTO> findMostUnpopularProduct() {
        var products = productRepository.getAllProducts();

        var mostUnpopularProduct = products.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue());

        return mostUnpopularProduct.map(productLongEntry -> mapToProductDTO(productLongEntry.getKey()));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.getAllProducts().stream()
                .map(this::mapToProductDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(long id) {
        return productRepository.getAllProducts().stream()
                .filter(product -> product.getId() == id)
                .map(this::mapToProductDTO)
                .findFirst();
    }

    private Product mapProductDTOToProduct(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .productState(productDTO.getProductState())
                .arrivedAtStoreTime(productDTO.getArrivedAtStoreTime())
                .soldAtStoreTime(productDTO.getSoldAtStoreTime())
                .build();
    }

    private ProductDTO mapToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productState(product.getProductState())
                .arrivedAtStoreTime(product.getArrivedAtStoreTime())
                .soldAtStoreTime(product.getSoldAtStoreTime())
                .build();
    }
}
