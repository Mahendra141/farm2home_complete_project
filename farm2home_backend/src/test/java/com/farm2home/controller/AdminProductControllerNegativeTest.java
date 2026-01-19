package com.farm2home.controller;

import com.farm2home.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
class AdminProductControllerNegativeTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    // ❌ GET product → not found
    @Test
    @WithMockUser(roles = "ADMIN")
    void getProduct_shouldReturn400_whenProductNotFound() throws Exception {

        mockMvc.perform(
                get("/api/admin/products/9999")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error")
                .value("Product not found with id: 9999"));
    }

    // ❌ UPDATE product → not found
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProduct_shouldReturn400_whenProductNotFound() throws Exception {

        String json = """
        {
          "name": "Tomato",
          "price": 20.0,
          "category": "Vegetables",
          "unit": "kg",
          "available": true
        }
        """;

        mockMvc.perform(
                put("/api/admin/products/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error")
                .value("Product not found with id: 9999"));
    }

    // ❌ ADD product → missing body
    @Test
    @WithMockUser(roles = "ADMIN")
    void addProduct_shouldFail_whenBodyMissing() throws Exception {

        mockMvc.perform(
                post("/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().is4xxClientError());
    }

}
