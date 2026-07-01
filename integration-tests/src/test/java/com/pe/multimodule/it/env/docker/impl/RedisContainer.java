package com.pe.multimodule.it.env.docker.impl;

import com.pe.multimodule.it.env.docker.ContainerConstants;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;

public class RedisContainer extends AbstractContainer {

    public RedisContainer(Network network) {
        super(ContainerConstants.Redis.VERSION, "redis", network);
        container = createBasicContainer(ContainerConstants.Redis.IMAGE_NAME, containerVersion, network, false)
                .withExposedPorts(ContainerConstants.Redis.TCP_PORT)
                .waitingFor(Wait.forLogMessage(".*Ready to accept connections.*\\n", 1));
    }

    @Override
    public Integer getMappedPort() {
        return container.getMappedPort(ContainerConstants.Redis.TCP_PORT);
    }
}
