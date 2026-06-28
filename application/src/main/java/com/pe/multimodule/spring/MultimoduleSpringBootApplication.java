package com.pe.multimodule.spring;

import com.pe.multimodule.spring.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.pe.multimodule.spring.*"))
@Import({
        BasicConfiguration.class,
        BridgeConfiguration.class,
        FlyWayConfiguration.class,
        PersistenceConfiguration.class,
        ServiceWorkersConfiguration.class,
        RestServicesConfiguration.class
})
public class MultimoduleSpringBootApplication {
    static void main(String[] args) {
        SpringApplication.run(MultimoduleSpringBootApplication.class, args);
    }
}
