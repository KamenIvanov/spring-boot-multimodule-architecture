package com.pe.multimodule.bl.service.cache;

import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.bridge.api.cache.Cache;
import com.pe.multimodule.dto.SortDirectionDto;
import com.pe.multimodule.dto.product.NewProductDto;
import com.pe.multimodule.dto.product.ProductDto;
import com.pe.multimodule.dto.product.ProductSortOptionDto;
import com.pe.multimodule.dto.product.ProductsDto;

import java.util.UUID;

public class ProductsServiceCacheProxy extends AbstractCrudServiceCacheProxy<NewProductDto, ProductDto, ProductsRestService> implements ProductsRestService {

    public ProductsServiceCacheProxy(ProductsRestService worker, Cache<UUID, ProductDto> productsCache) {
        super(worker, productsCache);
    }

    @Override
    public ProductsDto getProducts(int page, int size, ProductSortOptionDto sort, SortDirectionDto direction, UUID requesterId) {
        return crudServiceWorker.getProducts(page, size, sort, direction, requesterId);
    }
}
