package com.example.demo.controller;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/inventory")
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/api/inventory/{code}")
    public ProductDto getByCode(@PathVariable String code){
        return productService.getByCode(code);
    }

    @DeleteMapping("/api/inventory/{code}")
    public Boolean deleteByCode(@PathVariable String code){
        return productService.deleteByCode(code);
    }

    @PutMapping("/api/inventory")
    public Boolean updateProduct(@RequestBody ProductDto productDto){
        return productService.updateProduct(productDto);
    }

    @PostMapping("/api/inventory")
    public Boolean saveProduct(@RequestBody ProductDto productDto){
        return productService.saveProduct(productDto);
    }

    @PutMapping("/api/inventory/reserve")
    public Boolean reserveProducts(@RequestBody List<OrderDto> orders){
        return productService.reserveProducts(orders);
    }
}
