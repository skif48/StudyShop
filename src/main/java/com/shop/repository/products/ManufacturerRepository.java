package com.shop.repository.products;

import com.shop.domain.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vladyslav Usenko on 26.09.2016.
 */
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
