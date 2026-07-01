package com.pe.multimodule.spring.config;

import com.pe.multimodule.api.rest.pub.ProductsFilteringRestService;
import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.bl.service.pub.ProductsFilteringRestServiceImpl;
import com.pe.multimodule.bl.service.secured.ProductsServiceImpl;
import com.pe.multimodule.dao.api.product.ProductDao;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

public class ServiceWorkersConfiguration {

    @Bean("productsServiceWorker")
    @Transactional
    public ProductsRestService productsServiceWorker(ProductDao productDao) {
        return new ProductsServiceImpl(productDao);
    }

    @Bean
    public ProductsFilteringRestService productsFilteringService(ProductDao productDao) {
        return new ProductsFilteringRestServiceImpl(productDao);
    }
}
