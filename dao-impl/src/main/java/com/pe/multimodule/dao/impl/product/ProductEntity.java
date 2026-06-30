package com.pe.multimodule.dao.impl.product;

import com.pe.multimodule.dao.impl.AbstractNamedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTS")
public class ProductEntity extends AbstractNamedEntity {

    @Column(name = "sku", length = 256, nullable = false, unique = true)
    @NotNull
    private String sku;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    @NotNull
    @Range(min = 0)
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
