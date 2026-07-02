package com.pe.multimodule.spring.config;

import com.pe.multimodule.api.rest.pub.ProductsFilteringRestService;
import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.bl.service.DomainEventPublisher;
import com.pe.multimodule.bl.service.pub.ProductsFilteringRestServiceImpl;
import com.pe.multimodule.bl.service.secured.ProductsServiceImpl;
import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.spring.adapters.SpringDomainEventPublisherAdapter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

public class ServiceWorkersConfiguration {

    @Bean
    public DomainEventPublisher domainEventPublisher(ApplicationEventPublisher springPublisher) {
        return new SpringDomainEventPublisherAdapter(springPublisher);
    }

    @Bean("productsServiceWorker")
    @Transactional
    public ProductsRestService productsServiceWorker(ProductDao productDao, DomainEventPublisher domainEventPublisher) {
        return new ProductsServiceImpl(productDao, domainEventPublisher);
    }

    @Bean
    public ProductsFilteringRestService productsFilteringService(ProductDao productDao) {
        return new ProductsFilteringRestServiceImpl(productDao);
    }
}
