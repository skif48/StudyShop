package com.shop;

import com.shop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@RestController
public class ShopController {

    @Autowired
    private ShopService service;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity putProduct(@RequestBody String productJSON){
        Product product = null;

        try {
            product = (Product) Tools.parseObjectFromJSON(productJSON);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(product != null) {
            HttpStatus status = service.putProduct(product);
            return new ResponseEntity<>(status);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getProduct(@RequestHeader("ProductID") UUID uuid){
        if(Tools.isValidUUID(uuid.toString())){
            Product product = service.getProduct(uuid);
            System.out.println(product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteProduct(@RequestHeader("ProductID") UUID uuid){
        if(Tools.isValidUUID(uuid.toString())) {
            return new ResponseEntity<>(service.deleteProductByUUID(uuid));
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
