package com.pe.multimodule.spring.config;

import com.pe.multimodule.api.rest.RestUrl;
import com.pe.multimodule.dao.api.outbox.OutboxEventDao;
import com.pe.multimodule.spring.async.ProductOutboxListener;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class BasicConfiguration {

    @Bean(destroyMethod = "shutdownNow")
    public ScheduledExecutorService defaultScheduledExecutor() {
        return Executors.newScheduledThreadPool(8);
    }

    /* Documentation - OpenAPI */
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info()
                .title("Multimodule Service API")
                .version(RestUrl.APP_VERSION)
                .description("Multimodule Service API. All paths under /secured are with restricted access. All requests must be authenticated using JTW.")
                .termsOfService("https://multimodule.eu/terms/")
                .license(new License().name("Apache 2.0").url("https://springdoc.org/")));
    }

    @Bean
    public ProductOutboxListener productOutboxListener(OutboxEventDao outboxEventDao) {
        return new ProductOutboxListener(outboxEventDao);
    }
}
