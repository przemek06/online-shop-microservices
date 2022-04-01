package com.example.demo.repository;

import com.example.demo.entity.ProductTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<ProductTicket, Long> {
    List<ProductTicket> findAllByOrderRefId(Long orderId);
}