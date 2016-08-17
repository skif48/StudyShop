package com.shop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Vladyslav Usenko on 16.08.2016.
 */
@Entity
@Table(name = "characteristics")
public class Characteristic {
    private long ID;
    private String uuid;
    private int year;
    private int RAM;
    private String processor;
    private int coresCount;
    private int widthPixels;
    private int lengthPixels;
    private boolean hasOS;
    private boolean isUltrabook;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "characteristic_id", length = 6, nullable = false)
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    @NotNull
    @Column(name = "year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column(name = "RAM")
    public int getRAM() {
        return RAM;
    }

    public void setRAM(int RAM) {
        this.RAM = RAM;
    }

    @NotNull
    @Column(name = "processor")
    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    @NotNull
    @Column(name = "cores_count")
    public int getCoresCount() {
        return coresCount;
    }

    public void setCoresCount(int coresCount) {
        this.coresCount = coresCount;
    }

    @NotNull
    @Column(name = "width_pixels")
    public int getWidthPixels() {
        return widthPixels;
    }

    public void setWidthPixels(int widthPixels) {
        this.widthPixels = widthPixels;
    }

    @NotNull
    @Column(name = "length_pixels")
    public int getLengthPixels() {
        return lengthPixels;
    }

    public void setLengthPixels(int lengthPixels) {
        this.lengthPixels = lengthPixels;
    }

    @Column(name = "has_OS")
    public boolean isHasOS() {
        return hasOS;
    }

    public void setHasOS(boolean hasOS) {
        this.hasOS = hasOS;
    }

    @Column(name = "is_ultrabook")
    public boolean isUltrabook() {
        return isUltrabook;
    }

    public void setUltrabook(boolean ultrabook) {
        this.isUltrabook = ultrabook;
    }

    @NotNull
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
