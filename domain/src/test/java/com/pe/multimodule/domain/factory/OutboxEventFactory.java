package com.pe.multimodule.domain.factory;

import com.pe.multimodule.domain.outbox.OutboxEvent;

import java.time.Instant;
import java.util.UUID;

public class OutboxEventFactory {

    private OutboxEventFactory() {
        // POJO
    }

    public static OutboxEvent create(int bucketId) {
        final var outboxEvent = new OutboxEvent();
        outboxEvent.setCreatedAt(Instant.now());
        outboxEvent.setCreatedById(UUID.randomUUID());
        outboxEvent.setUpdatedAt(Instant.now());
        outboxEvent.setAggregateId(UUID.randomUUID());
        outboxEvent.setBucketId(bucketId);
        outboxEvent.setEventType("some-event");
        outboxEvent.setTopic("example-topic");
        outboxEvent.setPayload("{}");
        return outboxEvent;
    }
}
