package com.pe.multimodule.spring.config;

import com.pe.multimodule.api.JsonMapperSingleton;
import com.pe.multimodule.bl.service.cache.CacheRegistry;
import com.pe.multimodule.bridge.api.cache.CacheFactory;
import com.pe.multimodule.bridge.impl.cache.RedisCacheFactory;
import com.pe.multimodule.bridge.impl.cache.config.RedisConfiguration;
import com.pe.multimodule.spring.config.props.RedisProperties;
import org.springframework.context.annotation.Bean;

public class BridgeConfiguration {

    @Bean
    public CacheFactory cacheFactory(RedisProperties redisProperties) {
        final var config = new RedisConfiguration(
                redisProperties.host(),
                redisProperties.port(),
                redisProperties.defaultTtl(),
                redisProperties.configs()
        );
        return new RedisCacheFactory(config, JsonMapperSingleton.instance.getJsonMapper());
    }

    @Bean
    public CacheRegistry cacheRegistry(CacheFactory cacheFactory) {
        return new CacheRegistry(cacheFactory);
    }
}
