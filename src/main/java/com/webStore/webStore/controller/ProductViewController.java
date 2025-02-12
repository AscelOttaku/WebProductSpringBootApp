package com.webStore.webStore.controller;

import com.webStore.webStore.dto.ProductDTO;
import com.webStore.webStore.service.IProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/productPage")
public class ProductViewController {
    private final ProductService productService;

    @Autowired
    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAllTheProductsByPage")
    public String getAllTheProductsByPage(Model model) {
        List<ProductDTO> productDTOS = productService.getAllProducts();
        model.addAttribute("products", productDTOS);
        return "products-list";
    }
}
