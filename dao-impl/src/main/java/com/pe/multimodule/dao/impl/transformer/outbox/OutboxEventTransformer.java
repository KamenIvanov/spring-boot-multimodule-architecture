package com.pe.multimodule.dao.impl.transformer.outbox;

import com.pe.multimodule.dao.impl.outbox.OutboxEventEntity;
import com.pe.multimodule.dao.impl.transformer.AbstractEntityTransformer;
import com.pe.multimodule.domain.outbox.OutboxEvent;

public class OutboxEventTransformer extends AbstractEntityTransformer<OutboxEventEntity, OutboxEvent> {

    public static final OutboxEventTransformer instance = new OutboxEventTransformer();

    private OutboxEventTransformer() {
        // POJO
    }

    @Override
    public void copyToInput(OutboxEvent dest, OutboxEventEntity source) {
        super.copyToInput(dest, source);

        source.setAggregateId(dest.getAggregateId());
        source.setBucketId(dest.getBucketId());
        source.setEventType(dest.getEventType());
        source.setTopic(dest.getTopic());
        source.setPayload(dest.getPayload());
    }

    @Override
    public void copyToOutput(OutboxEventEntity source, OutboxEvent dest) {
        super.copyToOutput(source, dest);

        dest.setAggregateId(source.getAggregateId());
        dest.setBucketId(source.getBucketId());
        dest.setEventType(source.getEventType());
        dest.setTopic(source.getTopic());
        dest.setPayload(source.getPayload());
    }

    @Override
    public OutboxEventEntity createInput(OutboxEvent outboxEvent) {
        if (outboxEvent == null) {
            return null;
        }

        final var entity = new OutboxEventEntity();
        copyToInput(outboxEvent, entity);
        return entity;
    }

    @Override
    public OutboxEvent createOutput(OutboxEventEntity entity) {
        if (entity == null) {
            return null;
        }

        final var outboxEvent = new OutboxEvent();
        copyToOutput(entity, outboxEvent);
        return outboxEvent;
    }
}
