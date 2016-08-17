package com.shop.controller;

import com.shop.entity.Attribute;
import com.shop.entity.AttributeValue;
import com.shop.entity.Characteristic;
import com.shop.utils.Tools;
import com.shop.entity.Product;
import com.shop.service.ShopService;
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
            product = (Product) Tools.parseProductFromJSON(productJSON);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            service.putProduct(product);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getProduct(@RequestHeader("ProductID") UUID uuid){
        if(Tools.isValidUUID(uuid.toString())){
            try {
                Product product = service.getProduct(uuid);
                return new ResponseEntity<>(product, HttpStatus.OK);
            } catch (Exception exc){
                return new ResponseEntity<>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteAllProducts(){
        try {
            service.deleteAllProducts();
            return new ResponseEntity<>("all products successfully deleted from DB",HttpStatus.OK);
        } catch (Exception exc){
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteProduct(@RequestHeader("ProductID") UUID uuid){
        if(Tools.isValidUUID(uuid.toString())) {
            try {
                service.deleteProductByUUID(uuid);
                return new ResponseEntity<>("successfully deleted product with uuid: " + uuid.toString(), HttpStatus.OK);
            } catch (Exception exc){
                exc.printStackTrace();
                return new ResponseEntity<>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else
            return new ResponseEntity<>("requested uuid is not valid: " + uuid.toString(), HttpStatus.BAD_REQUEST);
    }

    @Deprecated
    @RequestMapping(value = "/characteristic", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity putCharacteristic(@RequestBody String characteristicJSON){
        try{
            Characteristic characteristic = (Characteristic) Tools.parseCharacteristicFromJSON(characteristicJSON);
            service.addCharacteristicsToProduct(characteristic);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exc){
            return new ResponseEntity<>(exc.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/attribute", method = RequestMethod.POST)
    public ResponseEntity putAttribute(@RequestBody String attributeJSON){
        try{
            Attribute attribute = (Attribute) Tools.parseAttributeFromJSON(attributeJSON);
            service.addAttribute(attribute);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exc){
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/attribute/{name}", method = RequestMethod.POST)
    public ResponseEntity putAttributeValue(@PathVariable(value = "name") String attributeName, @RequestParam(value = "uuid") UUID uuid, @RequestBody String valueJSON){
        try{
            AttributeValue attributeValue = (AttributeValue) Tools.parseAttributeValueFromJSON(valueJSON);
            service.addAttributeValue(attributeValue, uuid, attributeName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exc){
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
