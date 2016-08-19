package com.shop.service;

import com.shop.entity.Attribute;
import com.shop.entity.AttributeValue;
import com.shop.entity.Product;
import com.shop.entity.ProductType;
import com.shop.error.ErrorCode;
import com.shop.error.ServiceException;
import com.shop.repository.AttributeRepository;
import com.shop.repository.AttributeValueRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

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

    public void addAttributeValue(AttributeValue attributeValue, UUID uuid, String attributeName){
        attributeValue = setUpAttributeValue(attributeValue, uuid, attributeName);
        attributeValueRepository.save(attributeValue);
    }

    private AttributeValue setUpAttributeValue(AttributeValue attributeValue, UUID uuid, String attributeName){
        Product product = productRepository.findByUuid(uuid.toString());
        attributeValue.setProduct(product);
        Attribute attribute = attributeRepository.findByName(attributeName);
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
}