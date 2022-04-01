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
@Table(name = "tickets")
public class ProductTicket {
    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @ManyToOne(targetEntity = ProductOrder.class,
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_ref", referencedColumnName = "product_order_id")
    ProductOrder orderRef;
    String productCode;
    Integer productAmount;
}