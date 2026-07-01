package com.pe.multimodule.bridge.impl.cache.config;

import java.util.Map;

public record RedisConfiguration(String host, String port, long defaultTtl, Map<String, Long> configs) {
}
