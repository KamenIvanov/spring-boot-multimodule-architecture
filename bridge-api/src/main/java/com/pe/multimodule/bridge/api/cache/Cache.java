package com.pe.multimodule.bridge.api.cache;

import java.util.List;

public interface Cache<Key, Value> {

    String getName();

    /**
     * Puts the given value into the cache.
     *
     * @param key   the unique identifier to identify the pit value
     * @param value the value being cached
     * @return true if the value is put successfully, false otherwise
     */
    boolean put(Key key, Value value);

    /**
     * Removes the value identified by the given key.
     *
     * @param key the value's key
     * @return true if the value is removed successfully, false otherwise
     */
    boolean remove(Key key);

    /**
     * Removes and returns the associated value with the given key, if no value is associated with it, null is
     * returned.
     *
     * @param key the value's key
     * @return the associated value of the given key, or null
     */
    Value removeAndGet(Key key);

    /**
     * Returns the associated value of the given key.
     *
     * @param key the value's key
     * @return the associated value of the given key, or null
     */
    Value get(Key key);

    /**
     * Whether the cache has an element associated with the given key or no.
     *
     * @param key the key
     * @return true if the cache contains an element associated with the given key
     */
    boolean contains(Key key);

    /**
     * @return all values contained in the cache
     */
    List<Value> getAll();

    /**
     * Removes all entities from the cache.
     */
    void clear();

}
