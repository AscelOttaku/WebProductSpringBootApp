package com.webStore.webStore.controller;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.exceptions.ProductNotFoundException;
import com.webStore.webStore.service.IProductService.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ProductDTO getProductById(@PathVariable long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody @Valid ProductDTO productDTO) throws IllegalArgumentException {
        productService.addProduct(productDTO);
    }

    @GetMapping("/findMostPopularProduct")
    public ProductDTO findMostPopularProduct() {
        return productService.findMostPopularProduct();
    }

    @GetMapping("/findMostUnpopularProduct")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findMostUnpopularProduct() {
        return productService.findMostUnpopularProduct();
    }

    @GetMapping("/getAllProducts")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/findProductByArrivedDate/{arrivedDateTime}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findProductArrivedByDate(@PathVariable LocalDateTime arrivedDateTime) {
        return productService.findProductByArrivedProductDate(arrivedDateTime);
    }

    @GetMapping("/getTotalSumOfSoldProducts")
    public double getTotalSumOfSoldProducts() {
        return productService.getTotalEarnedPrice();
    }

    @GetMapping("/findProductBySoldDate/{soldDateTime}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findProductBySoldDate(@PathVariable LocalDateTime soldDateTime) {
        return productService.findProductByProductSoldDate(soldDateTime);
    }

    @PutMapping("/updateProduct")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@RequestBody @Valid ProductDTO productDTO) throws ProductNotFoundException {
        productService.updateProduct(productDTO);
    }

    @DeleteMapping("/deleteProduct/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable long id) throws ProductNotFoundException {
        productService.deleteProductById(id);
    }

    @DeleteMapping("/deleteProduct")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@RequestBody @Valid ProductDTO productDTO) {
        productService.deleteProduct(productDTO);
    }

    @GetMapping("/findProductByName")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findProductByName(@RequestParam String productName) {
        return productService.findProductByProductNameIgnoreCase(productName);
    }

    @GetMapping("/findMostPopularProductByYear/{productYear}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findMostPopularProductByYear(@PathVariable int productYear) {
        return productService.findMostPopularProductByYear(productYear);
    }

    @GetMapping("/findMostPopularProductByMonth/{productMonth}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findMostPopularProductByMonth(@PathVariable int productMonth) {
        return productService.findMostPopularProductByMonth(productMonth);
    }

    @GetMapping("/findMostUnPopularProductByYear/{productYear}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findMostUnPopularProductByYear(@PathVariable int productYear) {
        return productService.findMostUnPopularProductByYear(productYear);
    }

    @GetMapping("/findMostUnpopularProductByMonth/{productMonth}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findMostUnpopularProductByMonth(@PathVariable int productMonth) {
        return productService.findMostUnPopularProductByMonth(productMonth);
    }

    @GetMapping("/findMostPopularProductByDay/{productDay}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findMostPopularProductByDay(@PathVariable int productDay) {
        return productService.findMostPopularProductByDay(productDay);
    }

    @GetMapping("/findMostUnpopularPopularProductByDay/{productDay}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findMostUnPopularProductByDay(@PathVariable int productDay) {
        return productService.findMostUnPopularProductByDay(productDay);
    }

    @PatchMapping("/updateProductState/{productId}")
    public ResponseEntity<String> updateProductState(@PathVariable long productId) {
        if (productService.buy(productId))
            return new ResponseEntity<>("Product is updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Product is already sold", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findProductByNameAndPrice")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findProductByNameAndPrice(@RequestParam @NotNull @Min(3) String productName,
                                                @RequestParam @NotNull double price) {
        return productService.findProductByNameAndPrice(productName, price);
    }
}