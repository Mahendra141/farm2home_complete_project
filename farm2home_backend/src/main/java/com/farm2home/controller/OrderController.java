package com.farm2home.controller;


import com.farm2home.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.farm2home.dto.OrderRequestDTO;
import com.farm2home.dto.OrderResponseDTO;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody OrderRequestDTO orderRequestDTO) {

        OrderResponseDTO response = orderService.createOrder(orderRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



}
