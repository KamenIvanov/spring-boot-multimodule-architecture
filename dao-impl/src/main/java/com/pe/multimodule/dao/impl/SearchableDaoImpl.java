package com.pe.multimodule.dao.impl;

import com.pe.multimodule.dao.api.BaseQuery;
import com.pe.multimodule.dao.api.ResultPage;
import com.pe.multimodule.dao.api.SearchableDao;
import com.pe.multimodule.dao.api.SortableEnum;
import com.pe.multimodule.domain.AbstractDomain;
import com.pe.multimodule.domain.transformer.BiTransformer;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public abstract class SearchableDaoImpl<
        IdType,
        Domain extends AbstractDomain<IdType>,
        Entity extends AbstractEntity,
        Repo extends CrudRepository<Entity, IdType> & JpaSpecificationExecutor<Entity>,
        Query  extends BaseQuery<IdType, ? extends SortableEnum>
    > extends CrudDaoImpl<IdType, Domain, Entity, Repo> implements SearchableDao<IdType, Domain, Query> {

    protected SearchableDaoImpl(Repo repository, BiTransformer<Entity, Domain> transformer, Validator validator) {
        super(repository, transformer, validator);
    }

    @Override
    public ResultPage<Domain> search(Query criteria) {
        final Pageable pageable = toPage(criteria);
        final Specification<Entity> spec = createSpecification(criteria);
        final Page<Entity> result = repository.findAll(spec, pageable);
        final List<Domain> content = result.getContent()
                .stream()
                .map(transformer::createOutput)
                .toList();

        return new ResultPage<>(result.getTotalPages(), result.getTotalElements(), content);
    }

    private Pageable toPage(Query criteria) {
        return PageRequest.of(
                criteria.getPage(),
                criteria.getSize(),
                getSortOrDefault(criteria.getSortBy(), criteria.getSortDirection())
        );
    }

    protected abstract Specification<Entity> createSpecification(Query criteria);
}
