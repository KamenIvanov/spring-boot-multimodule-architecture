package com.pe.multimodule.dao.impl.outbox;

import com.pe.multimodule.dao.impl.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "OUTBOX_EVENTS", indexes = {
        // Important composite index for the performance
        @Index(name = "idx_outbox_events_bucket_id", columnList = "bucket_id, id ASC")
})
public class OutboxEventEntity extends AbstractEntity {

    @Column(name = "aggregate_id", nullable = false)
    @NotNull
    private UUID aggregateId;

    @Column(name = "bucket_id", nullable = false)
    private int bucketId;

    @Column(name = "event_type", length = 256, nullable = false)
    @NotNull
    private String eventType;

    @Column(name = "topic", length = 256, nullable = false)
    @NotNull
    private String topic;

    @Column(name = "payload", columnDefinition = "CLOB", nullable = false)
    @NotNull
    private String payload;

    public OutboxEventEntity() {
        // POJO
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public int getBucketId() {
        return bucketId;
    }

    public void setBucketId(int bucketId) {
        this.bucketId = bucketId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
