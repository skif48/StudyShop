package com.shop.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Vladyslav Usenko on 18.08.2016.
 */
@Entity
@Table(name = "productType")
public class ProductType {
    private long typeID;
    private String name;
    private Set<Attribute> attributes;
    @JsonIgnore
    private Set<Manufacturer> manufacturers;

    public ProductType() {
        attributes = new HashSet<>();
    }

    public ProductType(String name) {
        this.name = name;
        attributes = new HashSet<>();
    }

    @Id
    @NotNull
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "productType_id", length = 6, nullable = false)
    public long getTypeID() {
        return typeID;
    }

    public void setTypeID(long typeID) {
        this.typeID = typeID;
    }

    @Column(name = "name")
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="productType_attribute", joinColumns=@JoinColumn(name="productType_id"), inverseJoinColumns=@JoinColumn(name="attribute_id"))
    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "typeSet")
    public Set<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(Set<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }
}
