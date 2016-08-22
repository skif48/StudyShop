package com.shop.domain.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */

@Entity
@Table(name = "products")
public class Product {
    private long productID;
    private String uuid;
    private ProductType type;
    private String label;

    public Product() {
    }

    public Product(UUID uuid, ProductType type, String label) {
        this.uuid = uuid.toString();
        this.type = type;
        this.label = label;
    }

    @Id
    @NotNull
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "product_id", length = 6, nullable = false)
    public long getProductID() {
        return productID;
    }

    @Column(name = "uuid")
    @NotNull
    public String getUuid() {
        return uuid.toString();
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productType_id")
    public ProductType getType() {
        return type;
    }

    @Column(name = "label")
    @NotNull
    public String getLabel() {
        return label;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Product{" +
                " uuid=" + uuid +
                ", type=" + type +
                ", label='" + label + '\'' +
                '}';
    }
}
