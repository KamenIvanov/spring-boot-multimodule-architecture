package com.pe.multimodule.dao.impl.proxy;

import com.pe.multimodule.dao.api.Paging;
import com.pe.multimodule.dao.api.outbox.OutboxEventDao;
import com.pe.multimodule.domain.outbox.OutboxEvent;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

public class OutboxEventDaoProxy extends CrudDaoProxy<UUID, OutboxEvent, OutboxEventDao> implements OutboxEventDao {

    public OutboxEventDaoProxy(OutboxEventDao proxied, EntityManager entityManager) {
        super(proxied, entityManager);
    }

    @Override
    public List<OutboxEvent> load(int bucketId, Paging paging) {
        return proxied.load(bucketId, paging);
    }
}
