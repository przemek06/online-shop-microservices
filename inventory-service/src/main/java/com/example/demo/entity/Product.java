package com.example.demo.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document
public class Product {
    @MongoId
    private String code;
    private String name;
    private Integer amountAvailable;
}
