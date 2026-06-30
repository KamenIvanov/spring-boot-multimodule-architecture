package com.pe.multimodule.it.env.docker.impl;

import com.pe.multimodule.it.env.docker.ContainerTemplate;
import com.pe.multimodule.it.env.docker.ContainerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractContainer implements ContainerTemplate {

    private static final Logger logger = LoggerFactory.getLogger(AbstractContainer.class);

    protected final String  containerVersion;
    protected final String  networkAlias;
    protected final Network network;

    protected GenericContainer<?> container;

    public AbstractContainer(String containerVersion, String networkAlias, Network network) {
        this.containerVersion = containerVersion;
        this.networkAlias = networkAlias;
        this.network = network;
    }

    @Override
    public GenericContainer<?> getGenericContainer() {
        return container;
    }

    @Override
    public String getNetworkAlias() {
        return networkAlias;
    }

    @Override
    public String getContainerIpAddress() {
        return container.getHost();
    }

    @Override
    public String execInContainer(String... command) {
        return ContainerUtils.execInContainer(logger, container, command);
    }

    @Override
    public void logInfo() {
        logger.info("\t{}:", getNetworkAlias());
        logNetworkInfo("\t\t");
    }

    private void logNetworkInfo(String offset) {
        List<Integer> exposedPorts = new ArrayList<>(container.getExposedPorts());
        exposedPorts.sort(Comparator.naturalOrder());

        logger.info("{}IP: {}", offset, getContainerIpAddress());
        if (!exposedPorts.isEmpty()) {
            logger.info("{}Ports:", offset);
        }

        offset += "\t";
        for (Integer port : exposedPorts) {
            Integer mappedPort = container.getMappedPort(port);
            logger.info("{}{} -> {}", offset, port, Objects.toString(mappedPort, "NONE"));
        }
    }

    protected String getEnvValue(String name) {
        return container.getEnvMap().get(name);
    }

    protected Consumer<OutputFrame> createLogConsumer(String loggerName) {
        return new Slf4jLogConsumer(LoggerFactory.getLogger(loggerName)).withSeparateOutputStreams();
    }

    protected GenericContainer<?> createBasicContainer(String imageName, String version, Network network, boolean pullImage) {
        return new GenericContainer<>(imageName + ":" + version)
                .withNetworkAliases(networkAlias)
                .withLogConsumer(createLogConsumer("containers." + networkAlias))
                .withNetwork(network)
                .withImagePullPolicy(new ImagePullPolicy(networkAlias, pullImage));
    }

}
