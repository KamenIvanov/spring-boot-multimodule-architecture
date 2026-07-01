package com.pe.multimodule.it.env.docker;

public class ContainerConstants {

    public static final class MySQL9 {
        public static final String IMAGE_NAME = "docker.io/mysql";
        public static final String VERSION = "9.6.0";
        public static final int DB_PORT = 3306;

        private MySQL9() {
            // Constants
        }
    }

    public static final class Redis {
        public static final String IMAGE_NAME = "docker.io/redis";
        public static final String VERSION    = "8.8.0";
        public static final int    TCP_PORT   = 6379;

        private Redis() {
            // Constants
        }
    }

}