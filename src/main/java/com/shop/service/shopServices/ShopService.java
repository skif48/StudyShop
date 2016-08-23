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

    public void putProduct(Product product, ProductType type) {
        ProductType productType = productTypeRepository.findByTypeName(type.getName());
        product.setType(productType);
        productRepository.save(product);
    }

    public Product getProductByUUID(UUID uuid){
        return productRepository.findByUuid(uuid.toString());
    }

    public ProductInfo getProductFullInfo(UUID uuid){
        List<Object[]> info = productRepository.getFullInfoByUuid(uuid.toString());
        ProductInfo productInfo = new ProductInfo();
        Product product = productRepository.findByUuid(uuid.toString());
        productInfo.manageAttributes(info, product);
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

    //TODO 22.08.2016 unit test
    public void addAttributeValue(AttributeValue attributeValue, UUID uuid, Attribute attribute){
        attributeValue = setUpAttributeValue(attributeValue, uuid, attribute);
        attributeValueRepository.save(attributeValue);
    }

    private AttributeValue setUpAttributeValue(AttributeValue attributeValue, UUID uuid, Attribute attribute){
        String attributeName = attribute.getName();
        Product product = productRepository.findByUuid(uuid.toString());
        attributeValue.setProduct(product);
        Attribute attributeByName = getAttributeByName(attributeName);
        attributeValue.setAttribute(attributeByName);

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

    //TODO 22.08.2016 unit test
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
}