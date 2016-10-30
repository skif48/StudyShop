package com.shop.service.dataUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Vladyslav Usenko on 19.10.2016.
 */
public class SearchRequest {
    private Integer priceFrom;
    private Integer priceTo;
    private List<String> manufacturers;
    private Map<String, List<Integer>> attributesFromTo;
    private Map<String, List<String>> checkedEnumerableAttributes;

    public SearchRequest() {
    }

    public SearchRequest(Integer priceFrom, Integer priceTo, List<String> manufacturers, Map<String, List<Integer>> attributesFromTo, Map<String, List<String>> checkedEnumerableAttributes) {
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.manufacturers = manufacturers;
        this.attributesFromTo = attributesFromTo;
        this.checkedEnumerableAttributes = checkedEnumerableAttributes;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
    }

    public List<String> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<String> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public Map<String, List<Integer>> getAttributesFromTo() {
        return attributesFromTo;
    }

    public void setAttributesFromTo(Map<String, List<Integer>> attributesFromTo) {
        this.attributesFromTo = attributesFromTo;
    }

    public Map<String, List<String>> getCheckedEnumerableAttributes() {
        return checkedEnumerableAttributes;
    }

    public void setCheckedEnumerableAttributes(Map<String, List<String>> checkedEnumerableAttributes) {
        this.checkedEnumerableAttributes = checkedEnumerableAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchRequest that = (SearchRequest) o;

        if (priceFrom != null ? !priceFrom.equals(that.priceFrom) : that.priceFrom != null) return false;
        if (priceTo != null ? !priceTo.equals(that.priceTo) : that.priceTo != null) return false;
        if (manufacturers != null ? !manufacturers.equals(that.manufacturers) : that.manufacturers != null)
            return false;
        if (attributesFromTo != null ? !attributesFromTo.equals(that.attributesFromTo) : that.attributesFromTo != null)
            return false;
        return checkedEnumerableAttributes != null ? checkedEnumerableAttributes.equals(that.checkedEnumerableAttributes) : that.checkedEnumerableAttributes == null;

    }

    @Override
    public int hashCode() {
        int result = priceFrom != null ? priceFrom.hashCode() : 0;
        result = 31 * result + (priceTo != null ? priceTo.hashCode() : 0);
        result = 31 * result + (manufacturers != null ? manufacturers.hashCode() : 0);
        result = 31 * result + (attributesFromTo != null ? attributesFromTo.hashCode() : 0);
        result = 31 * result + (checkedEnumerableAttributes != null ? checkedEnumerableAttributes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "priceFrom=" + priceFrom +
                ", priceTo=" + priceTo +
                ", manufacturers=" + manufacturers +
                ", attributesFromTo=" + attributesFromTo +
                ", checkedEnumerableAttributes=" + checkedEnumerableAttributes +
                '}';
    }
}
