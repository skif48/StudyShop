package com.shop;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */

@Transactional
public interface ProductDAO extends CrudRepository<Product, String> {
    Product findByUUID(String uuid);
}
