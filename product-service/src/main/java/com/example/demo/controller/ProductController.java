package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        try {
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (FeignException.NotFound|FeignException.ServiceUnavailable e){
            return ResponseEntity.status(503).build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/api/products/{code}")
    public ResponseEntity<ProductDto> getProductByCode(@PathVariable String code){
        try {
            return ResponseEntity.ok(productService.getProduct(code));
        } catch (FeignException.NotFound|FeignException.ServiceUnavailable e){
            return ResponseEntity.status(503).build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/api/products")
    public ResponseEntity<Void> saveProduct(@RequestBody @Valid ProductDto productDto){
        try {
            productService.saveProduct(productDto);
            return ResponseEntity.ok().build();
        } catch (FeignException.NotFound|FeignException.ServiceUnavailable e){
            return ResponseEntity.status(503).build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/api/products/{code}")
    public ResponseEntity<Void> deleteProductByCode(@PathVariable String code){
        try {
            productService.deleteProduct(code);
            return ResponseEntity.ok().build();
        } catch (FeignException.NotFound|FeignException.ServiceUnavailable e){
            return ResponseEntity.status(503).build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Void> handleBadProductDto(){
        return ResponseEntity.badRequest().build();
    }
}
