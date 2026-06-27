package com.pe.multimodule.dao.impl;

import com.pe.multimodule.dao.api.BaseQuery;
import com.pe.multimodule.dao.api.ResultPage;
import com.pe.multimodule.dao.api.SearchableDao;
import com.pe.multimodule.dao.api.SortableEnum;
import com.pe.multimodule.domain.AbstractDomain;
import com.pe.multimodule.domain.transformer.BiTransformer;
import jakarta.validation.Validator;
import org.springframework.data.repository.CrudRepository;

public abstract class SearchableDaoImpl<
        IdType,
        Domain extends AbstractDomain<IdType>,
        Entity extends AbstractEntity,
        Repo extends CrudRepository<Entity, IdType>,
        Query  extends BaseQuery<IdType, ? extends SortableEnum>
    > extends CrudDaoImpl<IdType, Domain, Entity, Repo> implements SearchableDao<IdType, Domain, Query> {

    protected SearchableDaoImpl(Repo repository, BiTransformer<Entity, Domain> transformer, Validator validator) {
        super(repository, transformer, validator);
    }

    @Override
    public ResultPage<Domain> search(Query criteria) {
        return null;
    }
}
