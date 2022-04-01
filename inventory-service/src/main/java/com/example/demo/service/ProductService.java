package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.utils.ProductUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductUtils::entityToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getByCode(String code) {
        Optional<Product> optional = productRepository.findById(code);
        if(optional.isEmpty()) return null;
        return optional.map(ProductUtils::entityToDto).get();
    }

    @Transactional
    public void deleteByCode(String code) {
        productRepository.deleteById(code);
    }

    @Transactional
    public void saveProduct(ProductDto productDto) {
        productRepository.save(ProductUtils.dtoToEntity(productDto));
    }


}
