package com.pe.multimodule.spring.config;

import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.dao.impl.product.ProductDaoImpl;
import com.pe.multimodule.dao.impl.product.ProductRepository;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.validation.Validator;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

@EnableJpaRepositories(
        basePackages = "com.pe.multimodule.dao.impl",
        entityManagerFactoryRef = "entityManagerFactory"
)
@EntityScan(basePackages = "com.pe.multimodule.dao.impl")
public class PersistenceConfiguration {

    @Bean
    @DependsOn("flyway")
    public AbstractEntityManagerFactoryBean entityManagerFactory(@Autowired HikariDataSource dataSource) {
        final var properties = new Properties();
        properties.setProperty(AvailableSettings.DIALECT, MySQLDialect.class.getName());
        properties.setProperty(AvailableSettings.SHOW_SQL, "false");

        final var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setPackagesToScan("com.pe.multimodule.dao.impl");
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public ProductDao productDao(ProductRepository productRepository, Validator validator) {
        return new ProductDaoImpl(productRepository, validator);
    }
}
