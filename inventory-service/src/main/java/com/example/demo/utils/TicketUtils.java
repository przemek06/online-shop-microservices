package com.example.demo.utils;

import com.example.demo.dto.ProductTicketDto;
import com.example.demo.entity.ProductTicket;

public class TicketUtils {
    public static ProductTicket dtoToEntity(ProductTicketDto dto){
        return ProductTicket.builder()
                .productAmount(dto.getProductAmount())
                .productCode(dto.getProductCode())
                .build();
    }

    public static ProductTicketDto entityToDto(ProductTicket entity){
        return ProductTicketDto.builder()
                .productAmount(entity.getProductAmount())
                .productCode(entity.getProductCode())
                .build();
    }
}
