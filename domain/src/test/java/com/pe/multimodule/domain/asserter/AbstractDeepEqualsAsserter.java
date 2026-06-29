package com.pe.multimodule.domain.asserter;

import com.pe.multimodule.domain.AbstractDomain;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractDeepEqualsAsserter<Domain extends AbstractDomain<UUID>> implements DeepEqualsAsserter<Domain> {

    @Override
    public void assertDeepEquals(Domain expected, Domain actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCreatedAt().truncatedTo(ChronoUnit.MILLIS), actual.getCreatedAt());
        assertEquals(expected.getCreatedById(), actual.getCreatedById());
        assertEquals(expected.getUpdatedAt().truncatedTo(ChronoUnit.MILLIS), actual.getUpdatedAt());
    }
}

