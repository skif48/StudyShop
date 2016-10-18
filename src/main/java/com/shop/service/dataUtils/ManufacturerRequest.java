package com.shop.service.dataUtils;

import com.shop.domain.entity.ProductType;

import java.util.List;

/**
 * Created by Vladyslav Usenko on 17.10.2016.
 */
public class ManufacturerRequest {
    private String name;
    private List<ProductType> types;

    public ManufacturerRequest() {
    }

    public ManufacturerRequest(String name, List<ProductType> types) {
        this.name = name;
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductType> getTypes() {
        return types;
    }

    public void setTypes(List<ProductType> types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ManufacturerRequest that = (ManufacturerRequest) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return types != null ? types.equals(that.types) : that.types == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (types != null ? types.hashCode() : 0);
        return result;
    }
}
