package com.example.demo.client;

import com.example.demo.dto.OrderResponseDto;
import com.example.demo.dto.ProductOrderDto;
import com.example.demo.dto.ProductTicketDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("inventory-service")
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.POST, path = "/api/inventory/orders")
    OrderResponseDto makeOrder(@RequestBody ProductOrderDto order);

    @RequestMapping(method = RequestMethod.GET, path = "/api/inventory/orders",
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    List<ProductOrderDto> getAllOrders();

    @RequestMapping(method = RequestMethod.GET, path = "/api/inventory/orders/{id}")
    List<ProductTicketDto> getTicketsInOrder(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.DELETE, path = "/api/inventory/orders/{id}")
    void deleteOrder(@PathVariable Long id);
}
