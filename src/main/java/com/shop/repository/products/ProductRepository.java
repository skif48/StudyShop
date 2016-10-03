package com.shop.repository.products;

import com.shop.domain.entity.Product;
import com.shop.domain.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Query("select p from Product p where p.uuid = :uuid")
    Product findByUuid(@Param("uuid") String uuid);

    @Transactional
    Object deleteByUuid( String uuid);

    @Transactional
    @Query( "select attr.name, attrval.value " +
            "from Product p, Attribute attr, AttributeValue attrval " +
            "where attrval.product = p.productID and attrval.attribute = attr.attributeID and p.uuid = :uuid")
    List<Object[]> getFullInfoByUuid(@Param("uuid") String uuid);

    @Transactional
    @Query("select p from Product p where p.type = :type")
    List<Product> findByType(@Param("type") ProductType type);
}
