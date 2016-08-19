package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Vladyslav Usenko on 18.08.2016.
 */
@Entity
@Table(name = "productType")
public class ProductType {
    private long typeID;
    private String name;
    @JsonIgnore
    private Set<Product> productSet;
    private Set<Attribute> attributes;

    public ProductType() {
    }

    public ProductType(String name) {
        this.name = name;
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

    @OneToMany(mappedBy = "productID", cascade = CascadeType.ALL)
    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="productType_attribute", joinColumns=@JoinColumn(name="productType_id"), inverseJoinColumns=@JoinColumn(name="attribute_id"))
    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}
