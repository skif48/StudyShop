package com.shop.service.dataUtils;

import com.shop.domain.entity.Attribute;
import com.shop.domain.entity.AttributeValue;
import com.shop.domain.entity.Manufacturer;
import com.shop.domain.entity.ProductType;

import java.util.Map;

/**
 * Created by Vladyslav Usenko on 28.09.2016.
 */
public class ProductRequest {
    private ProductType type;
    private String label;
    private Manufacturer manufacturer;
    private Map<Attribute, AttributeValue> attributes;

    public ProductRequest() {
    }

    public ProductRequest(ProductType type, String label, Map<Attribute, AttributeValue> attributes, Manufacturer manufacturer) {
        this.type = type;
        this.label = label;
        this.attributes = attributes;
        this.manufacturer = manufacturer;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<Attribute, AttributeValue> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Attribute, AttributeValue> attributes) {
        this.attributes = attributes;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
