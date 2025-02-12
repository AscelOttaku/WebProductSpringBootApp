package com.webStore.webStore.service.impl;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.model.Product;
import com.webStore.webStore.model.ProductState;
import com.webStore.webStore.repository.ProductRepository;
import com.webStore.webStore.service.IProductService.ProductService;
import com.webStore.webStore.service.helper.SearchForProductByDate;
import com.webStore.webStore.service.serviceMapperDTO.ProductMapperDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

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
        productRepository.save(ProductMapperDTO.mapProductDTOToProduct(productDTO));
    }

    @Transactional
    @Override
    public void updateProduct(ProductDTO productDTO) {
        productRepository.saveAndFlush(ProductMapperDTO.mapProductDTOToProduct(productDTO));
    }

    @Transactional
    @Override
    public void deleteProduct(ProductDTO productDTO) {
        productRepository.delete(ProductMapperDTO.mapProductDTOToProduct(productDTO));
    }

    @Override
    public Optional<ProductDTO> findMostPopularProduct() {
        var products = productRepository.findAll();

        var mostPopularProduct = products.stream()
                .collect(Collectors.groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .max(Map.Entry.<Product, Long>comparingByValue()
                        .thenComparingDouble(entry -> entry.getKey().getPrice()));

        return mostPopularProduct.map(productLongEntry -> ProductMapperDTO.mapToProductDTO(productLongEntry.getKey()));
    }

    @Override
    public Optional<ProductDTO> findMostUnpopularProduct() {
        var products = productRepository.findAll();

        var mostUnpopularProduct = products.stream()
                .collect(Collectors.groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .min(Map.Entry.<Product, Long>comparingByValue()
                        .thenComparingDouble(entry -> entry.getKey().getPrice()));

        return mostUnpopularProduct.map(productLongEntry -> ProductMapperDTO.mapToProductDTO(productLongEntry.getKey()));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapperDTO::mapToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(long id) {
        return Optional.ofNullable(ProductMapperDTO.mapToProductDTO(productRepository.getProductById(id)));
    }

    @Override
    public Optional<ProductDTO> findProductByArrivedProductDate(LocalDateTime localDateTime) {
        return Optional.ofNullable(ProductMapperDTO.mapToProductDTO(productRepository.findProductByArrivedAtStoreTime(localDateTime)));
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
        return Optional.ofNullable(ProductMapperDTO.mapToProductDTO(productRepository.findProductBySoldAtStoreTime(localDateTime)));
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<ProductDTO> findProductByProductNameIgnoreCase(String productName) {
        return Optional.ofNullable(ProductMapperDTO.mapToProductDTO(productRepository.findByNameIgnoreCase(productName)));
    }

    @Override
    public Optional<ProductDTO> findMostPopularProductByYear(int year) {
        var products = productRepository.findAll();
        return SearchForProductByDate.YEAR.findMostPopularProductByDate(year, products);
    }

    @Override
    public Optional<ProductDTO> findMostPopularProductByMonth(int month) {
        var products = productRepository.findAll();
        return SearchForProductByDate.MONTH.findMostPopularProductByDate(month, products);
    }
}
