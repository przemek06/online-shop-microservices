package com.example.demo.controller;

import com.example.demo.dto.OrderResponseDto;
import com.example.demo.dto.ProductOrderDto;
import com.example.demo.dto.ProductTicketDto;
import com.example.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/inventory/orders")
    public ResponseEntity<OrderResponseDto> makeOrder(@RequestBody ProductOrderDto order) {
        try {
            return ResponseEntity.ok(orderService.makeOrder(order));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(OrderService.buildNotAvailableResponse());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/api/inventory/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/api/inventory/orders")
    public ResponseEntity<List<ProductOrderDto>> getAllOrders() {
        try {
            return ResponseEntity.ok(orderService.getAllOrders());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/api/inventory/orders/{id}")
    public ResponseEntity<List<ProductTicketDto>> getTicketsInOrder(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.getTicketsInOrder(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


}
