package com.example.demo.utils;

import com.example.demo.dto.ProductOrderDto;
import com.example.demo.entity.ProductOrder;

public class OrderUtils {
    public static ProductOrder dtoToEntity(ProductOrderDto dto){
        return ProductOrder.builder()
                .address(dto.getAddress())
                .owner(dto.getOwner())
                .ownerEmail(dto.getOwnerEmail())
                .build();
    }

    public static ProductOrderDto entityToDto(ProductOrder entity){
        return ProductOrderDto.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .ownerEmail(entity.getOwnerEmail())
                .owner(entity.getOwner())
                .build();
    }
}
