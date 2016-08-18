package com.shop.service;

import com.shop.entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vladyslav Usenko on 18.08.2016.
 */
public class ProductInfo {
    private Product product;
    private Map<String, String> attributeValueMap = new HashMap<>();

    public ProductInfo(){

    }

    public ProductInfo(Product product, Map<String, String> attributeValueMap) {
        this.product = product;
        this.attributeValueMap = attributeValueMap;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Map<String, String> getAttributeValueMap() {
        return attributeValueMap;
    }

    public void setAttributeValueMap(Map<String, String> attributeValueMap) {
        this.attributeValueMap = attributeValueMap;
    }

    public void manageAttributes(List<Object[]> values){
        for (Object[] attrVal : values) {
            attributeValueMap.put(attrVal[0].toString(), attrVal[1].toString());
        }
    }
}
