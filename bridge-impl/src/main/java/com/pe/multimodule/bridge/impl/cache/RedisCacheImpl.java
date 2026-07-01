package com.pe.multimodule.bridge.impl.cache;

import com.pe.multimodule.bridge.api.cache.Cache;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.api.options.KeysScanParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RedisCacheImpl<Key, Value> implements Cache<Key, Value> {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheImpl.class);

    private final RedissonClient redissonClient;
    private final String name; // for example products
    private final long ttlInMinutes = 10; // It can be dinamically injected

    public RedisCacheImpl(String name, RedissonClient redissonClient) {
        this.name = name;
        this.redissonClient = redissonClient;
    }

    @Override
    public String getName() {
        return name;
    }

    // Helper method for generating a real key in Redis (for example, "products:uuid-123")
    private String resolveKey(Key key) {
        return name + ":" + key.toString();
    }

    private RBucket<Value> getBucket(Key key) {
        return redissonClient.getBucket(resolveKey(key));
    }

    @Override
    public boolean put(Key key, Value value) {
        return Boolean.TRUE.equals(execSafely(() -> {
            getBucket(key).set(value, Duration.of(ttlInMinutes, ChronoUnit.MINUTES));
            return true;
        }));
    }

    @Override
    public boolean remove(Key key) {
        return Boolean.TRUE.equals(execSafely(() -> getBucket(key).delete()));
    }

    @Override
    public Value removeAndGet(Key key) {
        return execSafely(() -> {
            RBucket<Value> bucket = getBucket(key);
            Value value = bucket.get();
            bucket.delete();
            return value;
        });
    }

    @Override
    public Value get(Key key) {
        return execSafely(() -> getBucket(key).get());
    }

    @Override
    public boolean contains(Key key) {
        return Boolean.TRUE.equals(execSafely(() -> getBucket(key).isExists()));
    }

    @Override
    public void clear() {
        execSafely(() -> {
            // Scan and delete only keys starting with the name
            redissonClient.getKeys().deleteByPattern(name + ":*");
            return null;
        });
    }

    /**
     * We're using the integrated RKeys iterator, it makes SCAN under the hood (non-blocking and without OutOfMemory)
     */
    @Override
    public List<Value> getAll() {
        return execSafely(() -> {
            final var values = new ArrayList<Value>();
            final var keysOptions = new KeysScanParams();
            keysOptions.pattern(name + ":*");
            final var keys = redissonClient.getKeys().getKeys(keysOptions);

            for (final var key : keys) {
                final RBucket<Value> bucket = redissonClient.getBucket(key);
                final Value val = bucket.get();
                if (val != null) {
                    values.add(val);
                }
            }

            return values;
        });
    }

    private <R> R execSafely(Supplier<R> callback) {
        try {
            return callback.get();
        } catch (Throwable th) {
            logger.error("Redis operation failed with : {}", th.toString());
            return null;
        }
    }
}
