package com.pe.multimodule.bl.service.cache;

public enum CacheNames {

    PRODUCTS("products");

    private final String name;

    CacheNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
