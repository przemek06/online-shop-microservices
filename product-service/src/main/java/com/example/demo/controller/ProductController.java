package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
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
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/api/products/{code}")
    public ResponseEntity<ProductDto> getProductByCode(@PathVariable String code){
        return productService.getProduct(code);
    }

    @PostMapping("/api/products")
    public ResponseEntity<String> saveProduct(@RequestBody @Valid ProductDto productDto){
        return productService.saveProduct(productDto);
    }

    @PutMapping("/api/products")
    public ResponseEntity<String> updateProduct(@RequestBody @Valid ProductDto productDto){
        return productService.updateProduct(productDto);
    }

    @DeleteMapping("/api/products/{code}")
    public ResponseEntity<String> deleteProductByCode(@PathVariable String code){
        return productService.deleteProduct(code);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleBadProductDto(ValidationException exception){
        return ResponseEntity.badRequest().body("Product JSON in wrong format");
    }
}
