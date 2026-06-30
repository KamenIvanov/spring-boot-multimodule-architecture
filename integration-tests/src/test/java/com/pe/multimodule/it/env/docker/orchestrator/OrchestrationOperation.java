package com.pe.multimodule.it.env.docker.orchestrator;

public interface OrchestrationOperation {

    String name();

    void execute();

    default void logInfo() {

    }
}
