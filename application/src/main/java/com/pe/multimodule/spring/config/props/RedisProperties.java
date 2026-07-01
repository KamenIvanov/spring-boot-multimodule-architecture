package com.pe.multimodule.spring.config.props;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.redis")
@Validated
public record RedisProperties(
        @NotNull @NotBlank String host,
        @NotNull @NotBlank String port
) {
}