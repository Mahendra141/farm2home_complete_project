package com.farm2home.controller;

import com.farm2home.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class OrderControllerNegativeTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    // ❌ Missing request body
    // Accept any 4xx (400 or 415 depending on Spring parsing)
    @Test
    void createOrder_shouldFail_whenBodyIsMissing() throws Exception {

        mockMvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().is4xxClientError());
    }

    // ❌ Missing required field (validation error → always 400)
    @Test
    void createOrder_shouldFail_whenTotalAmountMissing() throws Exception {

        String json = """
        {
          "latitude": 17.3,
          "longitude": 78.4
        }
        """;

        mockMvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isBadRequest());
    }

    // ❌ Invalid JSON format
    // Parsing error → client mistake → 4xx
    @Test
    void createOrder_shouldFail_whenInvalidJson() throws Exception {

        String invalidJson = "{ totalAmount: 500 ";

        mockMvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson)
        )
        .andExpect(status().is4xxClientError());
    }
}
