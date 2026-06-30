package com.pe.multimodule.it.env.docker;

import org.slf4j.Logger;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Collection;

public class ContainerUtils {

    public ContainerUtils() {
        // static only
    }

    public static void run(Collection<ContainerTemplate> containers) {
        try {
            final var genericContainers = containers
                    .stream()
                    .map(ContainerTemplate::getGenericContainer)
                    .toList();
            Startables.deepStart(genericContainers).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String execInContainer(Logger logger, GenericContainer<?> container, String... command) {
        try {
            final Container.ExecResult result = container.execInContainer(command);
            if (result.getExitCode() != 0) {
                logger.error("Failed to execute command {}", String.join(" ", command));
                logger.error("Error stream: {}", result.getStderr());
                logger.error("Output stream: {}", result.getStdout());
            }

            return result.getStdout();
        } catch (Throwable th) {
            throw new RuntimeException("Failed to execute command " + String.join(" ", command));
        }
    }

    public static String createMysqlJdbcUrl(String host, int port, String dbName) {
        return String.format("jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true", host, port, dbName);
    }
}
