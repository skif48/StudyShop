package com.shop.controller;

import com.shop.entity.Attribute;
import com.shop.entity.AttributeValue;
import com.shop.entity.ProductType;
import com.shop.service.ProductInfo;
import com.shop.service.ServiceResponse;
import com.shop.service.UserLogin;
import com.shop.utils.Tools;
import com.shop.entity.Product;
import com.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@RestController
public class ShopController {
    @Autowired
    private ShopService service;

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity putProduct(@RequestBody Product product, @RequestHeader(name = "type") String type){
        ServiceResponse response = service.putProduct(product, type);
        if(response.getStatus() == ServiceResponse.ServiceStatus.SUCCESS){
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            return new ResponseEntity<>(response.getException(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getProduct(@RequestParam(value = "uuid") UUID uuid){
        if(Tools.isValidUUID(uuid.toString())){
            //Product product = service.getProduct(uuid);
            ServiceResponse response = service.getProductFullInfo(uuid);
            if(response.getStatus() == ServiceResponse.ServiceStatus.SUCCESS){
                ProductInfo productInfo = (ProductInfo) response.getData();
                return new ResponseEntity<>(productInfo, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(response.getException(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllProducts(){
        ServiceResponse response = service.getAllProducts();
        if(response.getStatus() == ServiceResponse.ServiceStatus.SUCCESS){
            Collection<Product> products = (Collection<Product>) response.getData();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(response.getException(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteAllProducts(){
        ServiceResponse response = service.deleteAllProducts();
        if(response.getStatus() == ServiceResponse.ServiceStatus.SUCCESS){
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            return new ResponseEntity<>(response.getException(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/product", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteProduct(@RequestParam(value = "uuid") UUID uuid){
        if(Tools.isValidUUID(uuid.toString())) {
            ServiceResponse response = service.deleteProductByUUID(uuid);
            if(response.getStatus() == ServiceResponse.ServiceStatus.SUCCESS){
                return new ResponseEntity<>(HttpStatus.OK);
            } else{
                return new ResponseEntity<>(response.getException(), HttpStatus.OK);
            }

        } else
            return new ResponseEntity<>("requested uuid is not valid: " + uuid.toString(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/attribute", method = RequestMethod.POST)
    public ResponseEntity putAttribute(@RequestBody Attribute attribute){
        ServiceResponse response = service.addAttribute(attribute);
        if(response.getStatus() == ServiceResponse.ServiceStatus.SUCCESS){
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            return new ResponseEntity<>(response.getException(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/attribute/{name}", method = RequestMethod.POST)
    public ResponseEntity putAttributeValue(@PathVariable(value = "name") String attributeName,
                                            @RequestParam(value = "uuid") UUID uuid,
                                            @RequestBody AttributeValue attributeValue){
        ServiceResponse response = service.addAttributeValue(attributeValue, uuid, attributeName);
        if(response.getStatus() == ServiceResponse.ServiceStatus.SUCCESS){
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            return new ResponseEntity<>(response.getException(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/type/{typeName}", method = RequestMethod.GET)
    public ResponseEntity getProductsOfType(@PathVariable(value = "typeName") String typeName){
        ServiceResponse response = service.getProductsOfType(new ProductType(typeName));
        if(response.getStatus() == ServiceResponse.ServiceStatus.SUCCESS){
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(response.getException(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public ResponseEntity addNewType(@RequestBody ProductType type){
        ServiceResponse response = service.addType(type);
        if(response.getStatus() == ServiceResponse.ServiceStatus.SUCCESS){
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            return new ResponseEntity<>(response.getException(), HttpStatus.OK);
        }
    }
}
