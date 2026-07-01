package com.pe.multimodule.spring.config.props;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@ConfigurationProperties(prefix = "app.redis")
@Validated
public record RedisProperties(
        @NotNull @NotBlank String host,
        @NotNull @NotBlank String port,
        @NotNull @Min(1) long defaultTtl,
        Map<String, Long> configs
) {
}