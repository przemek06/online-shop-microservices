package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_orders")
public class ProductOrder {
    @Id
    @Column(name = "product_order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String address;
    String owner;
    String ownerEmail;
}