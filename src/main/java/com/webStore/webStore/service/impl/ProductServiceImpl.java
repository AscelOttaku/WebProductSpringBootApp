package com.webStore.webStore.service.impl;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.exceptions.ProductNotFoundException;
import com.webStore.webStore.model.Product;
import com.webStore.webStore.model.ProductState;
import com.webStore.webStore.repository.ProductRepository;
import com.webStore.webStore.service.IProductService.ProductService;
import com.webStore.webStore.service.IProductService.SearchForProductsByDate;
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
        var product = productRepository.findById(id);

        if (product.isEmpty())
            throw new ProductNotFoundException("Product by id " + id + " is not found");
    }

    @Override
    public ProductDTO findMostPopularProduct() {
        var products = productRepository.findAll();

        return products.stream()
                .collect(Collectors.groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .max(Map.Entry.<Product, Long>comparingByValue()
                        .thenComparingDouble(entry -> entry.getKey().getPrice()))
                .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()))
                .orElseThrow(() -> new ProductNotFoundException("No most popular product found"));
    }

    @Override
    public ProductDTO findMostUnpopularProduct() {
        var products = productRepository.findAll();

        return products.stream()
                .collect(Collectors.groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .min(Map.Entry.<Product, Long>comparingByValue()
                        .thenComparingDouble(entry -> entry.getKey().getPrice()))
                .map(entry -> ProductMapperDTO.mapToProductDTO(entry.getKey()))
                .orElseThrow(() -> new ProductNotFoundException("No most unpopular product found"));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        var productList = productRepository.findAll().stream()
                .map(ProductMapperDTO::mapToProductDTO)
                .toList();

        if (productList.isEmpty())
            throw new ProductNotFoundException("No products found");

        return productList;
    }

    @Override
    public ProductDTO getProductById(long id) {
        var product = productRepository.findById(id);
        return ProductMapperDTO.mapToProductDTO(product.orElseThrow(() ->
                new ProductNotFoundException("Product Not Found By Id Exception")));
    }

    @Override
    public ProductDTO findProductByArrivedProductDate(LocalDateTime localDateTime) {
        return ProductMapperDTO.mapToProductDTO(productRepository.findProductByArrivedAtStoreTime(localDateTime)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found By LocalDate Exception")));
    }

    @Override
    public double getTotalEarnedPrice() {
        return productRepository.findAll().stream()
                .filter(product -> product.getProductState().equals(ProductState.SOLD))
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public ProductDTO findProductByProductSoldDate(LocalDateTime localDateTime) {
        return ProductMapperDTO.mapToProductDTO(productRepository.findProductBySoldAtStoreTime(localDateTime)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found By LocalDateTime Exception")));
    }

    @Override
    public void deleteProductById(long id) throws ProductNotFoundException {
        productIsNotFound(id);
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO findProductById(long id) {
        var productOptional = productRepository.findById(id);
        return productOptional.map(ProductMapperDTO::mapToProductDTO)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found By Id Exception"));
    }

    @Override
    public ProductDTO findProductByProductNameIgnoreCase(String productName) {
        return ProductMapperDTO.mapToProductDTO(productRepository.findByNameIgnoreCase(productName)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found By Name Exception")));
    }

    @Override
    public ProductDTO findProductByNameAndPrice(String productName, double price) {
        return ProductMapperDTO.mapToProductDTO(productRepository.findProductByNameAndPrice(productName, price)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found By Name Exception")));
    }

    @Override
    public ProductDTO findMostPopularProductByYear(int year) {
        return searchForProductByArg(SearchForProductByDate.YEAR, year, "No Product Found By Year");
    }

    @Override
    public ProductDTO findMostPopularProductByMonth(int month) {
        return searchForProductByArg(SearchForProductByDate.MONTH, month, "No Product Found By Month");
    }

    @Override
    public ProductDTO findMostUnPopularProductByYear(int year) {
        return searchForUnpopularProductByArg(SearchForProductByDate.YEAR, year, "No Product Found By Year");
    }

    @Override
    public ProductDTO findMostUnPopularProductByMonth(int month) {
        return searchForUnpopularProductByArg(SearchForProductByDate.MONTH, month, "No UnProduct Found By Month");
    }

    @Override
    public ProductDTO findMostPopularProductByDay(int day) {
        return searchForProductByArg(SearchForProductByDate.DAY, day, "No Product Found By Day");
    }

    private ProductDTO searchForProductByArg(SearchForProductByDate searchForProductsByDate, int val, String arg) {
        return searchForProductsByDate.findMostPopularProductByDate(val, productRepository.findAll())
                .orElseThrow(() -> new ProductNotFoundException(arg));
    }

    @Override
    public ProductDTO findMostUnPopularProductByDay(int day) {
        return searchForUnpopularProductByArg(SearchForProductByDate.DAY, day, "No Unpopular Product Found By Day");
    }

    private ProductDTO searchForUnpopularProductByArg(SearchForProductByDate searchForProductsByDate, int val, String arg) {
        return searchForProductsByDate.findMostUnpopularProductByDate(val, productRepository.findAll())
                .orElseThrow(() -> new ProductNotFoundException(arg));
    }

    @Override
    public boolean buy(long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty())
            throw new ProductNotFoundException("Product not found");

        if (!product.get().getProductState().equals(ProductState.SOLD)) {
            Product getProduct = product.get();
            getProduct.getProductState().buyProduct(getProduct);
            productRepository.save(getProduct);
            return true;
        }
        return false;
    }
}
