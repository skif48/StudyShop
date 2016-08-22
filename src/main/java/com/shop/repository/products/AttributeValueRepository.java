package com.shop.repository.products;

import com.shop.domain.entity.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vladyslav Usenko on 17.08.2016.
 */
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
}
