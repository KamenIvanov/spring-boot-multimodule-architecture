package com.pe.multimodule.dao.impl.outbox;

import com.pe.multimodule.dao.api.Paging;
import com.pe.multimodule.dao.api.outbox.OutboxEventDao;
import com.pe.multimodule.dao.impl.AbstractCrudTestCase;
import com.pe.multimodule.domain.asserter.DeepEqualsAsserter;
import com.pe.multimodule.domain.asserter.outbox.OutboxEventAsserter;
import com.pe.multimodule.domain.factory.OutboxEventFactory;
import com.pe.multimodule.domain.outbox.OutboxEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OutboxEventDaoTestCase extends AbstractCrudTestCase<UUID, OutboxEvent, OutboxEventDao> {

    @Autowired
    private OutboxEventDao outboxEventDao;

    @Override
    protected OutboxEventDao getDao() {
        return outboxEventDao;
    }

    @Override
    public OutboxEvent createDomain() {
        return OutboxEventFactory.create(1);
    }

    @Override
    protected DeepEqualsAsserter<OutboxEvent> getAsserter() {
        return OutboxEventAsserter.instance;
    }

    @Test
    void testLoad_withValidBucketAndPaging() {
        final int targetBucketId = 1;
        final int otherBucketId = 2;

        // Create and save events for the target bucket
        getDao().save(OutboxEventFactory.create(targetBucketId));
        getDao().save(OutboxEventFactory.create(targetBucketId));
        getDao().save(OutboxEventFactory.create(targetBucketId));

        // Create and save events for a different bucket
        getDao().save(OutboxEventFactory.create(otherBucketId));
        getDao().save(OutboxEventFactory.create(otherBucketId));

        final Paging paging = new Paging(0, 10);
        final List<OutboxEvent> loadedEvents = getDao().load(targetBucketId, paging);

        assertEquals(3, loadedEvents.size(), "Should load 3 events for the target bucket");
        for (OutboxEvent event : loadedEvents) {
            assertEquals(targetBucketId, event.getBucketId(), "All loaded events should have the target bucketId");
        }
    }

    @Test
    void testLoad_withNoMatchingEvents() {
        final int targetBucketId = 3;
        final int otherBucketId = 4;

        // Create and save events for a different bucket
        getDao().save(OutboxEventFactory.create(otherBucketId));
        getDao().save(OutboxEventFactory.create(otherBucketId));

        final Paging paging = new Paging(0, 10);
        final List<OutboxEvent> loadedEvents = getDao().load(targetBucketId, paging);

        assertTrue(loadedEvents.isEmpty(), "Should load no events for a non-existent bucketId");
    }

    @Test
    void testLoad_withPagingParameters() {
        final int targetBucketId = 5;

        // Create and save multiple events for the target bucket
        for (int i = 0; i < 5; i++) {
            getDao().save(OutboxEventFactory.create(targetBucketId));
        }

        List<OutboxEvent> loadedEvents = getDao().load(targetBucketId, new Paging(0, 2));
        assertEquals(2, loadedEvents.size(), "Should load 2 events on page 1");

        loadedEvents = getDao().load(targetBucketId, new Paging(1, 2));
        assertEquals(2, loadedEvents.size(), "Should load 2 events on page 2");

        loadedEvents = getDao().load(targetBucketId, new Paging(2, 2));
        assertEquals(1, loadedEvents.size(), "Should load 1 event on page 3");

        loadedEvents = getDao().load(targetBucketId, new Paging(3, 2));
        assertTrue(loadedEvents.isEmpty(), "Should load 0 events on page 4");
    }
}
