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
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @ManyToOne(targetEntity = Transaction.class,
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.REMOVE)
    @JoinColumn(name = "transaction_ref", referencedColumnName = "transaction_id")
    Transaction transactionRef;
    String productCode;
    String productAmount;
}
