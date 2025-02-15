package com.webStore.webStore.controller;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.exceptions.ProductNotFoundException;
import com.webStore.webStore.service.IProductService.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable long productId) {
        var productDTO = productService.getProductById(productId);
        return getProductDTOResponseEntity(productDTO, productId);
    }

    private static ResponseEntity<ProductDTO> getProductDTOResponseEntity(Optional<ProductDTO> productDTO) {
        return productDTO.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseThrow(ProductNotFoundException::new);
    }

    private static ResponseEntity<?> getProductDTOResponseEntity(Optional<ProductDTO> productDTO, long id) {
        if (productDTO.isPresent())
            return new ResponseEntity<>(productDTO.get(), HttpStatus.OK);

        HttpHeaders headers = new HttpHeaders();
        headers.add("find Product Request By Id", "Product with id " + id + " not found");
        return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDTO productDTO) throws IllegalArgumentException {
        productService.addProduct(productDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/findMostPopularProduct")
    public ResponseEntity<ProductDTO> findMostPopularProduct() {
        var productDTO = productService.findMostPopularProduct();
        return getProductDTOResponseEntity(productDTO);
    }

    @GetMapping("/findMostUnpopularProduct")
    public ResponseEntity<ProductDTO> findMostUnpopularProduct() {
        var productDTO = productService.findMostUnpopularProduct();
        return getProductDTOResponseEntity(productDTO);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        var productsDTO = productService.getAllProducts();

        return productsDTO.map(productDTOS -> new ResponseEntity<>(productDTOS, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findProductByArrivedDate/{arrivedDateTime}")
    public ResponseEntity<ProductDTO> findProductArrivedByDate(@PathVariable LocalDateTime arrivedDateTime) {
        var productDTO = productService.findProductByArrivedProductDate(arrivedDateTime);
        return getProductDTOResponseEntity(productDTO);
    }

    @GetMapping("/getTotalSumOfSoldProducts")
    public double getTotalSumOfSoldProducts() {
        return productService.getTotalEarnedPrice();
    }

    @GetMapping("/findProductBySoldDate/{soldDateTime}")
    public ResponseEntity<ProductDTO> findProductBySoldDate(@PathVariable LocalDateTime soldDateTime) {
        var productDTO = productService.findProductByProductBySoldDate(soldDateTime);
        return getProductDTOResponseEntity(productDTO);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<String> updateProduct(@RequestBody @Valid ProductDTO productDTO) throws ProductNotFoundException {
        productService.updateProduct(productDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable long id) throws ProductNotFoundException {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestBody @Valid ProductDTO productDTO) {
        productService.deleteProduct(productDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findProductByName")
    public ResponseEntity<ProductDTO> findProductByName(@RequestParam String productName) {
        var productDTO = productService.findProductByProductNameIgnoreCase(productName);
        return getProductDTOResponseEntity(productDTO);
    }

    @GetMapping("/findMostPopularProductByYear/{productYear}")
    public ResponseEntity<ProductDTO> findMostPopularProductByYear(@PathVariable int productYear) {
        var productDTO = productService.findMostPopularProductByYear(productYear);
        return getProductDTOResponseEntity(productDTO);
    }

    @GetMapping("/findMostPopularProductByMonth/{productMonth}")
    public ResponseEntity<ProductDTO> findMostPopularProductByMonth(@PathVariable int productMonth) {
        var productDTO = productService.findMostPopularProductByMonth(productMonth);
        return getProductDTOResponseEntity(productDTO);
    }

    @GetMapping("/findMostUnPopularProductByYear/{productYear}")
    public ResponseEntity<ProductDTO> findMostUnPopularProductByYear(@PathVariable int productYear) {
        var productDTO = productService.findMostUnPopularProductByYear(productYear);
        return getProductDTOResponseEntity(productDTO);
    }

    @GetMapping("/findMostUnpopularProductByMonth/{productMonth}")
    public ResponseEntity<ProductDTO> findMostUnpopularProductByMonth(@PathVariable int productMonth) {
        var productDTO = productService.findMostUnPopularProductByMonth(productMonth);
        return getProductDTOResponseEntity(productDTO);
    }

    @GetMapping("/findMostPopularProductByDay/{productDay}")
    public ResponseEntity<ProductDTO> findMostPopularProductByDay(@PathVariable int productDay) {
        var productDTO = productService.findMostPopularProductByDay(productDay);
        return getProductDTOResponseEntity(productDTO);
    }

    @GetMapping("/findMostUnpopularPopularProductByDay/{productDay}")
    public ResponseEntity<ProductDTO> findMostUnPopularProductByDay(@PathVariable int productDay) {
        var productDTO = productService.findMostUnPopularProductByDay(productDay);
        return getProductDTOResponseEntity(productDTO);
    }

    @PatchMapping("/updateProductState/{productId}")
    public ResponseEntity<String> updateProductState(@PathVariable long productId) {
        if (productService.buy(productId))
            return new ResponseEntity<>("Product is updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Product is already sold", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findProductByNameAndPrice")
    public ResponseEntity<ProductDTO> findProductByNameAndPrice(@RequestParam @NotNull
                                                                    @Min(3) String productName, @RequestParam @NotNull double price) {
        var productDTO = productService.findProductByNameAndPrice(productName, price);
        return getProductDTOResponseEntity(productDTO);
    }
}