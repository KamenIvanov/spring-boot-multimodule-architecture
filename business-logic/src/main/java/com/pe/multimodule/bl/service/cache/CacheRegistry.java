package com.pe.multimodule.bl.service.cache;

import com.pe.multimodule.bridge.api.cache.Cache;
import com.pe.multimodule.bridge.api.cache.CacheFactory;
import com.pe.multimodule.dto.product.ProductDto;

import java.util.UUID;

public class CacheRegistry {

    protected final CacheFactory cacheFactory;

    public CacheRegistry(CacheFactory cacheFactory) {
        this.cacheFactory = cacheFactory;
    }

    public Cache<UUID, ProductDto> getProductsCache() {
        return cacheFactory.cache(CacheNames.PRODUCTS.getName());
    }
}
