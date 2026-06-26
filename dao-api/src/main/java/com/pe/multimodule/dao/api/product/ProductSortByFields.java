package com.pe.multimodule.dao.api.product;

import com.pe.multimodule.dao.api.SortableEnum;

public enum ProductSortByFields implements SortableEnum {

    NAME("name"),
    CREATED_AT("createdAt");

    private final String value;

    ProductSortByFields(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
