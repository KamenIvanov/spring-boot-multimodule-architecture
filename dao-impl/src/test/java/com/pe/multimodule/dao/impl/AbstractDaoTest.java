package com.pe.multimodule.dao.impl;

import com.pe.multimodule.dao.impl.hibernate.HsqldbQueryFactory;
import com.pe.multimodule.dao.impl.hibernate.TablesEraser;
import com.pe.multimodule.dao.impl.outbox.OutboxEventDaoImpl;
import com.pe.multimodule.dao.impl.product.ProductDaoImpl;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestApplication.class)
@Import({
        ProductDaoImpl.class,
        OutboxEventDaoImpl.class,
        TestConfig.class
})
public abstract class AbstractDaoTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        TablesEraser.emptyAllTables(entityManagerFactory, HsqldbQueryFactory.instance);
    }
}
