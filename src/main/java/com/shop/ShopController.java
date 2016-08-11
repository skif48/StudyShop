package com.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@RestController
public class ShopController {

    @Autowired
    private ShopRepository shopRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity putProduct(@RequestBody String productJSON){
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        try {
            product = mapper.readValue(productJSON, Product.class);
        } catch (Exception exc){
            System.out.println("wrong JSON");
        }

        if(product != null){
            this.shopRepository.addProduct(product.getUuid(), product);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getProduct(@RequestHeader("ProductID") UUID uuid){
        HttpHeaders headers = new HttpHeaders();
        headers.add("testHeader", "content");
        if(Tools.isValidUUID(uuid.toString())){
            return new ResponseEntity<>(this.shopRepository.getProductByUUID(uuid), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
