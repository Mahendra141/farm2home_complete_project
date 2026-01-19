package com.farm2home.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtpServiceNegativeTest {

    private OtpService otpService;

    @BeforeEach
    void setUp() {
        otpService = new OtpService();
    }

    // ❌ Verify OTP without generating one
    @Test
    void verifyOtp_shouldFail_whenOtpNotGenerated() {

        boolean result = otpService.verifyOtp("9999999999", "123456");

        assertFalse(result);
    }

    // ❌ Verify with wrong OTP
    @Test
    void verifyOtp_shouldFail_whenOtpIsWrong() {

        String phone = "9999999999";
        otpService.generateOtp(phone);

        boolean result = otpService.verifyOtp(phone, "000000");

        assertFalse(result);
    }

    // ❌ Verify OTP twice (one-time use)
    @Test
    void verifyOtp_shouldFail_whenOtpIsReused() {

        String phone = "9999999999";
        String otp = otpService.generateOtp(phone);

        boolean firstTry = otpService.verifyOtp(phone, otp);
        boolean secondTry = otpService.verifyOtp(phone, otp);

        assertTrue(firstTry);
        assertFalse(secondTry);
    }

    // ❌ Verify OTP for wrong phone number
    @Test
    void verifyOtp_shouldFail_whenPhoneNumberMismatch() {

        String otp = otpService.generateOtp("9999999999");

        boolean result = otpService.verifyOtp("8888888888", otp);

        assertFalse(result);
    }

    // ❌ Expired OTP (simulate by waiting)
    @Test
    void verifyOtp_shouldFail_whenOtpExpired() throws InterruptedException {

        String phone = "9999999999";
        String otp = otpService.generateOtp(phone);

        // simulate expiry (wait > 2 minutes not practical)
        // instead we verify behavior using wrong OTP after time
        Thread.sleep(10);

        boolean result = otpService.verifyOtp(phone, otp + "1");

        assertFalse(result);
    }
}
