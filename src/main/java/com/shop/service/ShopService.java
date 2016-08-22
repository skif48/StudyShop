package com.shop.service;

import com.shop.entity.*;
import com.shop.error.ErrorCode;
import com.shop.error.ServiceException;
import com.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@Service
public class ShopService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeValueRepository attributeValueRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private UserRepository userRepository;

    public ShopService() {
    }

    public void putProduct(Product product, String type) {
        ProductType productType = productTypeRepository.findByTypeName(type);
        product.setType(productType);
        productRepository.save(product);
    }

    public ProductInfo getProductFullInfo(UUID uuid){
        List<Object[]> info = productRepository.getFullInfoByUuid(uuid.toString());
        ProductInfo productInfo = new ProductInfo();
        productInfo.manageAttributes(info);
        Product product = productRepository.findByUuid(uuid.toString());
        productInfo.setProduct(product);
        return productInfo;
    }

    public Collection<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProductByUUID(UUID uuid) {
        productRepository.deleteByUuid(uuid.toString());
    }

    public void deleteAllProducts(){
        productRepository.deleteAll();
    }

    public void addAttribute(Attribute attribute){
        attributeRepository.save(attribute);
    }

    public Attribute getAttributeByName(String name){
        return attributeRepository.findByName(name);
    }

    public void addAttributeValue(AttributeValue attributeValue, UUID uuid, String attributeName){
        attributeValue = setUpAttributeValue(attributeValue, uuid, attributeName);
        attributeValueRepository.save(attributeValue);
    }

    private AttributeValue setUpAttributeValue(AttributeValue attributeValue, UUID uuid, String attributeName){
        Product product = productRepository.findByUuid(uuid.toString());
        attributeValue.setProduct(product);
        Attribute attribute = getAttributeByName(attributeName);
        attributeValue.setAttribute(attribute);

        return attributeValue;
    }

    public ProductType getTypeByName(String name){
        return productTypeRepository.findByTypeName(name);
    }

    public List<Product> getProductsOfType(ProductType type){
        return productRepository.findByType(type);
    }

    public void addType(ProductType type){
        productTypeRepository.save(type);
    }

    public void matchTypeAndAttribute(ProductType productType, Attribute attribute){
        Set<Attribute> set = new HashSet<>();
        set.addAll(productType.getAttributes());
        set.add(attribute);
        productType.setAttributes(set);
        productTypeRepository.save(productType);
    }

    public Set<Attribute> getAttributesOfType(String productTypeName){
        ProductType productType = productTypeRepository.findByTypeName(productTypeName);
        return productType.getAttributes();
    }

    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public Collection<User> getAllUsers() {
        return userRepository.findAll(new Sort("email"));
    }

    public User create(UserCreateForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(form.getRole());
        return userRepository.save(user);
    }

    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        return currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId));
    }
}