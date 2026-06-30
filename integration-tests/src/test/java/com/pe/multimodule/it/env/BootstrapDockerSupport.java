package com.pe.multimodule.it.env;

import com.pe.multimodule.it.env.docker.impl.MultiModuleServiceContainer;
import com.pe.multimodule.it.env.docker.impl.Mysql9Container;
import com.pe.multimodule.it.env.docker.orchestrator.ContainerOrchestrator;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.Network;

public abstract class BootstrapDockerSupport extends AbstractDockerTestEnvironment {

    public static final  String MTS_NAME = "multimodule-integrations";

    protected final Mysql9Container mysqlContainer;
    protected final MultiModuleServiceContainer multiModuleServiceContainer;

    public BootstrapDockerSupport(ExtensionContext ctx) {
        super(ctx);

        final var network = Network.builder().build();

        mysqlContainer = new Mysql9Container("multi_module", "mm-user", "mm-pwd", network);
        multiModuleServiceContainer = new MultiModuleServiceContainer(serviceVersion, network, mysqlContainer, MTS_NAME);
    }

    public void bootstrap() {
        new ContainerOrchestrator()
                .add(mysqlContainer)
                .add(multiModuleServiceContainer)
                .run();
    }

    public String getBaseRestUri() {
        return String.format("http://%s:%d/api", multiModuleServiceContainer.getContainerIpAddress(), multiModuleServiceContainer.getMappedPort());
    }
}
