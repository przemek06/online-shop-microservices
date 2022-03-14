package com.example.demo.service;

import com.example.demo.client.InventoryClient;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.TransactionDto;
import com.example.demo.dto.TransactionResponse;
import com.example.demo.entity.Order;
import com.example.demo.entity.Transaction;
import com.example.demo.model.AppUserDetails;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.stream.NotificationSender;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    TransactionRepository transactionRepository;
    OrderRepository orderRepository;
    InventoryClient inventoryClient;
    NotificationSender notificationSender;

    public TransactionService(TransactionRepository transactionRepository,
                              OrderRepository orderRepository,
                              InventoryClient inventoryClient,
                              NotificationSender notificationSender) {
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.notificationSender = notificationSender;
    }

    public List<OrderDto> findAllOrdersInTransaction(Long transactionId){
        return orderRepository.findAllByTransactionRefId(transactionId)
                .stream().map(this::buildOrderDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<TransactionResponse> finalizeTransaction(TransactionDto transactionDto){
        List<OrderDto> orders = transactionDto.getOrders();
        Boolean saved = inventoryClient.updateInventory(orders);
        if(!saved) return ResponseEntity.badRequest().body(buildBadRequestResponse());
        Transaction transaction = transactionRepository.save(dtoToEntity(transactionDto));
        saveOrders(transaction, orders);
        transactionDto.setId(transaction.getId());
        notificationSender.sendNotification(transactionDto);

        return ResponseEntity.ok().body(buildOKResponse(transaction.getId()));
    }

    private TransactionResponse buildOKResponse(Long id){
        return TransactionResponse.builder()
                .message("Ordered!!")
                .transactionId(id)
                .build();
    }

    private TransactionResponse buildBadRequestResponse(){
        return TransactionResponse.builder()
                .message("Some products are not available!")
                .build();
    }

    private void saveOrders(Transaction transaction, List<OrderDto> orders){
        for(OrderDto order: orders){
            orderRepository.save(buildOrder(transaction, order));
        }
    }

    private OrderDto buildOrderDto(Order order){
        return OrderDto.builder()
                .productAmount(order.getProductAmount())
                .productCode(order.getProductCode())
                .build();
    }

    private Order buildOrder(Transaction transaction, OrderDto order){
        return Order.builder()
                .transactionRef(transaction)
                .productAmount(order.getProductAmount())
                .productCode(order.getProductCode())
                .build();
    }

    private Transaction dtoToEntity(TransactionDto dto){
        return Transaction.builder()
                .owner(extractUsername())
                .ownerEmail(extractEmail(dto))
                .address(dto.getAddress())
                .build();
    }

    private String extractUsername(){
        return  (((AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername();
    }

    private String extractEmail(TransactionDto transactionDto){
        if(transactionDto.getOwnerEmail()==null){
            return  (((AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getEmail();
        }
        return transactionDto.getOwnerEmail();
    }
}
