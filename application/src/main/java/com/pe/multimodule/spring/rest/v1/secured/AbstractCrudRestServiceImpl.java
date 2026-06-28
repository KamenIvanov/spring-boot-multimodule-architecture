package com.pe.multimodule.spring.rest.v1.secured;

import com.pe.multimodule.api.rest.secured.CrudRestService;
import com.pe.multimodule.dto.AbstractDto;
import com.pe.multimodule.dto.AbstractEntityDto;
import com.pe.multimodule.spring.rest.v1.AbstractRestServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.pe.multimodule.api.rest.HeaderConstants.REQUESTER_ID;

public abstract class AbstractCrudRestServiceImpl<
        NewEntityVo extends AbstractDto,
        EntityVo extends AbstractEntityDto,
        ServiceWorker extends CrudRestService<NewEntityVo, EntityVo>
        >
        extends AbstractRestServiceImpl<ServiceWorker> implements CrudRestService<NewEntityVo, EntityVo> {

    protected AbstractCrudRestServiceImpl(ServiceWorker serviceWorker) {
        super(serviceWorker);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @Override
    public EntityVo create(@RequestBody NewEntityVo entityVo, @RequestHeader(REQUESTER_ID) UUID requesterId) {
        return getServiceWorker().create(entityVo, requesterId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    @Override
    public EntityVo update(@RequestBody EntityVo entityVo, @RequestHeader(REQUESTER_ID) UUID requesterId) {
        return getServiceWorker().update(entityVo, requesterId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id:[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}}")
    @Override
    public EntityVo loadById(@PathVariable("id") UUID id, @RequestHeader(REQUESTER_ID) UUID requesterId) {
        return getServiceWorker().loadById(id, requesterId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id:[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}}")
    @Override
    public void delete(@PathVariable("id") UUID id, @RequestHeader(REQUESTER_ID) UUID requesterId) {
        getServiceWorker().delete(id, requesterId);
    }
}
