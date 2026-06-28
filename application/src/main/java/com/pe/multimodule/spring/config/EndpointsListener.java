package com.pe.multimodule.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Comparator;

record Pair(String first, String second) {}

public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {

    public static final  EndpointsListener instance = new EndpointsListener();
    private static final Logger logger   = LoggerFactory.getLogger(EndpointsListener.class);

    private EndpointsListener() {
        // Singleton
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final var endpoints = new ArrayList<Pair>();

        logger.info("===========================Rest Endpoints===========================");
        final var applicationContext = event.getApplicationContext();
        applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class).getHandlerMethods()
                .forEach((endpoint, _) -> {
                    if (endpoint.getPathPatternsCondition() != null) {
                        endpoints.add(new Pair(endpoint.getMethodsCondition().toString(), endpoint.getPathPatternsCondition().toString()));
                    }
                });

        endpoints
                .stream().sorted(Comparator.comparing(Pair::second))
                .forEach(pair -> logger.info("{}: {}", pair.first(), pair.second()));
    }
}
