package com.shop.repository;

import com.shop.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Vladyslav Usenko on 18.08.2016.
 */
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    @Query("select p from ProductType p where p.name = :name")
    ProductType findByTypeName(@Param(value = "name") String name);
}
