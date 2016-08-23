package com.shop.controller.shopControllers;

import com.shop.domain.entity.*;
import com.shop.service.shopServices.ProductInfo;
import com.shop.utils.Tools;
import com.shop.service.shopServices.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@Controller
public class ShopController {
    @Autowired
    private ShopService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView welcome(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        return mav;
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity putProduct(@RequestBody Product product, @RequestHeader(name = "type") ProductType type){
        try{
            service.putProduct(product, type);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception exc){
            return new ResponseEntity<>(exc, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getProduct(@RequestParam(value = "uuid") UUID uuid){
        if(Tools.isValidUUID(uuid.toString())){
            ProductInfo productInfo = service.getProductFullInfo(uuid);
            return new ResponseEntity<>(productInfo, HttpStatus.OK);
        } else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllProducts(){
        Collection<Product> products = service.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteAllProducts(){
        service.deleteAllProducts();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteProduct(@RequestParam(value = "uuid") UUID uuid){
        if(Tools.isValidUUID(uuid.toString())) {
            service.deleteProductByUUID(uuid);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>("requested uuid is not valid: " + uuid.toString(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/attribute", method = RequestMethod.POST)
    public ResponseEntity putAttribute(@RequestBody Attribute attribute){
        service.addAttribute(attribute);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/attribute/{name}", method = RequestMethod.POST)
    public ResponseEntity putAttributeValue(@PathVariable(value = "name") Attribute attribute,
                                            @RequestParam(value = "uuid") UUID uuid,
                                            @RequestBody AttributeValue attributeValue){
        service.addAttributeValue(attributeValue, uuid, attribute);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/type/{typeName}", method = RequestMethod.GET)
    public ResponseEntity getProductsOfType(@PathVariable(value = "typeName") ProductType type){
        ProductType productType = service.getTypeByName(type.getName());
        List<Product> products = service.getProductsOfType(productType);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public ResponseEntity addNewType(@RequestBody ProductType type){
        service.addType(type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/matchTypeAndAttribute", method = RequestMethod.PUT)
    public ResponseEntity matchTypeAndAttribute(@RequestParam(value = "typeName") ProductType type, @RequestHeader(value = "attributeName") Attribute attribute){
        ProductType productType = service.getTypeByName(type.getName());
        Attribute attributeByName = service.getAttributeByName(attribute.getName());
        service.matchTypeAndAttribute(productType, attributeByName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/attributesOfType", method = RequestMethod.GET)
    public ResponseEntity getAttributesOfType(@RequestParam(value = "typeName") String typeName){
        Set<Attribute> attributes = service.getAttributesOfType(typeName);
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }
}
