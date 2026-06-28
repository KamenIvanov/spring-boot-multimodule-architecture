package com.pe.multimodule.dao.impl.product;

import com.pe.multimodule.dao.impl.AbstractNamedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductEntity extends AbstractNamedEntity {

    @Column(name = "sku", length = 256, nullable = false, unique = true)
    private String sku;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    public ProductEntity() {
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
