package com.farm2home.controller;

import com.farm2home.BaseTest;
import com.farm2home.entity.Admin;
import com.farm2home.entity.Role;
import com.farm2home.repository.AdminRepository;
import com.farm2home.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class AdminAuthControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private JwtUtil jwtUtil;

    // ✅ SUCCESS CASE
    @Test
    void adminLoginSuccess() throws Exception {

        Admin admin = new Admin();
        admin.setEmail("admin@farm2home.com");
        admin.setRole(Role.ROLE_ADMIN);

        when(adminRepository.findByEmail("admin@farm2home.com"))
                .thenReturn(Optional.of(admin));

        when(jwtUtil.generateToken("admin@farm2home.com", "ROLE_ADMIN"))
                .thenReturn("mock-jwt-token");

        String json = """
        {
          "email": "admin@farm2home.com",
          "password": "admin123"
        }
        """;

        mockMvc.perform(
                post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }

    // ❌ FAILURE CASE — ADMIN NOT FOUND
    @Test
    void adminLoginFailsWhenAdminNotFound() throws Exception {

        when(adminRepository.findByEmail("wrong@farm2home.com"))
                .thenReturn(Optional.empty());

        String json = """
        {
          "email": "wrong@farm2home.com",
          "password": "admin123"
        }
        """;

        mockMvc.perform(
                post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error").value("Admin not found"));
    }
}
