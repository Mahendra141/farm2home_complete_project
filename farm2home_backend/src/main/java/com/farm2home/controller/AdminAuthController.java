package com.farm2home.controller;

import com.farm2home.entity.Admin;
import com.farm2home.repository.AdminRepository;
import com.farm2home.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;

    public AdminAuthController(AdminRepository adminRepository, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // (skip password check for now if already working)

        String token = jwtUtil.generateToken(
        	    admin.getEmail(),
        	    admin.getRole().name()
        	);

        return Map.of("token", token);
    }
}
