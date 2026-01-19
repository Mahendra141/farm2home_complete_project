package com.farm2home.security;

import com.farm2home.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class JwtSecurityIntegrationTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    // ❌ No token → forbidden
    @Test
    void adminApi_shouldFail_whenNoTokenProvided() throws Exception {

        mockMvc.perform(
                get("/api/admin/orders")
        )
        .andExpect(status().isForbidden());
    }

    // ❌ Invalid token → forbidden
    @Test
    void adminApi_shouldFail_whenInvalidTokenProvided() throws Exception {

        mockMvc.perform(
                get("/api/admin/orders")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer invalid.jwt.token")
        )
        .andExpect(status().isForbidden());
    }

    // ❌ USER role token → forbidden
    @Test
    void adminApi_shouldFail_whenUserRoleTokenProvided() throws Exception {

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

    // ✅ ADMIN role token → success
    @Test
    void adminApi_shouldSucceed_whenAdminRoleTokenProvided() throws Exception {

        String adminToken = jwtUtil.generateToken(
                "admin@farm2home.com",
                "ROLE_ADMIN"
        );

        mockMvc.perform(
                get("/api/admin/orders")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
        )
        .andExpect(status().isOk());
    }
}
