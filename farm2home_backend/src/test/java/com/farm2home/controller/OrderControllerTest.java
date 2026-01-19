package com.farm2home.controller;

import com.farm2home.BaseTest;
import com.farm2home.dto.OrderResponseDTO;
import com.farm2home.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class OrderControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {

        // ✅ Mock service response (empty DTO is enough)
        OrderResponseDTO mockResponse = new OrderResponseDTO();
        when(orderService.createOrder(any())).thenReturn(mockResponse);

        // ✅ Valid request JSON (must satisfy @Valid)
        String orderJson = """
        		{
        		  "totalAmount": 100,
        		  "latitude": 17.3850,
        		  "longitude": 78.4867,
        		  "items": [
        		    {
        		      "productId": 1,
        		      "quantity": 2
        		    }
        		  ],
        		  "deliveryDetails": {
        		    "name": "Mahi",
        		    "address": "Hyderabad",
        		    "phone": "9999999999"
        		  }
        		}
        		""";


        mockMvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson)
        )
        .andExpect(status().isCreated());
    }
}
