package com.farm2home.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static class OtpData {
        String otp;
        Instant expiry;
    }

    private final Map<String, OtpData> otpStore = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public String generateOtp(String phone) {
        String otp = String.valueOf(100000 + random.nextInt(900000));

        OtpData data = new OtpData();
        data.otp = otp;
        data.expiry = Instant.now().plusSeconds(120); // ⏱️ 2 mins

        otpStore.put(phone, data);
        return otp;
    }

    public boolean verifyOtp(String phone, String otp) {
        OtpData data = otpStore.get(phone);

        if (data == null) return false;
        if (Instant.now().isAfter(data.expiry)) return false;

        boolean valid = data.otp.equals(otp);

        if (valid) {
            otpStore.remove(phone); // 🔐 one-time use
        }

        return valid;
    }
}
