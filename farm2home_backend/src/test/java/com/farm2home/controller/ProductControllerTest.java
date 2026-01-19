package com.farm2home.controller;

import com.farm2home.BaseTest;
import com.farm2home.entity.Product;
import com.farm2home.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class ProductControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    // 🔥 THIS IS THE KEY FIX
    @MockBean
    private ProductService productService;

    // ✅ Test 1: Get all products
    @Test
    void shouldGetAllProducts() throws Exception {
        when(productService.getProducts(null)).thenReturn(List.of());

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    // ✅ Test 2: Get products by category
    @Test
    void shouldGetProductsByCategory() throws Exception {
        when(productService.getProducts("Vegetables")).thenReturn(List.of());

        mockMvc.perform(
                get("/api/products")
                        .param("category", "Vegetables")
        )
        .andExpect(status().isOk());
    }

    // ✅ Test 3: Search products
    @Test
    void shouldSearchProducts() throws Exception {
        when(productService.searchProducts("beetroot")).thenReturn(List.of());

        mockMvc.perform(
                get("/api/products/search")
                        .param("q", "beetroot")
        )
        .andExpect(status().isOk());
    }

    // ✅ Test 4: Get product by ID
    @Test
    void shouldGetProductById() throws Exception {
        Product mockProduct = new Product();
        mockProduct.setId(1L);

        when(productService.getProductById(1L)).thenReturn(mockProduct);

        mockMvc.perform(
                get("/api/products/id/{id}", 1L)
        )
        .andExpect(status().isOk());
    }
}
