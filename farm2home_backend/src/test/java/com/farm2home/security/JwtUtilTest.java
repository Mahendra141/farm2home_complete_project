package com.farm2home.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void generateToken_shouldReturnValidToken() {

        String token = jwtUtil.generateToken(
                "admin@farm2home.com",
                "ROLE_ADMIN"
        );

        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void validateToken_shouldFail_forInvalidToken() {

        boolean result = jwtUtil.validateToken("invalid.jwt.token");

        assertFalse(result);
    }

    @Test
    void extractEmail_shouldReturnCorrectEmail() {

        String token = jwtUtil.generateToken(
                "admin@farm2home.com",
                "ROLE_ADMIN"
        );

        assertEquals(
                "admin@farm2home.com",
                jwtUtil.extractEmail(token)
        );
    }

    @Test
    void extractRole_shouldReturnCorrectRole() {

        String token = jwtUtil.generateToken(
                "admin@farm2home.com",
                "ROLE_ADMIN"
        );

        assertEquals(
                "ROLE_ADMIN",
                jwtUtil.extractRole(token)
        );
    }
}
