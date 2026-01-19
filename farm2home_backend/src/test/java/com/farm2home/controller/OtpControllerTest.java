package com.farm2home.controller;

import com.farm2home.BaseTest;
import com.farm2home.service.OtpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class OtpControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OtpService otpService;

    // ✅ SEND OTP
    @Test
    void shouldSendOtpSuccessfully() throws Exception {

        when(otpService.generateOtp("9999999999"))
                .thenReturn("123456");

        String json = """
        {
          "phone": "9999999999"
        }
        """;

        mockMvc.perform(
                post("/api/otp/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message")
                .value("OTP sent successfully"));
    }

    // ✅ VERIFY OTP - SUCCESS
    @Test
    void shouldVerifyOtpSuccessfully() throws Exception {

        when(otpService.verifyOtp("9999999999", "123456"))
                .thenReturn(true);

        String json = """
        {
          "phone": "9999999999",
          "otp": "123456"
        }
        """;

        mockMvc.perform(
                post("/api/otp/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.verified").value(true));
    }

    // ❌ VERIFY OTP - FAILURE
    @Test
    void shouldFailWhenOtpIsInvalid() throws Exception {

        when(otpService.verifyOtp("9999999999", "000000"))
                .thenReturn(false);

        String json = """
        {
          "phone": "9999999999",
          "otp": "000000"
        }
        """;

        mockMvc.perform(
                post("/api/otp/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message")
                .value("Invalid or expired OTP"));
    }
}
