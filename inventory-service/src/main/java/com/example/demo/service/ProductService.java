package com.example.demo.service;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.utils.ProductUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts(){
        return productRepository.findAll().stream()
                .map(ProductUtils::entityToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getByCode(String code){
        Optional<Product> optional = productRepository.findById(code);
        return optional.map(ProductUtils::entityToDto).orElseGet(ProductDto::new);
    }

    public Boolean deleteByCode(String code){
        if(!productRepository.existsById(code)) return false;
        productRepository.deleteById(code);
        return false;
    }

    public Boolean updateProduct(ProductDto productDto){
        Optional<Product> optional = productRepository.findById(productDto.getCode());
        if(optional.isEmpty()) return false;
        Product product = optional.get();
        ProductUtils.updateFromDto(productDto, product);
        deleteByCode(product.getCode());
        productRepository.save(product);
        return true;
    }

    public Boolean saveProduct(ProductDto productDto){
        if(productRepository.existsById(productDto.getCode())) return false;
        productRepository.save(ProductUtils.dtoToEntity(productDto));
        return true;
    }

    public Boolean reserveProducts(List<OrderDto> orders){
        if(!checkOrders(orders)) return false;
        for(OrderDto order: orders){
            updateAmountAvailable(order.getProductCode(), order.getProductAmount());
        }
        return true;
    }

    private void updateAmountAvailable(String code, Integer amount){
        Product product = productRepository.findById(code).get();
        product.setAmountAvailable(product.getAmountAvailable()-amount);
        productRepository.deleteById(product.getCode());
        productRepository.save(product);
    }

    private Boolean checkOrders(List<OrderDto> orders){
        for(OrderDto order: orders){
            Optional<Product> optional = productRepository.findById(order.getProductCode());
            if(optional.isEmpty()) return false;
            Product product = optional.get();
            if(product.getAmountAvailable()<order.getProductAmount()) return false;
        }
        return true;
    }
}
