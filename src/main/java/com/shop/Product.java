package com.shop;

import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
public class Product {

    public enum Type {
        PHONE, TV, PC, LAPTOP
    }

    private UUID uuid;
    private Type type;
    private String name;

    public Product() {
    }

    public Product(UUID uuid, Type type, String name) {
        this.uuid = uuid;
        this.type = type;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
