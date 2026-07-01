package com.pe.multimodule.bl.service.cache;

import com.pe.multimodule.api.rest.secured.CrudRestService;
import com.pe.multimodule.bridge.api.cache.Cache;
import com.pe.multimodule.dto.AbstractDto;
import com.pe.multimodule.dto.AbstractEntityDto;

import java.util.UUID;

public abstract class AbstractCrudServiceCacheProxy<
        NewDto extends AbstractDto,
        CachedValue extends AbstractEntityDto,
        CrudService extends CrudRestService<NewDto, CachedValue>
        >
        implements CrudRestService<NewDto, CachedValue> {

    protected final CrudService crudServiceWorker;
    protected final Cache<UUID, CachedValue> cache;

    protected AbstractCrudServiceCacheProxy(CrudService crudServiceWorker, Cache<UUID, CachedValue> cache) {
        this.crudServiceWorker = crudServiceWorker;
        this.cache = cache;
    }

    @Override
    public CachedValue create(NewDto newDto, UUID requesterId) {
        return crudServiceWorker.create(newDto, requesterId);
    }

    @Override
    public CachedValue update(CachedValue dto, UUID requesterId) {
        final var updated = crudServiceWorker.update(dto, requesterId);
        cache.put(dto.getId(), updated); // Invalidate the cache
        return updated;
    }

    @Override
    public CachedValue loadById(UUID id, UUID requesterId) {
        if (cache.contains(id)) {
            return cache.get(id);
        }
        final var dto = crudServiceWorker.loadById(id, requesterId);
        cache.put(id, dto);
        return dto;
    }

    @Override
    public void delete(UUID id, UUID requesterId) {
        crudServiceWorker.delete(id, requesterId);
        cache.remove(id); // Delete it from cache also
    }
}
