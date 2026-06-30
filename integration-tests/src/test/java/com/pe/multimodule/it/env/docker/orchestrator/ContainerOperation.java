package com.pe.multimodule.it.env.docker.orchestrator;

import com.pe.multimodule.it.env.docker.ContainerTemplate;
import com.pe.multimodule.it.env.docker.ContainerUtils;

import java.util.List;

public class ContainerOperation implements OrchestrationOperation {

    private final String            name;
    private final List<ContainerTemplate> containers;

    public ContainerOperation(ContainerTemplate... containers) {
        this(List.of(containers));
    }

    public ContainerOperation(List<ContainerTemplate> containers) {
        final var containerAliases = String.join(",", containers.stream().map(ContainerTemplate::getNetworkAlias).toList());
        this.name = "Docker Containers: [%s]".formatted(containerAliases);
        this.containers = containers;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void execute() {
        ContainerUtils.run(containers);
    }

    @Override
    public void logInfo() {
        containers.forEach(ContainerTemplate::logInfo);
    }
}
