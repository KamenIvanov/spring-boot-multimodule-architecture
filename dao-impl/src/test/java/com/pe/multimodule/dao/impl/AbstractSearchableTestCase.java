package com.pe.multimodule.dao.impl;

import com.pe.multimodule.dao.api.BaseQuery;
import com.pe.multimodule.dao.api.ResultPage;
import com.pe.multimodule.dao.api.SearchableDao;
import com.pe.multimodule.dao.api.SortableEnum;
import com.pe.multimodule.domain.AbstractDomain;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractSearchableTestCase<
        ID,
        Domain extends AbstractDomain<ID>,
        Dao extends SearchableDao<ID, Domain, Query>,
        Query extends BaseQuery<ID, ? extends SortableEnum>
        > extends AbstractCrudTestCase<ID, Domain, Dao> {

    @Test
    void testSearchAll() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            runSave(createDomainWithUniqueData());
            TimeUnit.MILLISECONDS.sleep(20);
        }

        final Query query = createQuery();
        query.setPage(0);
        query.setSize(10);

        final ResultPage<Domain> result = getDao().search(query);

        assertEquals(5, result.elements().size());
        assertTrue(result.totalHits() >= 5);
        assertEquals(1, result.totalPages());

        // Assert default order by createdAt descending
        final List<Domain> allElements = result.elements();
        for (int i = 0; i < allElements.size() - 1; i++) {
            assertTrue(
                    allElements.get(i).getCreatedAt().compareTo(allElements.get(i + 1).getCreatedAt()) >= 0,
                    "Elements should be ordered by createdAt descending"
            );
        }
    }

    @Test
    void testSearchByIds() {
        final Domain entity1 = runSave(createDomainWithUniqueData());
        final Domain entity2 = runSave(createDomainWithUniqueData());
        runSave(createDomainWithUniqueData()); // Create a third one that shouldn't be in the results

        final Query query = createQuery();
        query.setIds(List.of(entity1.getId(), entity2.getId()));
        query.setPage(0);
        query.setSize(10);

        final ResultPage<Domain> result = getDao().search(query);

        assertEquals(2, result.elements().size());
        assertTrue(result.elements().stream().anyMatch(e -> e.getId().equals(entity1.getId())));
        assertTrue(result.elements().stream().anyMatch(e -> e.getId().equals(entity2.getId())));
    }

    @Test
    void testSearchPageAndSize() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            runSave(createDomainWithUniqueData());
            TimeUnit.MILLISECONDS.sleep(20);
        }

        final Query query = createQuery();
        query.setPage(0);
        query.setSize(2);

        final ResultPage<Domain> result = getDao().search(query);

        assertEquals(2, result.elements().size());
        assertTrue(result.totalHits() >= 5);

        query.setPage(1);
        final ResultPage<Domain> resultPage2 = getDao().search(query);
        assertEquals(2, resultPage2.elements().size());
        assertNotEquals(result.elements().getFirst().getId(), resultPage2.elements().getFirst().getId());
    }

    @Test
    void testSearchOutOfRange() {
        for (int i = 0; i < 5; i++) {
            runSave(createDomainWithUniqueData());
        }

        final Query query = createQuery();
        query.setPage(999);
        query.setSize(10);

        final ResultPage<Domain> result = getDao().search(query);

        assertEquals(1, result.totalPages());
        assertEquals(5, result.totalHits());
        assertTrue(result.elements().isEmpty());
    }

    @Test
    void testSearchZeroSize() {
        for (int i = 0; i < 5; i++) {
            runSave(createDomainWithUniqueData());
        }

        final Query query = createQuery();
        query.setPage(0);
        query.setSize(0);
        assertThrows(IllegalArgumentException.class, () -> getDao().search(query));
    }

    @Test
    public void testSearchNullQuery() {
        assertThrows(NullPointerException.class, () -> getDao().search(null));
    }

    protected Domain createDomainWithUniqueData() {
        return createDomain();
    }

    protected abstract Query createQuery();
}
