package com.pe.multimodule.bridge.api.cache;

public interface CacheFactory {

    /**
     * Returns the cache associated with the given name.
     *
     * @param name    the cache name
     * @param <Key>   the cache's key type
     * @param <Value> the cache's value type
     * @return the cache associated with the given name
     */
    <Key, Value> Cache<Key, Value> cache(String name);

    /**
     * Returns the cache associated with the given name.
     *
     * @param name    the cache name
     * @param <Key>   the cache's key type
     * @param <Value> the cache's value type
     * @return the cache associated with the given name
     */
    <Key, Value> Cache<Key, Value> cache(String name, Class<Key> keyClass, Class<Value> valueClass);

    /**
     * Clears all registered caches in the factory.
     */
    void clearAll();

}
