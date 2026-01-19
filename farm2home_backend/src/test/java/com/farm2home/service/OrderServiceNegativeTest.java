package com.farm2home.service;

import com.farm2home.dto.OrderRequestDTO;
import com.farm2home.entity.Order;
import com.farm2home.enums.OrderStatus;
import com.farm2home.repository.OrderRepository;
import com.farm2home.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceNegativeTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ❌ createOrder(null) → NullPointerException
    @Test
    void createOrder_shouldFail_whenRequestIsNull() {

        assertThrows(NullPointerException.class, () ->
                orderService.createOrder(null)
        );
    }

    // ❌ updateOrderStatus → order not found
    @Test
    void updateOrderStatus_shouldFail_whenOrderNotFound() {

        when(orderRepository.findById(999L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                orderService.updateOrderStatus(999L, OrderStatus.DELIVERED)
        );

        assertTrue(ex.getMessage().contains("Order not found"));
    }

    // ✅ getAllOrders → empty DB
    @Test
    void getAllOrders_shouldReturnEmptyList_whenNoOrdersExist() {

        when(orderRepository.findAll())
                .thenReturn(Collections.emptyList());

        assertTrue(orderService.getAllOrders().isEmpty());
    }
}
