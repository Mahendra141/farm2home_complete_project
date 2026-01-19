package com.farm2home.controller;

import com.farm2home.BaseTest;
import com.farm2home.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class AdminOrderControllerNegativeTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    // ❌ No token → 403 Forbidden
    @Test
    void getAllOrders_shouldReturn403_whenNoTokenProvided() throws Exception {

        mockMvc.perform(
                get("/api/admin/orders")
        )
        .andExpect(status().isForbidden());
    }

    // ❌ USER role token → 403 Forbidden
    @Test
    void getAllOrders_shouldReturn403_whenUserRoleProvided() throws Exception {

        String userToken = jwtUtil.generateToken(
                "user@farm2home.com",
                "ROLE_USER"
        );

        mockMvc.perform(
                get("/api/admin/orders")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken)
        )
        .andExpect(status().isForbidden());
    }

    // ❌ Update order → order not found → 400 Bad Request
    @Test
    void updateOrderStatus_shouldReturn400_whenOrderNotFound() throws Exception {

        String adminToken = jwtUtil.generateToken(
                "admin@farm2home.com",
                "ROLE_ADMIN"
        );

        mockMvc.perform(
                put("/api/admin/orders/{orderId}/status", 9999L)
                        .param("status", "DELIVERED")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error")
                .value("Order not found"));
    }

    // ❌ Invalid status value → 400 Bad Request
    @Test
    void updateOrderStatus_shouldReturn400_whenStatusIsInvalid() throws Exception {

        String adminToken = jwtUtil.generateToken(
                "admin@farm2home.com",
                "ROLE_ADMIN"
        );

        mockMvc.perform(
                put("/api/admin/orders/{orderId}/status", 1L)
                        .param("status", "INVALID_STATUS")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
        )
        .andExpect(status().isBadRequest());
    }
}
