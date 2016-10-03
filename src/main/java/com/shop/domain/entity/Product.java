package com.shop.domain.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
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
    private Set<ProductImage> images;
    private Manufacturer manufacturer;

    public Product() {
    }

    public Product(ProductType type, String label, Manufacturer manufacturer){
        this.type         = type;
        this.label        = label;
        this.manufacturer = manufacturer;
    }

    public Product(UUID uuid, ProductType type, String label, Manufacturer manufacturer) {
        this.uuid = uuid.toString();
        this.type = type;
        this.label = label;
        this.manufacturer = manufacturer;
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "productType_id")
    public ProductType getType() {
        return type;
    }

    @Column(name = "label")
    @NotNull
    public String getLabel() {
        return label;
    }

    @OneToMany(mappedBy = "id",cascade = CascadeType.ALL)
    public Set<ProductImage> getImages() {
        return images;
    }

    public void setImages(Set<ProductImage> images) {
        this.images = images;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manufacturer_id")
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void changeProduct(Product product){
        setLabel(product.getLabel());
        setType(product.getType());
    }

    @Override
    public String toString() {
        return "Product{" +
                " uuid=" + uuid +
                ", type=" + type +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productID != product.productID) return false;
        if (!uuid.equals(product.uuid)) return false;
        if (!type.equals(product.type)) return false;
        return label.equals(product.label);

    }

    @Override
    public int hashCode() {
        int result = (int) (productID ^ (productID >>> 32));
        result = 31 * result + uuid.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + label.hashCode();
        return result;
    }
}
