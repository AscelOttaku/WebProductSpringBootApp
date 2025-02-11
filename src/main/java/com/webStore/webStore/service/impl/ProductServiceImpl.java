package com.webStore.webStore.service.impl;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.model.Product;
import com.webStore.webStore.model.ProductState;
import com.webStore.webStore.repository.ProductRepository;
import com.webStore.webStore.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Transactional
    @Override
    public void addProduct(ProductDTO productDTO) {
        productRepository.save(mapProductDTOToProduct(productDTO));
    }

    @Transactional
    @Override
    public void updateProduct(ProductDTO productDTO) {
        productRepository.saveAndFlush(mapProductDTOToProduct(productDTO));
    }

    @Override
    public void deleteProduct(ProductDTO productDTO) {
        productRepository.delete(mapProductDTOToProduct(productDTO));
    }

    @Override
    public Optional<ProductDTO> findMostPopularProduct() {
        var products = productRepository.findAll();

        var mostPopularProduct = products.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.<Product, Long>comparingByValue()
                        .thenComparingDouble(entry -> entry.getKey().getPrice()));

        return mostPopularProduct.map(productLongEntry -> mapToProductDTO(productLongEntry.getKey()));
    }

    @Override
    public Optional<ProductDTO> findMostUnpopularProduct() {
        var products = productRepository.findAll();

        var mostUnpopularProduct = products.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .min(Map.Entry.<Product, Long>comparingByValue()
                        .thenComparingDouble(entry -> entry.getKey().getPrice()));

        return mostUnpopularProduct.map(productLongEntry -> mapToProductDTO(productLongEntry.getKey()));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(long id) {
        return Optional.ofNullable(mapToProductDTO(productRepository.getProductById(id)));
    }

    @Override
    public Optional<ProductDTO> findProductByArrivedProductDate(LocalDateTime localDateTime) {
        return Optional.ofNullable(mapToProductDTO(productRepository.findProductByArrivedAtStoreTime(localDateTime)));
    }

    @Override
    public double getTotalEarnedPrice() {
        return productRepository.findAll().stream()
                .filter(product -> product.getProductState().equals(ProductState.SOLD))
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public Optional<ProductDTO> findProductByProductBySoldDate(LocalDateTime localDateTime) {
        return Optional.ofNullable(mapToProductDTO(productRepository.findProductBySoldAtStoreTime(localDateTime)));
    }

    private Product mapProductDTOToProduct(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .productState(productDTO.getProductState())
                .arrivedAtStoreTime(productDTO.getArrivedAtStoreTime())
                .soldAtStoreTime(productDTO.getSoldAtStoreTime())
                .build();
    }

    private ProductDTO mapToProductDTO(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .price(product.getPrice())
                .productState(product.getProductState())
                .arrivedAtStoreTime(product.getArrivedAtStoreTime())
                .soldAtStoreTime(product.getSoldAtStoreTime())
                .build();
    }
}
