package com.shop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Vladyslav Usenko on 17.08.2016.
 */
@Entity
@Table(name = "attribute_value")
public class AttributeValue {
    private long attributeValueID;
    private Product product;
    private Attribute attribute;
    private String value;

    public AttributeValue() {
    }

    public AttributeValue(Product product, Attribute attribute, String value) {
        this.product = product;
        this.attribute = attribute;
        this.value = value;
    }


    @Id
    @NotNull
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "attribute_value_id", length = 6, nullable = false)
    public long getAttributeValueID() {
        return attributeValueID;
    }

    public void setAttributeValueID(long attributeValueID) {
        this.attributeValueID = attributeValueID;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_foreign")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Column(name = "value")
    @NotNull
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
