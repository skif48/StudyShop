package com.shop;

import com.shop.entity.Product;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@org.springframework.stereotype.Repository
public class ShopRepository {
    private final Map<UUID, Product> repository;

    public ShopRepository() {
        this.repository = new ConcurrentHashMap<>();
    }

    public Product getProductByUUID(UUID uuid){
        return this.repository.get(uuid);
    }

    public void addProduct(UUID uuid, Product product){
        this.repository.put(uuid, product);
    }

    public Collection<Product> getAllProducts(){
        return this.repository.values();
    }

    public void deleteProductByUUID(UUID uuid){
        this.repository.remove(uuid);
    }
}
