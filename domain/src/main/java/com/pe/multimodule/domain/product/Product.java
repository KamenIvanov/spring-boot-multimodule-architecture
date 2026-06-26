package com.pe.multimodule.domain.product;

import com.pe.multimodule.domain.AbstractNamedDomain;

import java.math.BigDecimal;
import java.util.UUID;

public class Product extends AbstractNamedDomain<UUID> {

    private String sku;
    private BigDecimal price;

    public Product() {
        // POJO
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
