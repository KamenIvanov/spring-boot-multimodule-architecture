package com.pe.multimodule.it.env;

import org.junit.jupiter.api.extension.ExtensionContext;

public abstract class AbstractDockerTestEnvironment {

    public static final String CONTAINER_VERSION = "junit.extension.containers_version";

    protected final String serviceVersion;

    public AbstractDockerTestEnvironment(ExtensionContext ctx) {
        serviceVersion = ctx.getConfigurationParameter(CONTAINER_VERSION).orElse("latest");
    }
}
