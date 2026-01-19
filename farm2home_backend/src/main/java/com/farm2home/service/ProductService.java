package com.farm2home.service;

import com.farm2home.dto.ProductRequest;
import com.farm2home.entity.Product;
import com.farm2home.enums.DemandLevel;
import com.farm2home.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PricingService pricingService;

    // ---------- PUBLIC ----------

    public List<Product> getProducts(String category) {

        List<Product> products;

        if (category == null || category.isBlank()) {
            products = productRepository
                    .findByCategoryIgnoreCaseAndAvailableTrue("Vegetables");
        } else {
            products = productRepository
                    .findByCategoryIgnoreCaseAndAvailableTrue(category);
        }

        // 🔥 calculate final price (DO NOT override base price)
        products.forEach(product ->
                product.setFinalPrice(
                        pricingService.calculateFinalPrice(product)
                )
        );

        return products;
    }

    public List<Product> searchProducts(String keyword) {

        List<Product> products =
                productRepository.findByNameContainingIgnoreCaseAndAvailableTrue(keyword);

        products.forEach(product ->
                product.setFinalPrice(
                        pricingService.calculateFinalPrice(product)
                )
        );

        return products;
    }

    public List<String> getSearchSuggestions(String keyword) {
        return productRepository.findTop5ProductNames(keyword);
    }

    public Product getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id));

        product.setFinalPrice(
                pricingService.calculateFinalPrice(product)
        );

        return product;
    }

    // ---------- ADMIN ----------

    public Product addProduct(ProductRequest request) {

        Product product = new Product();
        product.setName(request.getName());

        // 🔥 ADMIN ENTERS BASE PRICE
        product.setBasePrice(request.getPrice());

        product.setCategory(request.getCategory());
        product.setUnit(request.getUnit());
        product.setAvailable(request.getAvailable());

        // 🔥 Discount & demand (admin controlled)
        product.setDiscountPercent(request.getDiscountPercent());
        product.setDiscountActive(request.getDiscountActive());
        product.setStock(request.getStock());
        product.setDemandLevel(
                request.getDemandLevel() != null
                        ? request.getDemandLevel()
                        : DemandLevel.NORMAL
        );

        product.setImageUrl(resolveImageUrl(request.getName()));

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id));

        // 🔥 update base price if provided
        product.setBasePrice(request.getPrice());

        product.setCategory(request.getCategory());
        product.setUnit(request.getUnit());
        product.setAvailable(request.getAvailable());

        // 🔥 update discount rules
        product.setDiscountPercent(request.getDiscountPercent());
        product.setDiscountActive(request.getDiscountActive());
        product.setStock(request.getStock());
        product.setDemandLevel(request.getDemandLevel());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id));

        product.setAvailable(false);
        productRepository.save(product);
    }

    // ---------- IMAGE URL ----------
    private String resolveImageUrl(String productName) {
        return "/images/vegetables/"
                + productName.toLowerCase().replace(" ", "")
                + ".jpg";
    }
 // ---------- ADMIN ----------
    public List<Product> getAllProductsForAdmin() {
        return productRepository.findAll();
    }

}
