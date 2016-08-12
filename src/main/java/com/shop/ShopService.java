package com.shop;

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
    private ProductDAO productDAO;

    public ShopService(){

    }

    public void setShopRepository(ShopRepository shopRepository){
        this.shopRepository = shopRepository;
    }

    public HttpStatus putProduct(Product product){
        if(product != null) {
            this.shopRepository.addProduct(product.getUuid(), product);
            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus putProductUsingDAO(Product product){
        try{
            productDAO.save(product);
        } catch (Exception exc){
            exc.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.OK;
    }

    public Product getProductUsingDAO(UUID uuid){
        Product product = null;
        try{
            product = productDAO.findByUUID(uuid.toString());
        } catch (Exception exc){
            exc.printStackTrace();
        }

        return product;
    }

    public Collection<Product> getAllProductsUsingDAO(){
        Iterable<Product> products = null;
        try{
            products = productDAO.findAll();
        } catch (Exception exc){
            exc.printStackTrace();
        }

        return (Collection<Product>) products;
    }

    public Product getProductByUUID(UUID uuid){
        return this.shopRepository.getProductByUUID(uuid);
    }

    public ArrayList<Product> getAllProducts(){
        return new ArrayList<>(this.shopRepository.getAllProducts());
    }

    public HttpStatus deleteProductByUUID(UUID uuid){
        try {
            this.shopRepository.deleteProductByUUID(uuid);
            return HttpStatus.OK;
        } catch (NullPointerException npe){
            return HttpStatus.BAD_REQUEST;
        } catch (Exception exc){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
