package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "update product" +
            " set amount_available=(select amount_available from product where code=:code)-:subtracted" +
            " where code=:code")
    int updateProductAmount(int subtracted, String code);

}
