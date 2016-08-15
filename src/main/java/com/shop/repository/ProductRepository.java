package com.shop.repository;

import com.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.uuid = :uuid")
    Product findByuuid(@Param("uuid") String uuid);
}
