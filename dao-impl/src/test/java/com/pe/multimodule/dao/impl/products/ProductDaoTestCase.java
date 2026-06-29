package com.pe.multimodule.dao.impl.products;

import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.dao.api.product.ProductSearchQuery;
import com.pe.multimodule.dao.impl.AbstractSearchableTestCase;
import com.pe.multimodule.domain.asserter.DeepEqualsAsserter;
import com.pe.multimodule.domain.asserter.products.ProductAsserter;
import com.pe.multimodule.domain.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

class ProductDaoTestCase extends AbstractSearchableTestCase<UUID, Product, ProductDao, ProductSearchQuery> {

    @Autowired
    private ProductDao productDao;

    @Override
    protected ProductSearchQuery createQuery() {
        return new ProductSearchQuery();
    }

    @Override
    protected ProductDao getDao() {
        return productDao;
    }

    @Override
    public Product createDomain() {
        final var product = new Product();
        product.setCreatedAt(Instant.now());
        product.setCreatedById(UUID.randomUUID());
        product.setUpdatedAt(Instant.now());
        product.setName("Simple product name");
        product.setSku(UUID.randomUUID().toString());
        product.setPrice(BigDecimal.TWO);
        return product;
    }

    @Override
    protected DeepEqualsAsserter<Product> getAsserter() {
        return ProductAsserter.instance;
    }

    @Test
    void testLoadBySku() {
        final var product = runSave();
        final var productBySku = getDao().loadBySku(product.getSku());
        Assertions.assertNotNull(productBySku);
        getAsserter().assertDeepEquals(product, productBySku);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testLoadBySkuNullOrEmptySku(String sku) {
        final var productBySku = getDao().loadBySku(sku);
        Assertions.assertNull(productBySku);
    }
}
