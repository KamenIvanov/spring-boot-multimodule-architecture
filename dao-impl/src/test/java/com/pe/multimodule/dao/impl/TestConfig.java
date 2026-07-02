package com.pe.multimodule.dao.impl;

import com.pe.multimodule.dao.api.outbox.OutboxEventDao;
import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.dao.impl.outbox.OutboxEventDaoImpl;
import com.pe.multimodule.dao.impl.product.ProductDaoImpl;
import com.pe.multimodule.dao.impl.proxy.OutboxEventDaoProxy;
import com.pe.multimodule.dao.impl.proxy.ProductsDaoProxy;
import jakarta.persistence.EntityManager;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;

public class TestConfig {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ProductDao productDao(ProductDaoImpl productDao, EntityManager entityManager) {
        return new ProductsDaoProxy(productDao, entityManager);
    }

    @Bean
    public OutboxEventDao outboxEventDao(OutboxEventDaoImpl outboxEventDao, EntityManager entityManager) {
        return new OutboxEventDaoProxy(outboxEventDao, entityManager);
    }
}
