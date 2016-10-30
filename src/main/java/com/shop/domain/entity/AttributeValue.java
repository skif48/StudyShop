package com.shop.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Product product;
    private Attribute attribute;
    private Integer value;
    private EnumerableAttributeValue enumerableAttributeValue;

    public AttributeValue() {
    }

    public AttributeValue(Integer value) {
        this.value = value;
    }

    public AttributeValue(Product product, Attribute attribute, Integer value) {
        this.product = product;
        this.attribute = attribute;
        this.value = value;
    }

    public AttributeValue(Product product, Attribute attribute, EnumerableAttributeValue enumerableAttributeValue) {
        this.product = product;
        this.attribute = attribute;
        this.enumerableAttributeValue = enumerableAttributeValue;
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

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_foreign")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attribute_id_foreign")
    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Column(name = "value", nullable = true)
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "enumerable_attribute_value")
    public EnumerableAttributeValue getEnumerableAttributeValue() {
        return enumerableAttributeValue;
    }

    public void setEnumerableAttributeValue(EnumerableAttributeValue enumerableAttributeValue) {
        this.enumerableAttributeValue = enumerableAttributeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeValue that = (AttributeValue) o;

        if (attributeValueID != that.attributeValueID) return false;
        if (!product.equals(that.product)) return false;
        if (!attribute.equals(that.attribute)) return false;
        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = (int) (attributeValueID ^ (attributeValueID >>> 32));
        result = 31 * result + product.hashCode();
        result = 31 * result + attribute.hashCode();
        if(value != null)
            result = 31 * result + value.hashCode();
        return result;
    }
}
