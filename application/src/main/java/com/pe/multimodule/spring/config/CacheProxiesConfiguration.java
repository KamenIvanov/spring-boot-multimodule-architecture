package com.pe.multimodule.spring.config;

import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.bl.service.cache.CacheRegistry;
import com.pe.multimodule.bl.service.cache.ProductsServiceCacheProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class CacheProxiesConfiguration {

    @Bean
    public ProductsRestService productsService(@Qualifier("productsServiceWorker") ProductsRestService productsServiceWorker, CacheRegistry cacheRegistry) {
        return new ProductsServiceCacheProxy(productsServiceWorker, cacheRegistry.getProductsCache());
    }
}
