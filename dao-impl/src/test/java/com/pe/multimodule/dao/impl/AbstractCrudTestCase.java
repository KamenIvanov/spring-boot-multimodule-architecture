package com.pe.multimodule.dao.impl;

import com.pe.multimodule.domain.asserter.DeepEqualsAsserter;
import com.pe.multimodule.dao.api.CrudDao;
import com.pe.multimodule.domain.AbstractDomain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class AbstractCrudTestCase<
        ID,
        D extends AbstractDomain<ID>,
        DAO extends CrudDao<ID, D>
        > extends AbstractDaoTest {

    @Test
    void testSave() {
        final D expectedEntity = runSave();
        final D dbEntity = getDao().loadById(expectedEntity.getId());
        assertNotNull(dbEntity, "missing entity");
        assertEquals(expectedEntity.getId(), dbEntity.getId());
        final D reloaded = getDao().loadById(expectedEntity.getId());
        getAsserter().assertDeepEquals(reloaded, dbEntity);
    }

    @Test
    void testSaveNull() {
        final var ex = Assertions.assertThrows(IllegalArgumentException.class, () -> getDao().save(null));
        Assertions.assertEquals("Entity is mandatory", ex.getMessage());
    }

    @Test
    void testMultipleSave() {
        D entity = createDomain();

        entity = getDao().save(entity);
        entity = getDao().loadById(entity.getId()); // reload after flush/clear
        entity = getDao().save(entity);

        final D dbEntity = getDao().loadById(entity.getId());
        assertNotNull(dbEntity, "entity is missing");
        getAsserter().assertDeepEquals(entity, dbEntity);
        //
        entity = getDao().loadById(entity.getId());
        entity = getDao().save(entity);
        entity = getDao().loadById(entity.getId()); // reload after flush/clear
        getDao().save(entity);
    }

    @Test
    protected void testDelete() {
        final D entity = runSave();

        final D savedEntity = getDao().loadById(entity.getId());
        if (savedEntity != null) {
            getDao().delete(savedEntity);
        }

        assertNull(getDao().loadById(entity.getId()), "entity is available");
    }

    @Test
    protected void testDeleteNull() {
        final var ex = Assertions.assertThrows(IllegalArgumentException.class, () -> getDao().delete(null));
        Assertions.assertEquals("Entity is mandatory", ex.getMessage());
    }

    @Test
    void testLoadById() {
        final D entity = runSave();
        final D dbEntity = getDao().loadById(entity.getId());
        assertNotNull(dbEntity, "entity is missing");
    }

    @Test
    void testLoadByIdNullArg() {
        final D dbEntity = getDao().loadById(null);
        assertNull(dbEntity);
    }

    protected D runSave() {
        final D expected = createDomain();
        return runSave(expected);
    }

    protected D runSave(D expected) {
        assertNull(getDao().loadById(expected.getId()), "entity is available");
        return getDao().save(expected);
    }

    protected abstract DAO getDao();

    public abstract D createDomain();

    protected abstract DeepEqualsAsserter<D> getAsserter();
}

