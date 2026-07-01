package com.pe.multimodule.bridge.impl.cache;

import com.pe.multimodule.bridge.api.cache.Cache;
import com.pe.multimodule.bridge.api.cache.CacheFactory;
import com.pe.multimodule.bridge.impl.cache.config.RedisConfiguration;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJackson3Codec;
import org.redisson.config.Config;
import tools.jackson.databind.json.JsonMapper;

public class RedisCacheFactory implements CacheFactory {

    private final RedisConfiguration redisConfig;
    private final JsonMapper jsonMapper;

    private RedissonClient jedisPooled;

    public RedisCacheFactory(RedisConfiguration redisConfig, JsonMapper jsonMapper) {
        this.redisConfig = redisConfig;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public <Key, Value> Cache<Key, Value> cache(String name) {
        init();

        try {
            return new RedisCacheImpl<>(name, jedisPooled);
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public <Key, Value> Cache<Key, Value> cache(String name, Class<Key> keyClass, Class<Value> valueClass) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public void clearAll() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    private void init() {
        if (jedisPooled != null) {
            return;
        }

        final var config = redisConfig();
        jedisPooled = Redisson.create(config);
    }

    public Config redisConfig() {
        final var config = new Config();
        config.useSingleServer().setAddress("redis://" + redisConfig.host() + ":" + redisConfig.port());
        config.setCodec(new JsonJackson3Codec(jsonMapper));
        return config;
    }
}