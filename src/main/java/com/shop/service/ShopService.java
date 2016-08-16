package com.shop.service;

import com.shop.entity.Characteristic;
import com.shop.entity.Product;
import com.shop.repository.CharacteristicRepository;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@Service
public class ShopService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CharacteristicRepository characteristicRepository;

    public ShopService() {
    }

    public void putProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProduct(UUID uuid) {
        Product product = new Product();
        try {
            System.out.println(uuid.toString());
            product = productRepository.findByUuid(uuid.toString());
            if (product == null) {
                product = new Product();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            return product;
        }

        return product;
    }

    public Collection<Product> getAllProducts() {
        Iterable<Product> products = null;
        try {
            products = productRepository.findAll();
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return (Collection<Product>) products;
    }

    public void deleteProductByUUID(UUID uuid) {
        productRepository.deleteByUuid(uuid.toString());
    }

    public void deleteAllProducts(){
        productRepository.deleteAll();
    }

    public void addCharacteristicsToProduct(Characteristic characteristic){
        characteristicRepository.save(characteristic);
    }
}