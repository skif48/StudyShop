package com.shop;

import com.shop.entity.Product;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;

    public ShopService(){

    }

    public void setShopRepository(ShopRepository shopRepository){
        this.shopRepository = shopRepository;
    }

    public HttpStatus putProduct(Product product){
        try{
            productRepository.save(product);
        } catch (Exception exc){
            exc.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.OK;
    }

    public Product getProduct(UUID uuid){
        Product product = new Product();
        try{
            System.out.println(uuid.toString());
            product = productRepository.findByUuid(uuid.toString());
            if(product == null){
                product = new Product();
            }
        } catch (Exception exc){
            exc.printStackTrace();
            return product;
        }

        return product;
    }

    public Collection<Product> getAllProducts(){
        Iterable<Product> products = null;
        try{
            products = productRepository.findAll();
        } catch (Exception exc){
            exc.printStackTrace();
        }

        return (Collection<Product>) products;
    }

    public HttpStatus deleteProductByUUID(UUID uuid){
        try {
            System.out.println("Deleted: " + productRepository.deleteByUuid(uuid.toString()));
            return HttpStatus.OK;
        } catch (NullPointerException npe){
            return HttpStatus.BAD_REQUEST;
        } catch (Exception exc){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
