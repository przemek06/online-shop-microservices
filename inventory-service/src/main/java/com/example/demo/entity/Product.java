package com.example.demo.entity;

import lombok.*;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.LockModeType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Product {
    @Id
    private String code;
    private String name;
    private Integer amountAvailable;
}
