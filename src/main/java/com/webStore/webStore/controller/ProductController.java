package com.webStore.webStore.controller;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
        var productDTO = productService.getProductById(productId);
        return productDTO.orElse(null);
    }

    @PostMapping("/addProduct")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
        return productDTO;
    }

    @GetMapping("/findMostPopularProduct")
    public ProductDTO findMostPopularProduct() {
        var productDTO = productService.findMostPopularProduct();
        return productDTO.orElse(null);
    }

    @GetMapping("/findMostUnpopularProduct")
    public ProductDTO findMostUnpopularProduct() {
        var productDTO = productService.findMostUnpopularProduct();
        return productDTO.orElse(null);
    }

    @GetMapping("/getAllProducts")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/findProductByArrivedDate/{arrivedDateTime}")
    public ProductDTO findProductArrivedByDate(@PathVariable LocalDateTime arrivedDateTime) {
        var productDTO = productService.findProductByArrivedProductDate(arrivedDateTime);
        return productDTO.orElse(null);
    }

    @GetMapping("/getTotalSumOfSoldProducts")
    public double getTotalSumOfSoldProducts() {
        return productService.getTotalEarnedPrice();
    }

    @GetMapping("/findProductBySoldDate/{soldDateTime}")
    public ProductDTO findProductBySoldDate(@PathVariable LocalDateTime soldDateTime) {
        var productDTO = productService.findProductByProductBySoldDate(soldDateTime);
        return productDTO.orElse(null);
    }

    @PutMapping("/updateProduct")
    public void updateProduct(@RequestBody ProductDTO productDTO) {
        productService.updateProduct(productDTO);
    }
}
