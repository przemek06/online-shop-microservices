package com.example.demo.controller;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.TransactionDto;
import com.example.demo.dto.TransactionResponse;
import com.example.demo.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/api/order/{id}")
    public List<OrderDto> getOrdersInTransaction(@PathVariable Long id){
        return transactionService.findAllOrdersInTransaction(id);
    }

    @PostMapping("/api/order")
    public ResponseEntity<TransactionResponse> finalizeTransaction(@RequestBody TransactionDto transactionDto){
        return transactionService.finalizeTransaction(transactionDto);
    }
}
