package com.farm2home.controller;

import com.farm2home.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "*")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    // 🔐 SEND OTP
    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> req) {
        String phone = req.get("phone");
        String otp = otpService.generateOtp(phone);

        // ✅ TEMP: LOG OTP (replace with WhatsApp API later)
        System.out.println("OTP for " + phone + " = " + otp);

        return ResponseEntity.ok(Map.of(
                "message", "OTP sent successfully"
        ));
    }

    // ✅ VERIFY OTP
    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> req) {
        String phone = req.get("phone");
        String otp = req.get("otp");

        boolean valid = otpService.verifyOtp(phone, otp);

        if (!valid) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Invalid or expired OTP")
            );
        }

        return ResponseEntity.ok(
                Map.of("verified", true)
        );
    }
}
