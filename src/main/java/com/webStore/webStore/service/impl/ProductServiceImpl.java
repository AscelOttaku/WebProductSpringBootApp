package com.webStore.webStore.service.impl;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.exceptions.ProductNotFound;
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
        if (checkForDate(productDTO))
            productRepository.save(ProductMapperDTO.mapProductDTOToProduct(productDTO));
        else
            throw new IllegalArgumentException("Product Arrived Date cannot be equals or after Sold Date");
    }

    private boolean checkForDate(ProductDTO productDTO) {
        return productDTO.getSoldAtStoreTime() == null ||
                productDTO.getArrivedAtStoreTime().isBefore(productDTO.getSoldAtStoreTime());
    }

    @Transactional
    @Override
    public void updateProduct(ProductDTO productDTO) {
        productIsNotFound(productDTO.getId());
        productRepository.save(ProductMapperDTO.mapProductDTOToProduct(productDTO));
    }

    @Transactional
    @Override
    public void deleteProduct(ProductDTO productDTO) {
        productIsNotFound(productDTO.getId());
        productRepository.delete(ProductMapperDTO.mapProductDTOToProduct(productDTO));
    }

    private void productIsNotFound(long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty())
            throw new ProductNotFound("Product by id " + id + " is not found");
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
    public Optional<List<ProductDTO>> getAllProducts() {
        return Optional.of(productRepository.findAll().stream()
                .map(ProductMapperDTO::mapToProductDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<ProductDTO> getProductById(long id) {
        var product = productRepository.findById(id);
        return product.map(ProductMapperDTO::mapToProductDTO);
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
    public void deleteProductById(long id) throws ProductNotFound {
        productIsNotFound(id);
        productRepository.deleteById(id);
    }

    @Override
    public Optional<ProductDTO> findProductById(long id) {
        var productOptional = productRepository.findById(id);
        return productOptional.map(ProductMapperDTO::mapToProductDTO);
    }

    @Override
    public Optional<ProductDTO> findProductByProductNameIgnoreCase(String productName) {
        return Optional.ofNullable(ProductMapperDTO.mapToProductDTO(productRepository.findByNameIgnoreCase(productName)));
    }

    @Override
    public Optional<ProductDTO> findMostPopularProductByYear(int year) {
        return SearchForProductByDate.YEAR.findMostPopularProductByDate(year, productRepository.findAll());
    }

    @Override
    public Optional<ProductDTO> findMostPopularProductByMonth(int month) {
        return SearchForProductByDate.MONTH.findMostPopularProductByDate(month, productRepository.findAll());
    }

    @Override
    public Optional<ProductDTO> findMostUnPopularProductByYear(int year) {
        return SearchForProductByDate.YEAR.findMostUnpopularProductByDate(year, productRepository.findAll());
    }

    @Override
    public Optional<ProductDTO> findMostUnPopularProductByMonth(int month) {
        return SearchForProductByDate.MONTH.findMostUnpopularProductByDate(month, productRepository.findAll());
    }

    @Override
    public Optional<ProductDTO> findMostPopularProductByDay(int day) {
        return SearchForProductByDate.DAY.findMostPopularProductByDate(day, productRepository.findAll());
    }

    @Override
    public Optional<ProductDTO> findMostUnPopularProductByDay(int day) {
        return SearchForProductByDate.DAY.findMostUnpopularProductByDate(day, productRepository.findAll());
    }

    @Override
    public boolean buy(long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty())
            throw new ProductNotFound("Product not found");

        if (!product.get().getProductState().equals(ProductState.SOLD)) {
            Product getProduct = product.get();
            getProduct.getProductState().buyProduct(getProduct);
            productRepository.save(getProduct);
            return true;
        }
        return false;
    }
}
