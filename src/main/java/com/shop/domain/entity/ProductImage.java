package com.shop.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Vladyslav Usenko on 25.08.2016.
 */
@Entity
@Table(name = "images")
public class ProductImage {
    @Id
    @NotNull
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "image_id", length = 6, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productID")
    private Product product;

    @Lob
    @Column(name="byteImage", nullable=false, length = 1000000000)
    private byte[] imageByte;

    public ProductImage(Product product, byte[] imageByte) {
        this.product = product;
        this.imageByte = imageByte;
    }

    public ProductImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public byte[] getImage() {
        return imageByte;
    }

    public void setImage(byte[] image) {
        this.imageByte = image;
    }
}
