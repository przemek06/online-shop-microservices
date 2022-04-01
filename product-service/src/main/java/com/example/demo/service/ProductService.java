package com.example.demo.service;

import com.example.demo.client.InventoryClient;
import com.example.demo.dto.ProductDto;
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

    public ProductDto getProduct(String code) {
        return inventoryClient.getProduct(code);
    }

    public void saveProduct(ProductDto productDto){
        inventoryClient.saveProduct(productDto);
    }

    public void deleteProduct(String code){
        inventoryClient.deleteProduct(code);
    }

}
