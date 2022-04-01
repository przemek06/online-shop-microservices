package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/inventory")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        try{
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/api/inventory/{code}")
    public ResponseEntity<ProductDto> getByCode(@PathVariable String code){
        try {
            return ResponseEntity.ok(productService.getByCode(code));
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/api/inventory/{code}")
    public ResponseEntity<Void> deleteByCode(@PathVariable String code){
        try {
            productService.deleteByCode(code);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/api/inventory")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductDto productDto){
        try {
            productService.saveProduct(productDto);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }    }


}
