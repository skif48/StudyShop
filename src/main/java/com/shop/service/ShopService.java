package com.shop.service;

import com.shop.entity.Attribute;
import com.shop.entity.AttributeValue;
import com.shop.entity.Characteristic;
import com.shop.entity.Product;
import com.shop.repository.AttributeRepository;
import com.shop.repository.AttributeValueRepository;
import com.shop.repository.CharacteristicRepository;
import com.shop.repository.ProductRepository;
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
    private CharacteristicRepository characteristicRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeValueRepository attributeValueRepository;

    public ShopService() {
    }

    public void putProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProduct(UUID uuid) {
        Product product = new Product();
        try {
            System.out.println(uuid.toString());
            product = productRepository.findByUuid(uuid.toString());
            if (product == null) {
                product = new Product();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            return product;
        }

        return product;
    }

    public ProductInfo getProductFullInfo(UUID uuid){
        ProductInfo productInfo = new ProductInfo();
        try{
            List<Object[]> info = productRepository.info(uuid.toString());
            productInfo = new ProductInfo();
            productInfo.manageAttributes(info);
            productInfo.setProduct(productRepository.findByUuid(uuid.toString()));
        } catch (Exception exc){
            exc.printStackTrace();
            return productInfo;
        }

        return productInfo;
    }

    public Collection<Product> getAllProducts() {
        Iterable<Product> products = null;
        try {
            products = productRepository.findAll();
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return (Collection<Product>) products;
    }

    public void deleteProductByUUID(UUID uuid) {
        productRepository.deleteByUuid(uuid.toString());
    }

    public void deleteAllProducts(){
        productRepository.deleteAll();
    }

    public void addCharacteristicsToProduct(Characteristic characteristic){
        characteristicRepository.save(characteristic);
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
}