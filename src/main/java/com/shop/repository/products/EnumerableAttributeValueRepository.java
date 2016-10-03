package com.shop.repository.products;

import com.shop.domain.entity.EnumerableAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vladyslav Usenko on 03.10.2016.
 */
public interface EnumerableAttributeValueRepository extends JpaRepository<EnumerableAttributeValue, Long> {
    @Transactional
    @Query("select enumrableAttributeValue.id " +
            "from Attribute attributes, EnumerableAttributeValue enumrableAttributeValue\n" +
            "where enumrableAttributeValue.attribute = attributes.attributeID and attributes.name = :name")
    List<Object> findEnumerableAttributeValueByName(@Param("name") String name);

    @Transactional
    @Query("select enumerableAttributeValue.value " +
            "from EnumerableAttributeValue enumerableAttributeValue, Product product, Attribute attribute, AttributeValue attributeValue " +
            "where product.uuid = :uuid and attributeValue.attribute = attribute.attributeID and attributeValue.product = product.productID and attribute.name = :name and enumerableAttributeValue.id = attributeValue.enumerableAttributeValue")
    String findEnumerableAttributeValueOfProductByUUIDAndName(@Param("name") String name, @Param("uuid") String uuid);
}
