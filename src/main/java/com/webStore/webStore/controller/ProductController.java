package com.webStore.webStore.controller;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.service.IProductService.ProductService;
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

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProductById(@PathVariable long id) {
        productService.deleteProductById(id);
    }

    @DeleteMapping("/deleteProduct")
    public void deleteProduct(@RequestBody ProductDTO productDTO) {
        productService.deleteProduct(productDTO);
    }

    @GetMapping("/findProductByName")
    public ProductDTO findProductByName(@RequestParam String productName) {
        var productDTO = productService.findProductByProductNameIgnoreCase(productName);
        return productDTO.orElse(null);
    }

    @GetMapping("/findMostPopularProductByYear/{productYear}")
    public ProductDTO findMostPopularProductByYear(@PathVariable int productYear) {
        var productDTO = productService.findMostPopularProductByYear(productYear);
        return productDTO.orElse(null);
    }

    @GetMapping("/findMostPopularProductByMonth/{productMonth}")
    public ProductDTO findMostPopularProductByMonth(@PathVariable int productMonth) {
        var productDTO = productService.findMostPopularProductByMonth(productMonth);
        return productDTO.orElse(null);
    }

    @GetMapping("/findMostUnPopularProductByYear/{productYear}")
    public ProductDTO findMostUnPopularProductByYear(@PathVariable int productYear) {
        var productDTO = productService.findMostUnPopularProductByYear(productYear);
        return productDTO.orElse(null);
    }

    @GetMapping("/findMostUnpopularProductByMonth/{productMonth}")
    public ProductDTO findMostUnpopularProductByMonth(@PathVariable int productMonth) {
        var productDTO = productService.findMostUnPopularProductByMonth(productMonth);
        return productDTO.orElse(null);
    }

    @GetMapping("/findMostPopularProductByDay/{productDay}")
    public ProductDTO findMostPopularProductByDay(@PathVariable int productDay) {
        var productDTO = productService.findMostPopularProductByDay(productDay);
        return productDTO.orElse(null);
    }

    @GetMapping("/findMostUnpopularPopularProductByDay/{productDay}")
    public ProductDTO findMostUnPopularProductByDay(@PathVariable int productDay) {
        var productDTO = productService.findMostUnPopularProductByDay(productDay);
        return productDTO.orElse(null);
    }
}
