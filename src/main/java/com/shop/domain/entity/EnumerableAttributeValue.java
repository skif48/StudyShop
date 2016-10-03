package com.shop.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Vladyslav Usenko on 26.09.2016.
 */
@Entity
@Table(name="enumerable_attribute_value")
public class EnumerableAttributeValue {
    private long id;
    private String value;
    @JsonIgnore
    private Attribute attribute;
    @JsonIgnore
    private Set<AttributeValue> attributeValueSet;

    public EnumerableAttributeValue() {
    }

    public EnumerableAttributeValue(String value, Attribute attribute) {
        this.value = value;
        this.attribute = attribute;
    }

    public EnumerableAttributeValue(long id, String value, Attribute attribute) {
        this.id = id;
        this.value = value;
        this.attribute = attribute;
    }

    public EnumerableAttributeValue(String value, Attribute attribute, Set<AttributeValue> attributeValueSet) {
        this.value = value;
        this.attribute = attribute;
        this.attributeValueSet = attributeValueSet;
    }

    @Id
    @NotNull
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "enumerable_attribute_value_id", length = 6, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    @Column(name="value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="attribute_id")
    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @OneToMany(mappedBy = "enumerableAttributeValue", cascade = CascadeType.ALL)
    public Set<AttributeValue> getAttributeValueSet() {
        return attributeValueSet;
    }

    public void setAttributeValueSet(Set<AttributeValue> attributeValueSet) {
        this.attributeValueSet = attributeValueSet;
    }
}
