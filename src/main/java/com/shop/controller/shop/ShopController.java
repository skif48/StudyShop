package com.shop.controller.shop;

import com.shop.domain.entity.*;
import com.shop.domain.user.User;
import com.shop.error.ServiceException;
import com.shop.service.dataUtils.ManufacturerRequest;
import com.shop.service.dataUtils.ProductRequest;
import com.shop.service.shop.ProductInfo;
import com.shop.service.user.UserService;
import com.shop.utils.Tools;
import com.shop.service.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.*;
import java.util.List;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@Controller
public class ShopController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView welcome(@ModelAttribute(name = "allProducts") ModelMap map, Principal principal) throws ServiceException {
        List<ProductInfo> productInfos = shopService.getAllProductsInfo();
        List<Product> products = new ArrayList<>();
        for(ProductInfo productInfo : productInfos){
            products.add(productInfo.getProduct());
        }
        map.addAttribute("allProducts", products);
        if(principal != null) {
            String userName = principal.getName();
            User user = userService.getUserByEmail(userName).get();
            map.addAttribute("user", user);
        }
        return new ModelAndView("home", map);
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public ModelAndView putProduct(Principal principal, @ModelAttribute("userInfo") ModelMap map){
        if(principal != null){
            String username = principal.getName();
            User user = userService.getUserByEmail(username).get();
            map.addAttribute("user", user);
        }

        map.addAttribute("productTypes", shopService.getAllProductTypes());
        return new ModelAndView("newProduct", map);
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity putProduct(@RequestBody ProductRequest productRequest){
        try{
            UUID uuid = shopService.putProduct(productRequest);
            return new ResponseEntity<>(uuid, HttpStatus.OK);
        } catch (Exception exc){
            exc.printStackTrace();
            return new ResponseEntity<>(exc, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getProduct(@RequestParam(value = "uuid") UUID uuid,
                                   @ModelAttribute(name = "productInfo") ModelMap map,
                                   Principal principal) throws ServiceException {
        if(Tools.isValidUUID(uuid.toString())){
            ProductInfo productInfo = shopService.getProductFullInfo(uuid);
            map.addAttribute("product", productInfo.getProduct());
            map.addAttribute("attributes", new ArrayList<>(productInfo.getAttributeValueMap().keySet()));
            map.addAttribute("attributeValues", new ArrayList<>(productInfo.getAttributeValueMap().values()));
            if(principal != null) {
                String userName = principal.getName();
                User user = userService.getUserByEmail(userName).get();
                map.addAttribute("user", user);
            }
            return new ModelAndView("product", map);
        } else {
            return new ModelAndView("error");
        }
    }

    @RequestMapping(value ="/addAttributesToProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addAttributesToProduct(@RequestParam(value = "uuid") UUID uuid, @RequestBody Map<Attribute, AttributeValue> attributes){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllProducts(){
        Collection<Product> products = shopService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteAllProducts(){
        shopService.deleteAllProducts();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteProduct(@RequestParam(value = "uuid") UUID uuid){
        if(Tools.isValidUUID(uuid.toString())) {
            shopService.deleteProductByUUID(uuid);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>("requested uuid is not valid: " + uuid.toString(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/attribute", method = RequestMethod.POST)
    public ResponseEntity putAttribute(@RequestBody Attribute attribute){
        shopService.addAttribute(attribute);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/attribute/{name}", method = RequestMethod.POST)
    public ResponseEntity putAttributeValue(@PathVariable(value = "name") String attributeName,
                                            @RequestParam(value = "uuid") UUID uuid,
                                            @RequestBody AttributeValue attributeValue){
        Attribute attribute = shopService.getAttributeByName(attributeName);
        shopService.addAttributeValue(attributeValue, uuid, attribute);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/type/{typeName}", method = RequestMethod.GET)
    public ResponseEntity getProductsOfType(@PathVariable(value = "typeName") ProductType type){
        ProductType productType = shopService.getTypeByName(type.getName());
        List<Product> products = shopService.getProductsOfType(productType);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public ResponseEntity addNewType(@RequestBody ProductType type){
        shopService.addType(type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/matchTypeAndAttribute", method = RequestMethod.PUT)
    public ResponseEntity matchTypeAndAttribute(@RequestParam(value = "typeName") String type,
                                                @RequestHeader(value = "attributeName") String attribute){
        ProductType productType = shopService.getTypeByName(type);
        Attribute attributeByName = shopService.getAttributeByName(attribute);
        shopService.matchTypeAndAttribute(productType, attributeByName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/attributesOfType", method = RequestMethod.GET)
    public ResponseEntity getAttributesOfType(@RequestParam(value = "typeName") String typeName){
        List<Attribute> attributes = new ArrayList<>(shopService.getAttributesOfType(typeName));
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @RequestMapping(value = "/addImageByURL", method = RequestMethod.POST)
    public ResponseEntity addImageToProductByURL(@RequestParam(value = "uuid") String uuid, @RequestHeader(value = "imageURL") URL url) throws IOException {
        shopService.setImageToProductByURL(url, UUID.fromString(uuid));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/addImageByFile", method = RequestMethod.POST)
    public ResponseEntity addImageByFile(@RequestParam(value = "imageUploadad") MultipartFile uploadFile) throws IOException {
        long id = shopService.addImageByFile(uploadFile, null);
        return new ResponseEntity<>(Long.toString(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/getImage/{productUUID}", method = RequestMethod.GET)
    public ResponseEntity getImage(@PathVariable(value = "productUUID") String uuid, @RequestParam(value = "imgNumber") int img){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(shopService.getImageForSpecifiedProduct(UUID.fromString(uuid), img), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/changeProduct", method = RequestMethod.PUT)
    public ResponseEntity changeProduct(@RequestParam(value = "uuid") String uuid, @RequestBody Product product){
        shopService.getProductByUUID(UUID.fromString(uuid)).changeProduct(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value ="/manufacturers", method = RequestMethod.GET)
    public ResponseEntity getManufacturers(){
        ArrayList<Manufacturer> manufacturers = new ArrayList<>(shopService.getAllManufacturers());
        return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }

    @RequestMapping(value ="/advancedSearch", method = RequestMethod.GET)
    public ModelAndView advancedSearchPage(@ModelAttribute ModelMap map, Principal principal){
        if(principal != null) {
            String userName = principal.getName();
            User user = userService.getUserByEmail(userName).get();
            map.addAttribute("user", user);
        }

        List<ProductType> productTypes = shopService.getAllProductTypes();
        map.addAttribute("productTypes", productTypes);
        return new ModelAndView("searchPage", map);
    }

    @RequestMapping(value ="/addManufacturer", method = RequestMethod.POST)
    public ResponseEntity addManufacturer(@RequestBody ManufacturerRequest manufacturerRequest){
        shopService.addManufacturer(manufacturerRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getManufacturersOfType", method = RequestMethod.GET)
    public ResponseEntity getManufacturersOfType(@RequestParam(value = "type") String type) {
        List<Manufacturer> manufacturers = shopService.getManufacturersOfType(type);
        return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }
}
