package com.farm2home.service;

import com.farm2home.dto.OrderRequestDTO;
import com.farm2home.dto.OrderResponseDTO;
import com.farm2home.entity.Order;
import com.farm2home.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    // USER
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    // ADMIN
    List<Order> getAllOrders();

    Order updateOrderStatus(Long orderId, OrderStatus status);
}
