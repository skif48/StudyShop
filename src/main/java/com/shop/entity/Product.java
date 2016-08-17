package com.shop.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */

@Entity
@Table(name = "products")
/*@NamedQuery(name = "productsAttributesByUUID",
        query = "select p.uuid, attr.name, attrval.value " +
                "from products p, attributes attr, attributes_value attrval " +
                "where attrval.product_id = p.product_id and attrval.attribute_id = attr.attribute_id and p.uuid = :uuid") */
public class Product {

    private long ID;

    private String uuid;

    private Type type;

    private String label;

    private Characteristic characteristic;

    public Product() {
    }

    public Product(UUID uuid, Type type, String label, Characteristic characteristic) {
        this.uuid = uuid.toString();
        this.type = type;
        this.label = label;
        this.characteristic = characteristic;
    }

    @Id
    @NotNull
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "product_id", length = 6, nullable = false)
    public long getID() {
        return ID;
    }

    @Column(name = "uuid")
    @NotNull
    public String getUuid() {
        return uuid.toString();
    }

    @Column(name = "type")
    @NotNull
    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    @Column(name = "label")
    @NotNull
    public String getLabel() {
        return label;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "characteristic_foreign")
    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setType(Type type) {
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
