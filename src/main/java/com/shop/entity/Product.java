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
public class Product {

    private long ID;

    private UUID uuid;

    private Type type;

    private String label;

    public Product() {
    }

    public Product(UUID uuid, Type type, String label) {
        this.uuid = uuid;
        this.type = type;
        this.label = label;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
    public long getID() {
        return ID;
    }

    @Column(name = "uuid")
    @NotNull
    public UUID getUuid() {
        return uuid;
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

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUuid(UUID uuid) {
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
