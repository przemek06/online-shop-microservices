package com.example.demo.utils;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import org.springframework.beans.BeanUtils;

public class ProductUtils {
    public static ProductDto entityToDto(Product product){
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto){
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }

    public static void updateFromDto(ProductDto from, Product to){
        if(!from.getName().equals(to.getName())) to.setName(from.getName());
        if(!from.getAmountAvailable().equals(to.getAmountAvailable())) to.setAmountAvailable(from.getAmountAvailable());
    }
}
