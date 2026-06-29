package com.pe.multimodule.dao.impl.proxy;

import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.dao.api.product.ProductSearchQuery;
import com.pe.multimodule.domain.product.Product;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class ProductsDaoProxy extends SearchableCrudDaoProxy<UUID, Product, ProductSearchQuery, ProductDao> implements ProductDao {

    public ProductsDaoProxy(ProductDao proxied, EntityManager entityManager) {
        super(proxied, entityManager);
    }

    @Override
    public Product loadBySku(String sku) {
        return proxied.loadBySku(sku);
    }
}
