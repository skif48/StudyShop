package com.shop.service.shop;

import com.shop.domain.entity.Attribute;
import com.shop.domain.entity.AttributeValue;
import com.shop.domain.entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vladyslav Usenko on 18.08.2016.
 */
public class ProductInfo {
    private Product product;
    private Map<Attribute, AttributeValue> attributeValueMap = new HashMap<>();

    public ProductInfo(){

    }

    public ProductInfo(Product product, Map<Attribute, AttributeValue> attributeValueMap) {
        this.product = product;
        this.attributeValueMap = attributeValueMap;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Map<Attribute, AttributeValue> getAttributeValueMap() {
        return attributeValueMap;
    }

    public void setAttributeValueMap(Map<Attribute, AttributeValue> attributeValueMap) {
        this.attributeValueMap = attributeValueMap;
    }

    public void manageAttributes(List<Object[]> values, Product product){
        for (Object[] attrVal : values) {
            Attribute attribute = new Attribute(attrVal[0].toString());
            AttributeValue attributeValue = new AttributeValue(product, attribute, attrVal[1].toString());
            attributeValueMap.put(attribute, attributeValue);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductInfo that = (ProductInfo) o;

        if (!product.equals(that.product)) return false;
        return attributeValueMap.equals(that.attributeValueMap);

    }

    @Override
    public int hashCode() {
        int result = product.hashCode();
        result = 31 * result + attributeValueMap.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "product=" + product +
                ", attributeValueMap=" + attributeValueMap +
                '}';
    }
}
