package com.example.demo.client;

import com.example.demo.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("inventory-service")
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.PUT, path = "/api/inventory/reserve")
    Boolean updateInventory(@RequestBody List<OrderDto> orders);
}
