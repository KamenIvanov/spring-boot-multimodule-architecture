package com.pe.multimodule.it.env.docker.impl;

import com.pe.multimodule.it.env.docker.ContainerConstants;
import com.pe.multimodule.it.env.docker.ContainerUtils;
import org.testcontainers.containers.Network;

public class MultiModuleServiceContainer extends AbstractContainer {

    public MultiModuleServiceContainer(
            String containerVersion,
            Network network,
            Mysql9Container mysqlContainer,
            RedisContainer redisContainer
    ) {
        super(containerVersion, "multi-module-service", network);
        container = createBasicContainer(MultiModuleContainerConstants.MultiModule.IMAGE_NAME, containerVersion, network, false)
                .withEnv("DEBUG", "false")
                .withEnv("DB_URL", ContainerUtils.createMysqlJdbcUrl(mysqlContainer.getNetworkAlias(), ContainerConstants.MySQL9.DB_PORT, mysqlContainer.getDbName()))
                .withEnv("DB_USER", mysqlContainer.getUser())
                .withEnv("DB_PASSWORD", mysqlContainer.getUserPassword())
                .withEnv("REDIS_HOST", redisContainer.getNetworkAlias())
                .withEnv("REDIS_PORT", String.valueOf(ContainerConstants.Redis.TCP_PORT))
                .withExposedPorts(MultiModuleContainerConstants.MultiModule.HTTP_PORT);
    }

    @Override
    public Integer getMappedPort() {
        return container.getMappedPort(MultiModuleContainerConstants.MultiModule.HTTP_PORT);
    }
}
