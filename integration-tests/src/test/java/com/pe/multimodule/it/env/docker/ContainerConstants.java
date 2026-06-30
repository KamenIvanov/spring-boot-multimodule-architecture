package com.pe.multimodule.it.env.docker;

public class ContainerConstants {

    public interface MySQL9 {
        String IMAGE_NAME = "docker.io/mysql";
        String VERSION = "9.6.0";
        int DB_PORT = 3306;
    }
}