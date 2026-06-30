package com.pe.multimodule.it.env.docker;

import org.testcontainers.containers.GenericContainer;

public interface ContainerTemplate {

    GenericContainer<?> getGenericContainer();

    String getNetworkAlias();

    String getContainerIpAddress();

    Integer getMappedPort();

    void logInfo();

    String execInContainer(String... command);

    default void init() {

    }
}
