package com.pe.multimodule.spring.config;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class FlyWayConfiguration {

    @Bean
    public ClassicConfiguration flywayConfig(@Autowired HikariDataSource dataSource) {
        final var config = new ClassicConfiguration();
        config.setDataSource(dataSource);
        config.setLocationsAsStrings("classpath:db/migrations");
        config.setConnectRetries(Integer.MAX_VALUE);
        config.setCleanDisabled(true);              // Prevent Flyway from cleaning the DB
        config.setCleanOnValidationError(false);
        config.setOutOfOrder(false);                // Do not execute migrations with version lower than the latest applied
        config.setValidateOnMigrate(true);          // Validate that the migrations applied to the database are the ones on the classpath
        return config;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway(@Autowired ClassicConfiguration flywayConfig) {
        return new Flyway(flywayConfig);
    }
}
