package com.pe.multimodule.dao.impl.proxy;

import com.pe.multimodule.dao.api.BaseQuery;
import com.pe.multimodule.dao.api.ResultPage;
import com.pe.multimodule.dao.api.SearchableDao;
import com.pe.multimodule.dao.api.SortableEnum;
import com.pe.multimodule.domain.AbstractDomain;
import jakarta.persistence.EntityManager;

public abstract class SearchableCrudDaoProxy<
        IdType,
        Domain extends AbstractDomain<IdType>,
        Query extends BaseQuery<IdType, ? extends SortableEnum>,
        Dao extends SearchableDao<IdType, Domain, Query>
        > extends CrudDaoProxy<IdType, Domain, Dao> implements SearchableDao<IdType, Domain, Query> {

    protected SearchableCrudDaoProxy(Dao proxied, EntityManager entityManager) {
        super(proxied, entityManager);
    }

    @Override
    public ResultPage<Domain> search(Query criteria) {
        return proxied.search(criteria);
    }
}
