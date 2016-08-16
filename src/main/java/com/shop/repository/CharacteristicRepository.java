package com.shop.repository;

import com.shop.entity.Characteristic;
import com.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vladyslav Usenko on 16.08.2016.
 */
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
    @Query("select c from Characteristic c where c.uuid = :uuid")
    Product findByUuid(@Param("uuid") String uuid);

    @Transactional
    Object deleteByUuid(String uuid);
}
