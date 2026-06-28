package com.pe.multimodule.spring.config;

import com.pe.multimodule.api.JsonMapperSingleton;
import com.pe.multimodule.api.rest.pub.ProductsFilteringRestService;
import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.spring.exception.GeneralExceptionHandler;
import com.pe.multimodule.spring.rest.v1.pub.ProductsFilteringRestServiceImpl;
import com.pe.multimodule.spring.rest.v1.secured.ProductsRestServiceImpl;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

public class RestServicesConfiguration {

    /*
     * Prints all registered endpoints
     */
    @Bean
    public EndpointsListener endpointsListener() {
        return EndpointsListener.instance;
    }

    @Bean
    public BuildInfoContributor buildInfoContributor() throws IOException {
        return new BuildInfoContributor();
    }

    @Bean
    public JsonMapper jsonMapper() {
        return JsonMapperSingleton.instance.getJsonMapper();
    }

    @Bean
    public GeneralExceptionHandler exceptionHandler() {
        return new GeneralExceptionHandler();
    }

    @Bean
    public ProductsRestService accountsRestService(ProductsRestService productsService) {
        return new ProductsRestServiceImpl(productsService);
    }

    @Bean
    public ProductsFilteringRestService settingsRestService(ProductsFilteringRestService productsFilteringService) {
        return new ProductsFilteringRestServiceImpl(productsFilteringService);
    }
}
