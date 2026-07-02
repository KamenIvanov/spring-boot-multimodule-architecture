package com.pe.multimodule.domain.asserter.outbox;

import com.pe.multimodule.domain.asserter.AbstractDeepEqualsAsserter;
import com.pe.multimodule.domain.outbox.OutboxEvent;
import org.junit.jupiter.api.Assertions;

public class OutboxEventAsserter extends AbstractDeepEqualsAsserter<OutboxEvent> {

    public static final OutboxEventAsserter instance = new OutboxEventAsserter();

    private OutboxEventAsserter() {
        // Singleton
    }

    @Override
    public void assertDeepEquals(OutboxEvent expected, OutboxEvent actual) {
        super.assertDeepEquals(expected, actual);

        Assertions.assertEquals(expected.getAggregateId(), actual.getAggregateId());
        Assertions.assertEquals(expected.getBucketId(), actual.getBucketId());
        Assertions.assertEquals(expected.getEventType(), actual.getEventType());
        Assertions.assertEquals(expected.getTopic(), actual.getTopic());
        Assertions.assertEquals(expected.getPayload(), actual.getPayload());
    }
}
