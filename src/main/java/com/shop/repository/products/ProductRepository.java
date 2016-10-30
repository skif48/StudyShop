package com.shop.repository.products;

import com.shop.domain.entity.Attribute;
import com.shop.domain.entity.Manufacturer;
import com.shop.domain.entity.Product;
import com.shop.domain.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Attr;

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

    @Transactional
    @Query("select p from Product p where p.label like %:label%")
    List<Product> findByLabel(@Param("label") String label);

    @Transactional
    @Query("select distinct p.uuid " +
           "from Product p inner join p.values as value " +
           "inner join p.manufacturer as manufacturer " +
           "where p.manufacturer = :leftManufacturer or p.manufacturer = :rightManufacturer and" +
           " p.price between :leftPrice and :rightPrice and" +
           " (value.attribute = :coreAttribute and value.value between :leftCore and :rightCore or" +
           " value.attribute = :ramAttribute and value.value between :leftRam and :rightRam or" +
           " value.attribute = :weightAttribute and value.value between :leftWeight and :rightWeight)")
    List<String> advancedSearchPC(@Param("leftManufacturer")Manufacturer leftManufacturer,
                                  @Param("rightManufacturer") Manufacturer rightManufacturer,
                                  @Param("leftPrice") Double leftPrice,
                                  @Param("rightPrice") Double rightPrice,
                                  @Param("coreAttribute")Attribute coreAttribute,
                                  @Param("leftCore") Integer leftCore,
                                  @Param("rightCore") Integer rightCore,
                                  @Param("ramAttribute") Attribute ramAttribute,
                                  @Param("leftRam") Integer leftRam,
                                  @Param("rightRam") Integer rightRam,
                                  @Param("weightAttribute") Attribute weightAttribute,
                                  @Param("leftWeight") Integer leftWeight,
                                  @Param("rightWeight") Integer rightWeight);

    @Transactional
    @Query
    List<String> advancedSearchLaptop();

    @Transactional
    @Query
    List<String> advancedSearchTV();

    @Transactional
    @Query
    List<String> advancedSearchPhone();
}
