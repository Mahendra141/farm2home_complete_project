package com.farm2home.service;

import com.farm2home.dto.OrderRequestDTO;
import com.farm2home.dto.OrderResponseDTO;
import com.farm2home.entity.Order;
import com.farm2home.enums.OrderStatus;
import com.farm2home.repository.OrderRepository;
import com.farm2home.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    // ---------- USER ----------

    @Test
    void createOrder_shouldSaveOrder_andReturnResponse() {

        OrderRequestDTO request = new OrderRequestDTO();
        request.setTotalAmount(500.0);
        request.setLatitude(17.385);
        request.setLongitude(78.486);

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setTotalAmount(500.0);
        savedOrder.setStatus(OrderStatus.PLACED);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        OrderResponseDTO response =
                orderService.createOrder(request);

        assertNotNull(response);
        assertEquals(1L, response.getOrderId());
        assertEquals(500.0, response.getTotalAmount());
        assertEquals("PLACED", response.getStatus());

        verify(orderRepository).save(any(Order.class));
    }

    // ---------- ADMIN ----------

    @Test
    void getAllOrders_shouldReturnOrders() {

        when(orderRepository.findAll())
                .thenReturn(List.of(new Order(), new Order()));

        List<Order> orders = orderService.getAllOrders();

        assertEquals(2, orders.size());
        verify(orderRepository).findAll();
    }

    @Test
    void updateOrderStatus_shouldUpdateStatus() {

        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PLACED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderRepository.save(order))
                .thenReturn(order);

        Order updated =
                orderService.updateOrderStatus(1L, OrderStatus.DELIVERED);

        assertEquals(OrderStatus.DELIVERED, updated.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrderStatus_shouldThrowException_whenOrderNotFound() {

        when(orderRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> orderService.updateOrderStatus(99L, OrderStatus.CANCELLED)
        );

        assertEquals("Order not found", ex.getMessage());
    }
}
