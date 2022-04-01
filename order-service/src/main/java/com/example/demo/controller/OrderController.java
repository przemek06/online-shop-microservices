package com.example.demo.controller;

import com.example.demo.dto.OrderResponseDto;
import com.example.demo.dto.ProductOrderDto;
import com.example.demo.dto.ProductTicketDto;
import com.example.demo.service.OrderService;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
public class OrderController {

    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @DeleteMapping("/api/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        try{
            orderService.deleteOrder(id);
            return ResponseEntity.ok(null);
        } catch (FeignException.NotFound|FeignException.ServiceUnavailable e){
            return ResponseEntity.status(503).build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<List<ProductTicketDto>> getTicketsInOrder(@PathVariable Long id){
        try{
            return ResponseEntity.ok(orderService.getTicketsInOrder(id));
        }  catch (FeignException.NotFound|FeignException.ServiceUnavailable e){
            return ResponseEntity.status(503).build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<ProductOrderDto>> getAllOrders(){
        try{
            return ResponseEntity.ok(orderService.getAllOrders());
        }  catch (FeignException.NotFound|FeignException.ServiceUnavailable e){
            return ResponseEntity.status(503).build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }


    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponseDto> makeOrder(@RequestBody @Valid ProductOrderDto productOrderDto){
        try{
            return ResponseEntity.ok(orderService.makeOrder(productOrderDto));
        } catch (ValidationException e){
            return ResponseEntity.badRequest().build();
        } catch (FeignException.NotFound|FeignException.ServiceUnavailable e){
            return ResponseEntity.status(503).build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }



    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<OrderResponseDto> handleBadOrderFormat(){
        return ResponseEntity.badRequest().build();
    }
}
