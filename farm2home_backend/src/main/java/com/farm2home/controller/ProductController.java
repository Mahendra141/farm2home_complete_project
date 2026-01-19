package com.farm2home.controller;

import com.farm2home.entity.Product;
import com.farm2home.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ Category filter API
    // /api/products
    // /api/products?category=Vegetables
    // /api/products?category=Fruits
    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) String category
    ) {
        return productService.getProducts(category);
    }

    // ✅ SEARCH SUGGESTIONS
    // /api/products/suggestions?q=ba
    @GetMapping("/suggestions")
    public List<String> getSuggestions(
            @RequestParam String q
    ) {
        return productService.getSearchSuggestions(q);
    }

    // ✅ SEARCH RESULTS
    // /api/products/search?q=beetroot
    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String q
    ) {
        return productService.searchProducts(q);
    }

    // ✅ GET PRODUCT BY ID (EXPLICIT PATH)
    // /api/products/id/5
    @GetMapping("/id/{id}")
    public Product getProductById(
            @PathVariable Long id
    ) {
        return productService.getProductById(id);
    }
}
