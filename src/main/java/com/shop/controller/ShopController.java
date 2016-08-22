package com.shop.controller;

import com.shop.entity.*;
import com.shop.service.ProductInfo;
import com.shop.service.UserLogin;
import com.shop.utils.Tools;
import com.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@Controller
public class ShopController {
    @Autowired
    private ShopService service;
    @Autowired
    private UserCreateFormValidator userCreateFormValidator;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView welcome(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody UserLogin userLogin){
        System.out.println(userLogin.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity putProduct(@RequestBody Product product, @RequestHeader(name = "type") String type){
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
    public ResponseEntity putAttributeValue(@PathVariable(value = "name") String attributeName,
                                            @RequestParam(value = "uuid") UUID uuid,
                                            @RequestBody AttributeValue attributeValue){
        service.addAttributeValue(attributeValue, uuid, attributeName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/type/{typeName}", method = RequestMethod.GET)
    public ResponseEntity getProductsOfType(@PathVariable(value = "typeName") String typeName){
        ProductType productType = service.getTypeByName(typeName);
        List<Product> products = service.getProductsOfType(productType);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public ResponseEntity addNewType(@RequestBody ProductType type){
        service.addType(type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/matchTypeAndAttribute", method = RequestMethod.PUT)
    public ResponseEntity matchTypeAndAttribute(@RequestParam(value = "typeName") String typeName, @RequestHeader(value = "attributeName") String attributeName){
        ProductType productType = service.getTypeByName(typeName);
        Attribute attribute = service.getAttributeByName(attributeName);
        service.matchTypeAndAttribute(productType, attribute);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/attributesOfType", method = RequestMethod.GET)
    public ResponseEntity getAttributesOfType(@RequestParam(value = "typeName") String typeName){
        Set<Attribute> attributes = service.getAttributesOfType(typeName);
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @RequestMapping("/users")
    public ModelAndView getUsersPage() {
        return new ModelAndView("users", "users", service.getAllUsers());
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }

    @PreAuthorize("@shopService.canAccessUser(principal, #id)")
    @RequestMapping("/user/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
        return new ModelAndView("user", "user", service.getUserById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", id))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
        return new ModelAndView("user_create", "form", new UserCreateForm());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("form") UserCreateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_create";
        }
        try {
            service.create(form);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("email.exists", "Email already exists");
            return "user_create";
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("index", "error", error);
    }
}
