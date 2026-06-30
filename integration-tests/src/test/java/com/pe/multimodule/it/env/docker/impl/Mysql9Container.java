package com.pe.multimodule.it.env.docker.impl;

import com.pe.multimodule.it.env.docker.ContainerConstants;
import org.testcontainers.containers.Network;

public class Mysql9Container extends AbstractContainer {

    private static final String ROOT_PASSWORD = "admin";

    public Mysql9Container(String dbName, String user, String userPassword, Network network) {
        this(dbName, user, userPassword, "mysql9", network);
    }

    public Mysql9Container(String dbName, String user, String userPassword, String networkAlias, Network network) {
        super(ContainerConstants.MySQL9.VERSION, networkAlias, network);
        this.container = createBasicContainer(ContainerConstants.MySQL9.IMAGE_NAME, containerVersion, network, false)
                .withEnv("MYSQL_ROOT_PASSWORD", ROOT_PASSWORD)
                .withEnv("MYSQL_DATABASE", dbName)
                .withEnv("MYSQL_USER", user)
                .withEnv("MYSQL_PASSWORD", userPassword)
                .withExposedPorts(ContainerConstants.MySQL9.DB_PORT);
    }

    @Override
    public Integer getMappedPort() {
        return container.getMappedPort(ContainerConstants.MySQL9.DB_PORT);
    }

    public String getDbName() {
        return getEnvValue("MYSQL_DATABASE");
    }

    public String getUser() {
        return getEnvValue("MYSQL_USER");
    }

    public String getUserPassword() {
        return getEnvValue("MYSQL_PASSWORD");
    }

}
