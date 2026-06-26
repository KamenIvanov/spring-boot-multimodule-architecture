package com.pe.multimodule.dao.impl;

import com.pe.multimodule.dao.api.CrudDao;
import com.pe.multimodule.dao.api.SortDirection;
import com.pe.multimodule.dao.api.SortableEnum;
import com.pe.multimodule.domain.AbstractDomain;
import com.pe.multimodule.domain.transformer.BiTransformer;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public abstract class CrudDaoImpl<
        IdType,
        Domain extends AbstractDomain<IdType>,
        Entity extends AbstractEntity<IdType>,
        Repo extends CrudRepository<Entity, IdType>
    > implements CrudDao<IdType, Domain>
{

    public static final Logger logger = LoggerFactory.getLogger(CrudDaoImpl.class);

    protected static final Sort ID_DESC_SORT = Sort.by(Sort.Direction.DESC, "id");
    protected static final Sort CREATED_AT_DESC_SORT = Sort.by("createdAt").descending().and(ID_DESC_SORT);

    protected final Validator validator;

    protected final Repo repository;
    protected final BiTransformer<Domain, Entity> transformer;

    protected CrudDaoImpl(Repo repository, BiTransformer<Domain, Entity> transformer, Validator validator) {
        this.repository = repository;
        this.transformer = transformer;
        this.validator = validator;
    }

    @Override
    public Domain save(Domain domain) {
        if (domain == null) {
            throw new IllegalArgumentException("Entity is mandatory");
        }

        if (domain.getId() != null) {
            var entity = repository.findById(domain.getId()).orElse(null);
            if (entity != null) {
                logger.info("There is an existing entity with id '{}'. Performing update operation.", domain.getId());
                transformer.copyToOutput(domain, entity);
                preUpdate(entity);
                entity = repository.save(entity);
            }
            return transformer.createInput(entity);
        }

        var entity = transformer.createOutput(domain);
        validateEntity(entity);
        preCreate(entity);
        logger.info("Saving new entity with id '{}'", domain.getId());
        entity = repository.save(entity);
        return transformer.createInput(entity);
    }

    @Override
    public Domain loadById(IdType id) {
        if (id == null) {
            return null;
        }

        final var entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return transformer.createInput(entity);
    }

    @Override
    public void delete(Domain domain) {
        if (domain == null) {
            throw new IllegalArgumentException("Entity is mandatory");
        }
        Objects.requireNonNull(domain.getId(), "ID must not be null");
        repository.findById(domain.getId()).ifPresent(repository::delete);
    }

    protected void preCreate(AbstractEntity entity) {
        entity.setCreatedAt(Instant.now());
    }

    protected void preUpdate(AbstractEntity entity) {
    }

    protected void validateEntity(Entity entity) {
        var violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    protected Sort getSortOrDefault(SortableEnum field, SortDirection direction) {
        if (field == null) {
            return CREATED_AT_DESC_SORT;
        }

        var orderDirection = switch (direction) {
            case ASC -> Sort.Direction.ASC;
            default -> Sort.Direction.DESC; // Handles both null and DESC
        };

        // ID as tiebreaker ensures stable pagination when sortBy field has equal values
        return Sort.by(orderDirection, field.getValue()).and(ID_DESC_SORT);
    }
}
