package com.example.demo.service;

import com.example.demo.client.InventoryClient;
import com.example.demo.dto.OrderResponseDto;
import com.example.demo.dto.ProductOrderDto;
import com.example.demo.dto.ProductTicketDto;
import com.example.demo.model.AppUserDetails;
import com.example.demo.stream.NotificationSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class OrderService {

    InventoryClient inventoryClient;
    NotificationSender notificationSender;

    public OrderService(InventoryClient inventoryClient, NotificationSender notificationSender) {
        this.inventoryClient = inventoryClient;
        this.notificationSender = notificationSender;
    }

    public void deleteOrder(Long id){
        inventoryClient.deleteOrder(id);
    }

    public List<ProductTicketDto> getTicketsInOrder(Long id){
        return inventoryClient.getTicketsInOrder(id);
    }

    public List<ProductOrderDto> getAllOrders(){
        return inventoryClient.getAllOrders();
    }

    public OrderResponseDto makeOrder(ProductOrderDto productOrderDto) {
        checkIfTicketValid(productOrderDto.getTickets());
        productOrderDto.setOwner(extractUsername());
        OrderResponseDto response = inventoryClient.makeOrder(productOrderDto);
        notificationSender.sendNotification(productOrderDto);
        return response;
    }

    public void checkIfTicketValid(List<ProductTicketDto> tickets){
        if(tickets.stream().anyMatch(t->t.getProductAmount()==null || t.getProductCode()==null)){
            throw new ValidationException();
        }
    }

    private String extractUsername() {
        return (((AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername();
    }


}
