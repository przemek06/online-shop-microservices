package com.example.demo.service;

import com.example.demo.client.InventoryClient;
import com.example.demo.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    InventoryClient inventoryClient;

    public ProductService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    public List<ProductDto> getAllProducts(){
        
        return inventoryClient.getAllProducts();
    }

    public ResponseEntity<ProductDto> getProduct(String code) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        ProductDto productDto= inventoryClient.getProduct(code);
        if(productDto.getCode()==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productDto);
    }

    public ResponseEntity<String> saveProduct(ProductDto productDto){
        Boolean saved = inventoryClient.saveProduct(productDto);
        if(saved) return ResponseEntity.ok("Product saved");
        return ResponseEntity.badRequest().body("Product with that code already exists!");
    }

    public ResponseEntity<String> deleteProduct(String code){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Boolean deleted =  inventoryClient.deleteProduct(code);
        if(deleted) return ResponseEntity.ok("Product deleted");
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> updateProduct(ProductDto productDto){
        Boolean updated =  inventoryClient.updateProduct(productDto);
        if(updated) return ResponseEntity.ok("Product updated");
        return ResponseEntity.notFound().build();
    }
}
