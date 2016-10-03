package com.shop.repository.products;

import com.shop.domain.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vladyslav Usenko on 18.08.2016.
 */
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    @Transactional
    @Query("select p from ProductType p where p.name = :name")
    ProductType findByTypeName(@Param(value = "name") String name);
}
