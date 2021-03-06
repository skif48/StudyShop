package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Vladyslav Usenko on 17.08.2016.
 */
@Entity
@Table(name = "attributes")
public class Attribute {
    private long attributeID;
    private String name;
    @JsonIgnore
    private Set<AttributeValue> attributeValueSet;

    public Attribute() {
    }

    public Attribute(String name, Set<AttributeValue> attributeValueSet) {
        this.name = name;
        this.attributeValueSet = attributeValueSet;
    }

    @Id
    @NotNull
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "attribute_id", length = 6, nullable = false)
    public long getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(long attributeID) {
        this.attributeID = attributeID;
    }

    @NotNull
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "attributeValueID", cascade = CascadeType.ALL)
    public Set<AttributeValue> getAttributeValueSet() {
        return attributeValueSet;
    }

    public void setAttributeValueSet(Set<AttributeValue> attributeValueSet) {
        this.attributeValueSet = attributeValueSet;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "attributeValueSet=" + attributeValueSet +
                ", name='" + name + '\'' +
                '}';
    }
}
