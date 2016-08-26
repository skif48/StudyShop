package com.shop.repository.products;

import com.shop.domain.entity.Product;
import com.shop.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Vladyslav Usenko on 26.08.2016.
 */
public interface ProductImageRepository  extends JpaRepository<ProductImage, Long>{
    @Query("select image from ProductImage image where image.product = :product")
    List<ProductImage> getImagesForProduct(@Param(value = "product") Product product);
}
