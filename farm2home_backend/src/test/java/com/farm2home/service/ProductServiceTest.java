package com.farm2home.service;

import com.farm2home.dto.ProductRequest;
import com.farm2home.entity.Product;
import com.farm2home.enums.DemandLevel;
import com.farm2home.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PricingService pricingService;

    @InjectMocks
    private ProductService productService;

    // ---------- PUBLIC ----------

    @Test
    void getProducts_shouldReturnVegetablesByDefault() {

        Product product = new Product();
        product.setBasePrice(100.0);

        when(productRepository
                .findByCategoryIgnoreCaseAndAvailableTrue("Vegetables"))
                .thenReturn(List.of(product));

        when(pricingService.calculateFinalPrice(product))
                .thenReturn(70.0);

        List<Product> products = productService.getProducts(null);

        assertEquals(1, products.size());
        assertEquals(70.0, products.get(0).getFinalPrice());

        verify(productRepository)
                .findByCategoryIgnoreCaseAndAvailableTrue("Vegetables");
    }

    @Test
    void searchProducts_shouldReturnMatchingProducts() {

        Product product = new Product();
        product.setBasePrice(50.0);

        when(productRepository
                .findByNameContainingIgnoreCaseAndAvailableTrue("tom"))
                .thenReturn(List.of(product));

        when(pricingService.calculateFinalPrice(product))
                .thenReturn(50.0);

        List<Product> result = productService.searchProducts("tom");

        assertFalse(result.isEmpty());
        assertEquals(50.0, result.get(0).getFinalPrice());

        verify(productRepository)
                .findByNameContainingIgnoreCaseAndAvailableTrue("tom");
    }

    @Test
    void getSearchSuggestions_shouldReturnNamesOnly() {

        when(productRepository.findTop5ProductNames("to"))
                .thenReturn(List.of("Tomato", "Potato"));

        List<String> suggestions =
                productService.getSearchSuggestions("to");

        assertEquals(2, suggestions.size());
        verify(productRepository)
                .findTop5ProductNames("to");
    }

    @Test
    void getProductById_shouldReturnProduct_whenExists() {

        Product product = new Product();
        product.setId(1L);
        product.setBasePrice(100.0);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(pricingService.calculateFinalPrice(product))
                .thenReturn(80.0);

        Product result = productService.getProductById(1L);

        assertEquals(1L, result.getId());
        assertEquals(80.0, result.getFinalPrice());
    }

    @Test
    void getProductById_shouldThrowException_whenNotFound() {

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> productService.getProductById(99L)
        );

        assertTrue(ex.getMessage().contains("Product not found"));
    }

    // ---------- ADMIN ----------

    @Test
    void addProduct_shouldSaveProduct_withBasePrice() {

        ProductRequest request = new ProductRequest();
        request.setName("Tomato");
        request.setPrice(40.0);
        request.setCategory("Vegetables");
        request.setUnit("kg");
        request.setAvailable(true);
        request.setDiscountActive(true);
        request.setDiscountPercent(20);
        request.setDemandLevel(DemandLevel.NORMAL);

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product savedProduct = productService.addProduct(request);

        assertEquals("Tomato", savedProduct.getName());
        assertEquals(40.0, savedProduct.getBasePrice());
        assertEquals("/images/vegetables/tomato.jpg",
                savedProduct.getImageUrl());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldUpdateExistingProduct() {

        Product existing = new Product();
        existing.setId(1L);
        existing.setBasePrice(30.0);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(productRepository.save(existing))
                .thenReturn(existing);

        ProductRequest request = new ProductRequest();
        request.setPrice(50.0);
        request.setCategory("Vegetables");
        request.setUnit("kg");
        request.setAvailable(true);
        request.setDiscountActive(false);

        Product updated =
                productService.updateProduct(1L, request);

        assertEquals(50.0, updated.getBasePrice());
        assertFalse(updated.getDiscountActive());

        verify(productRepository).save(existing);
    }

    @Test
    void deleteProduct_shouldMarkProductUnavailable() {

        Product product = new Product();
        product.setId(1L);
        product.setAvailable(true);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        assertFalse(product.getAvailable());
        verify(productRepository).save(product);
    }
}
