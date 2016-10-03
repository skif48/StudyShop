package com.shop.repository.products;

import com.shop.domain.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vladyslav Usenko on 17.08.2016.
 */
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    @Transactional
    @Query("select a from Attribute a where a.name = :name")
    Attribute findByName(@Param("name") String name);
}
