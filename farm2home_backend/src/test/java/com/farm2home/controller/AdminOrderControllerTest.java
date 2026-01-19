package com.farm2home.controller;

import com.farm2home.BaseTest;
import com.farm2home.entity.Order;
import com.farm2home.enums.OrderStatus;
import com.farm2home.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.test.context.support.WithMockUser;

@AutoConfigureMockMvc
class AdminOrderControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    // ✅ GET ALL ORDERS (ADMIN ACCESS)
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllOrders() throws Exception {

        Order order1 = new Order();
        order1.setId(1L);

        Order order2 = new Order();
        order2.setId(2L);

        when(orderService.getAllOrders())
                .thenReturn(List.of(order1, order2));

        mockMvc.perform(
                get("/api/admin/orders")
        )
        .andExpect(status().isOk());
    }

    // ✅ UPDATE ORDER STATUS (ADMIN ACCESS)
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateOrderStatus() throws Exception {

        when(orderService.updateOrderStatus(1L, OrderStatus.DELIVERED))
                .thenReturn(null);

        mockMvc.perform(
                put("/api/admin/orders/{orderId}/status", 1L)
                        .param("status", "DELIVERED")
        )
        .andExpect(status().isOk());
    }
}
