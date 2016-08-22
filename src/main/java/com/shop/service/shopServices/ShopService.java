package com.shop.service.shopServices;

import com.shop.domain.entity.*;
import com.shop.domain.user.Role;
import com.shop.domain.user.User;
import com.shop.domain.user.UserCreateForm;
import com.shop.repository.products.AttributeRepository;
import com.shop.repository.products.AttributeValueRepository;
import com.shop.repository.products.ProductRepository;
import com.shop.repository.products.ProductTypeRepository;
import com.shop.repository.users.UserRepository;
import com.shop.service.userServices.CurrentUser;
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

    public ShopService() {
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public AttributeRepository getAttributeRepository() {
        return attributeRepository;
    }

    public void setAttributeRepository(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public AttributeValueRepository getAttributeValueRepository() {
        return attributeValueRepository;
    }

    public void setAttributeValueRepository(AttributeValueRepository attributeValueRepository) {
        this.attributeValueRepository = attributeValueRepository;
    }

    public ProductTypeRepository getProductTypeRepository() {
        return productTypeRepository;
    }

    public void setProductTypeRepository(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public void putProduct(Product product, String type) {
        ProductType productType = productTypeRepository.findByTypeName(type);
        product.setType(productType);
        productRepository.save(product);
    }

    public Product getProductByUUID(UUID uuid){
        return productRepository.findByUuid(uuid.toString());
    }

    // TODO: 22.08.2016 unit test
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

    // TODO: 22.08.2016 unit test
    public void deleteProductByUUID(UUID uuid) {
        productRepository.deleteByUuid(uuid.toString());
    }

    // TODO: 22.08.2016 unit test
    public void deleteAllProducts(){
        productRepository.deleteAll();
    }

    // TODO: 22.08.2016 unit test
    public void addAttribute(Attribute attribute){
        attributeRepository.save(attribute);
    }

    // TODO: 22.08.2016 unit test
    public Attribute getAttributeByName(String name){
        return attributeRepository.findByName(name);
    }

    //TODO 22.08.2016 unit test
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

    //TODO 22.08.2016 unit test
    public ProductType getTypeByName(String name){
        return productTypeRepository.findByTypeName(name);
    }

    //TODO 22.08.2016 unit test
    public List<Product> getProductsOfType(ProductType type){
        return productRepository.findByType(type);
    }

    //TODO 22.08.2016 unit test
    public void addType(ProductType type){
        productTypeRepository.save(type);
    }

    //TODO 22.08.2016 unit test
    public void matchTypeAndAttribute(ProductType productType, Attribute attribute){
        Set<Attribute> set = new HashSet<>();
        set.addAll(productType.getAttributes());
        set.add(attribute);
        productType.setAttributes(set);
        productTypeRepository.save(productType);
    }

    //TODO 22.08.2016 unit test
    public Set<Attribute> getAttributesOfType(String productTypeName){
        ProductType productType = productTypeRepository.findByTypeName(productTypeName);
        return productType.getAttributes();
    }
}