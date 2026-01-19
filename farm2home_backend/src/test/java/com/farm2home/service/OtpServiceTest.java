package com.farm2home.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OtpServiceTest {

    private OtpService otpService;

    @BeforeEach
    void setUp() {
        otpService = new OtpService();
    }

    @Test
    void generateOtp_shouldReturnSixDigitOtp() {

        String otp = otpService.generateOtp("9999999999");

        assertNotNull(otp);
        assertEquals(6, otp.length());
        assertTrue(otp.matches("\\d{6}"));
    }

    @Test
    void verifyOtp_shouldReturnTrue_forCorrectOtp() {

        String phone = "9999999999";
        String otp = otpService.generateOtp(phone);

        boolean result = otpService.verifyOtp(phone, otp);

        assertTrue(result);
    }

    @Test
    void verifyOtp_shouldReturnFalse_forWrongOtp() {

        String phone = "9999999999";
        otpService.generateOtp(phone);

        boolean result = otpService.verifyOtp(phone, "000000");

        assertFalse(result);
    }

    @Test
    void verifyOtp_shouldBeOneTimeUse() {

        String phone = "9999999999";
        String otp = otpService.generateOtp(phone);

        assertTrue(otpService.verifyOtp(phone, otp));   // first time
        assertFalse(otpService.verifyOtp(phone, otp));  // second time
    }

    @Test
    void verifyOtp_shouldReturnFalse_whenExpired() throws Exception {

        String phone = "9999999999";
        String otp = otpService.generateOtp(phone);

        // 🔧 Force expiry using reflection (test-only)
        Field storeField = OtpService.class.getDeclaredField("otpStore");
        storeField.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> store =
                (Map<String, Object>) storeField.get(otpService);

        Object otpData = store.get(phone);

        Field expiryField = otpData.getClass().getDeclaredField("expiry");
        expiryField.setAccessible(true);
        expiryField.set(otpData, Instant.now().minusSeconds(1)); // expired

        boolean result = otpService.verifyOtp(phone, otp);

        assertFalse(result);
    }
}
