package com.pe.multimodule.domain.outbox;

import com.pe.multimodule.domain.AbstractDomain;

import java.util.UUID;

public class OutboxEvent extends AbstractDomain<UUID> {

    private UUID aggregateId;
    private int bucketId;
    private String eventType;
    private String topic;
    private String payload;

    public OutboxEvent() {
        // POJO
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
//        if (aggregateId != null) {
//            // Mathematically divide the UUIDs in 8 buckets (0 to 7)
//            this.bucketId = Math.abs(aggregateId.hashCode() % 8);
//        }
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
