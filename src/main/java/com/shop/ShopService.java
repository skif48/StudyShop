package com.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    public ShopService(){

    }

    public HttpStatus putProduct(Product product){
        if(product != null) {
            this.shopRepository.addProduct(product.getUuid(), product);
            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }

    public Product getProductByUUID(UUID uuid){
        return this.shopRepository.getProductByUUID(uuid);
    }
}
