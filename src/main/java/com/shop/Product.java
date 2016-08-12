package com.shop;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */

@Entity(name = "product")
@Table(name = "products")
public class Product {

    public enum Type {
        PHONE, TV, PC, LAPTOP
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;

    @NotNull
    private String uuid;
    @NotNull
    private Type type;
    @NotNull
    private String name;

    public Product() {
    }

    public Product(UUID uuid, Type type, String name) {
        this.uuid = uuid.toString();
        this.type = type;
        this.name = name;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
