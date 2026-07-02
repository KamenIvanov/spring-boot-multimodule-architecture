package com.pe.multimodule.spring.async;

import com.pe.multimodule.api.JsonSerializer;
import com.pe.multimodule.dao.api.outbox.OutboxEventDao;
import com.pe.multimodule.domain.outbox.OutboxEvent;
import com.pe.multimodule.events.TopicNames;
import com.pe.multimodule.events.products.*;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

public class ProductOutboxListener {

    private final OutboxEventDao outboxEventDao;

    public ProductOutboxListener(OutboxEventDao outboxEventDao) {
        this.outboxEventDao = outboxEventDao;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleProductCreated(ProductCreatedEventDto event) {
        createOutboxRecord(event.getProductId(), ProductTypes.CREATED, event);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleProductUpdated(ProductUpdatedEventDto event) {
        createOutboxRecord(event.getProductId(), ProductTypes.UPDATED, event);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleProductDeleted(ProductDeletedEventDto event) {
        createOutboxRecord(event.getProductId(), ProductTypes.DELETED, event);
    }

    private void createOutboxRecord(UUID aggregateId, String eventType, AbstractProductEventDto productEvent) {
        final var outbox = new OutboxEvent();
        outbox.setAggregateId(aggregateId);
        outbox.setTopic(TopicNames.PRODUCTS_OUTPUT_TOPIC);
        outbox.setEventType(eventType);
        outbox.setPayload(JsonSerializer.instance.process(productEvent));

        outboxEventDao.save(outbox);
    }
}
