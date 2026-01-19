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
class JwtSecurityTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ ADMIN token should access admin API
    @Test
    void adminTokenCanAccessAdminOrders() throws Exception {

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

    // ❌ No token should be forbidden
    @Test
    void noTokenCannotAccessAdminOrders() throws Exception {

        mockMvc.perform(
                get("/api/admin/orders")
        )
        .andExpect(status().isForbidden());
    }
 // ❌ Invalid token should be forbidden
    @Test
    void invalidTokenCannotAccessAdminOrders() throws Exception {

        mockMvc.perform(
                get("/api/admin/orders")
                        .header("Authorization", "Bearer invalid.jwt.token")
        )
        .andExpect(status().isForbidden());
    }
 // ❌ USER token should NOT access admin API
    @Test
    void userTokenCannotAccessAdminOrders() throws Exception {

        String userToken = jwtUtil.generateToken(
                "user@farm2home.com",
                "ROLE_USER"
        );

        mockMvc.perform(
                get("/api/admin/orders")
                        .header("Authorization", "Bearer " + userToken)
        )
        .andExpect(status().isForbidden());
    }
 // ✅ Public API should be accessible without token
    @Test
    void publicApiAccessibleWithoutToken() throws Exception {

        mockMvc.perform(
                get("/api/products")
        )
        .andExpect(status().isOk());
    }


}
