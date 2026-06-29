package com.pe.multimodule.domain.asserter;

public interface DeepEqualsAsserter<T> {
    void assertDeepEquals(T expected, T actual);
}
