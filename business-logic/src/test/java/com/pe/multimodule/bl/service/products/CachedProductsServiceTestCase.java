package com.pe.multimodule.bl.service.products;

import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.bl.service.AbstractServiceTestCase;
import com.pe.multimodule.bl.service.cache.CacheNames;
import com.pe.multimodule.bl.service.cache.CacheRegistry;
import com.pe.multimodule.bl.service.cache.ProductsServiceCacheProxy;
import com.pe.multimodule.bridge.api.cache.Cache;
import com.pe.multimodule.bridge.api.cache.CacheFactory;
import com.pe.multimodule.dto.product.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CachedProductsServiceTestCase extends AbstractServiceTestCase {

    @Mock
    private ProductsRestService coreService;

    @Mock
    private CacheFactory cacheFactory;

    @Mock
    private Cache<UUID, ProductDto> productCache;

    private ProductsRestService cachedService;

    @BeforeEach
    void setUp() {
        when(cacheFactory.<UUID, ProductDto>cache(CacheNames.PRODUCTS.getName())).thenReturn(productCache);

        final var cacheRegistry = new CacheRegistry(cacheFactory);
        this.cachedService = new ProductsServiceCacheProxy(coreService, cacheRegistry.getProductsCache());
    }

    @Test
    void loadById_ShouldReturnFromCache_WhenCacheHit() {
        UUID productId = UUID.randomUUID();
        UUID requesterId = UUID.randomUUID();
        ProductDto expectedDto = createEntityDto(productId);

        // Simulate Cache Hit: cache contains the key and returns the value
        when(productCache.contains(productId)).thenReturn(true);
        when(productCache.get(productId)).thenReturn(expectedDto);

        // When
        ProductDto result = cachedService.loadById(productId, requesterId);

        // Then
        assertNotNull(result);
        assertEquals(expectedDto, result);

        // Verify that the real service is NOT called, we have cache hit
        verifyNoInteractions(coreService);
        verify(productCache, times(1)).get(productId);
    }

    @Test
    void loadById_ShouldCallCoreServiceAndPopulateCache_WhenCacheMiss() {
        UUID productId = UUID.randomUUID();
        UUID requesterId = UUID.randomUUID();
        ProductDto expectedDto = createEntityDto(productId);

        // Simulate Cache Miss
        when(productCache.contains(productId)).thenReturn(false);
        when(coreService.loadById(productId, requesterId)).thenReturn(expectedDto);

        // When
        ProductDto result = cachedService.loadById(productId, requesterId);

        // Then
        assertNotNull(result);
        assertEquals(expectedDto, result);

        // Verify the stream: cache checked -> called service worker -> written in cache
        verify(productCache, times(1)).contains(productId);
        verify(coreService, times(1)).loadById(productId, requesterId);
        verify(productCache, times(1)).put(productId, expectedDto);
    }

    @Test
    void update_ShouldCallCoreServiceAndRefreshCache() {
        // Given
        UUID productId = UUID.randomUUID();
        UUID requesterId = UUID.randomUUID();
        ProductDto inputDto = createEntityDto(productId);
        ProductDto updatedDto = createEntityDto(productId);

        when(coreService.update(inputDto, requesterId)).thenReturn(updatedDto);

        // When
        ProductDto result = cachedService.update(inputDto, requesterId);

        // Then
        assertNotNull(result);

        // On update, we always call the real service and update the cache with the new value
        verify(coreService, times(1)).update(inputDto, requesterId);
        verify(productCache, times(1)).put(productId, updatedDto);
    }

    @Test
    void delete_ShouldCallCoreServiceAndEvictFromCache() {
        // Given
        UUID productId = UUID.randomUUID();
        UUID requesterId = UUID.randomUUID();

        // When
        cachedService.delete(productId, requesterId);

        // Then
        // On delete, we have to be sure that it's deleted from the DB and from the cache (Cache Eviction)
        verify(coreService, times(1)).delete(productId, requesterId);
        verify(productCache, times(1)).remove(productId);
    }

    @Test
    void getProducts_ShouldBypassCacheAndCallCoreServiceDirectly() {
        // Given
        UUID requesterId = UUID.randomUUID();

        // When
        cachedService.getProducts(0, 20, null, null, requesterId);

        // Then
        // We don't cache the page searches, so we don't have combination explosion
        verify(coreService, times(1)).getProducts(0, 20, null, null, requesterId);
        verifyNoInteractions(productCache);
    }

    protected ProductDto createEntityDto(UUID id) {
        final var product = new ProductDto();
        product.setId(id);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        product.setName("Simple name");
        product.setPrice(BigDecimal.TWO);
        product.setSku(UUID.randomUUID().toString());
        return product;
    }
}
