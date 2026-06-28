package com.pe.multimodule.bl.service.secured;

import com.pe.multimodule.api.rest.secured.CrudRestService;
import com.pe.multimodule.bl.service.AbstractService;
import com.pe.multimodule.dao.api.CrudDao;
import com.pe.multimodule.domain.AbstractDomain;
import com.pe.multimodule.domain.exceptions.AuthorizationException;
import com.pe.multimodule.domain.exceptions.NotFoundException;
import com.pe.multimodule.domain.transformer.BiTransformer;
import com.pe.multimodule.domain.transformer.Transformer;
import com.pe.multimodule.dto.AbstractDto;
import com.pe.multimodule.dto.AbstractEntityDto;

import java.util.UUID;

public abstract class AbstractCrudService<
        Domain extends AbstractDomain<UUID>,
        NewEntityDto extends AbstractDto,
        Dto extends AbstractEntityDto,
        Dao extends CrudDao<UUID, Domain>
        > extends AbstractService implements CrudRestService<NewEntityDto, Dto> {

    private final Dao dao;

    protected AbstractCrudService(Dao dao) {
        this.dao = dao;
    }

    @Override
    public Dto create(NewEntityDto entityVo, UUID requesterId) {
        if (requesterId == null) {
            throw new AuthorizationException(UNAUTHORIZED);
        }

        final var entity = getCreateTransformer().createOutput(entityVo);
        preProcessNewEntity(entity, requesterId);

        dao.save(entity);
        return getTransformer().createOutput(entity);
    }

    @Override
    public Dto update(Dto dto, UUID requesterId) {
        if (requesterId == null) {
            throw new AuthorizationException(UNAUTHORIZED);
        }

        if (dto.getId() == null) {
            throw new NotFoundException(MISSING_ENTITY);
        }

        final var domain = loadOrThrowNotFound(() -> dao.loadById(dto.getId()));

        // Can the requester modify the entity?
        authorize(domain, requesterId);
        //
        getTransformer().copyToInput(dto, domain);
        final var updated = dao.save(domain);

        return getTransformer().createOutput(updated);
    }

    @Override
    public Dto loadById(UUID id, UUID requesterId) {
        if (requesterId == null) {
            throw new AuthorizationException(UNAUTHORIZED);
        }

        final var entity = loadOrThrowNotFound(() -> dao.loadById(id));
        authorize(entity, requesterId);
        return getTransformer().createOutput(entity);
    }

    @Override
    public void delete(UUID id, UUID requesterId) {
        if (requesterId == null) {
            throw new AuthorizationException(UNAUTHORIZED);
        }

        final var entity = dao.loadById(id);

        if (entity == null) {
            return;
        }

        authorizeDelete(entity, requesterId);
        dao.delete(entity);
    }

    protected Dao getDao() {
        return dao;
    }

    /**
     * This will be called right before the entity validation and persist to database occurs.
     *
     * @param domain      the new entity
     * @param requesterId the requester identification
     */
    protected void preProcessNewEntity(Domain domain, UUID requesterId) {
        // empty by default
    }

    protected void authorizeDelete(Domain domain, UUID requesterId) {
        this.authorize(domain, requesterId);
    }

    protected abstract Transformer<NewEntityDto, Domain> getCreateTransformer();

    protected abstract BiTransformer<Domain, Dto> getTransformer();

    protected abstract void authorize(Domain domain, UUID requesterId);
}
