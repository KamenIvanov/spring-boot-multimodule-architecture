package com.pe.multimodule.it.env.docker.orchestrator;

import com.pe.multimodule.it.env.docker.ContainerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ContainerOrchestrator {

    private static final Logger logger = LoggerFactory.getLogger(ContainerOrchestrator.class);
    private final List<OrchestrationOperation> operations = new LinkedList<>();
    private       boolean                      finished   = false;

    public ContainerOrchestrator add(ContainerTemplate container) {
        return add(new ContainerOperation(container));
    }

    public ContainerOrchestrator add(ContainerTemplate... containers) {
        this.operations.addAll(Arrays.stream(containers).map(ContainerOperation::new).toList());
        return this;
    }

    public ContainerOrchestrator add(OrchestrationOperation operation) {
        this.operations.add(operation);
        return this;
    }

    public ContainerOrchestrator add(OrchestrationOperation... operations) {
        add(List.of(operations));
        return this;
    }

    public ContainerOrchestrator add(Collection<OrchestrationOperation> operations) {
        this.operations.addAll(operations);
        return this;
    }

    public void run() {
        if (finished) {
            throw new IllegalArgumentException("Orchestration already ran!");
        }

        for (final var operation : operations) {
            try {
                logger.info("----> Operation {} is starting...", operation.name());
                operation.execute();
                logger.info("----> Operation {} was successful.", operation.name());
            } catch (Exception e) {
                logger.error("----> Operation {} failed.", operation.name());
                break;
            }
        }

        logger.info("====== Containers ======");
        operations.forEach(OrchestrationOperation::logInfo);

        finished = true;
    }
}
