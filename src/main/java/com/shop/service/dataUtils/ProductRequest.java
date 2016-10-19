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
    private String price;
    private String label;
    private Manufacturer manufacturer;
    private Map<Attribute, AttributeValue> attributes;
    private Map<Attribute, String> enumerableAttributes;
    private Long imageID;

    public ProductRequest() {
    }

    public ProductRequest(ProductType type, String price, String label, Manufacturer manufacturer, Map<Attribute, AttributeValue> attributes, Long imageID, Map<Attribute, String> enumerableAttributes) {
        this.type = type;
        this.price = price;
        this.label = label;
        this.manufacturer = manufacturer;
        this.attributes = attributes;
        this.imageID = imageID;
        this.enumerableAttributes = enumerableAttributes;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
    }

    public Map<Attribute, String> getEnumerableAttributes() {
        return enumerableAttributes;
    }

    public void setEnumerableAttributes(Map<Attribute, String> enumerableAttributes) {
        this.enumerableAttributes = enumerableAttributes;
    }
}
