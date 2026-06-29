package com.pe.multimodule.dao.impl.proxy;

import com.pe.multimodule.dao.api.CrudDao;
import com.pe.multimodule.domain.AbstractDomain;
import jakarta.persistence.EntityManager;

public abstract class CrudDaoProxy <IdType, Domain extends AbstractDomain<IdType>, Dao extends CrudDao<IdType, Domain>> implements CrudDao<IdType, Domain> {

    protected final Dao proxied;
    private final EntityManager entityManager;

    protected CrudDaoProxy(Dao proxied, EntityManager entityManager) {
        this.proxied = proxied;
        this.entityManager = entityManager;
    }

    @Override
    public Domain save(Domain domain) {
        final var saved = proxied.save(domain);
        commit();
        return saved;
    }

    @Override
    public Domain loadById(IdType id) {
        return proxied.loadById(id);
    }

    @Override
    public void delete(Domain domain) {
        proxied.delete(domain);
        commit();
    }

    protected void commit() {
        entityManager.flush(); // Send the insert queries to Database
        entityManager.clear(); // Clear the EntityManager
    }
}