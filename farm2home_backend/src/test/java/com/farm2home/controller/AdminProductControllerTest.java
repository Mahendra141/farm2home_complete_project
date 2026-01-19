package com.farm2home.controller;

import com.farm2home.BaseTest;
import com.farm2home.dto.ProductRequest;
import com.farm2home.entity.Product;
import com.farm2home.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class AdminProductControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ ADD PRODUCT
    @Test
    void shouldAddProduct() throws Exception {

        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setName("Tomato");

        when(productService.addProduct(any(ProductRequest.class)))
                .thenReturn(mockProduct);

        ProductRequest request = new ProductRequest();
        request.setName("Tomato");
        request.setPrice(40.0);   // ✅ FIXED


        mockMvc.perform(
                post("/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Tomato"));
    }

    // ✅ GET PRODUCT BY ID
    @Test
    void shouldGetProductById() throws Exception {

        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setName("Potato");

        when(productService.getProductById(1L))
                .thenReturn(mockProduct);

        mockMvc.perform(
                get("/api/admin/products/{id}", 1L)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Potato"));
    }

    // ✅ UPDATE PRODUCT
    @Test
    void shouldUpdateProduct() throws Exception {

        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setName("Updated Tomato");

        when(productService.updateProduct(eq(1L), any(ProductRequest.class)))
                .thenReturn(mockProduct);

        ProductRequest request = new ProductRequest();
        request.setName("Updated Tomato");
        request.setPrice(50.0); 

        mockMvc.perform(
                put("/api/admin/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Tomato"));
    }

    // ✅ DELETE PRODUCT
    @Test
    void shouldDeleteProduct() throws Exception {

        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(
                delete("/api/admin/products/{id}", 1L)
        )
        .andExpect(status().isOk())
        .andExpect(content().string("Product disabled successfully"));
    }
}
