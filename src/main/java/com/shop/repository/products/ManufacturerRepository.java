package com.shop.repository.products;

import com.shop.domain.entity.Manufacturer;
import com.shop.domain.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vladyslav Usenko on 26.09.2016.
 */
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    @Transactional
    @Query("select m from Manufacturer m where m.name = :name")
    Manufacturer findByName(@Param(value = "name") String name);
}
