package com.pe.multimodule.it.env.docker.impl;

public interface MultiModuleContainerConstants {

    interface MultiModule {
        String IMAGE_NAME = "multimodule-service";
        int DEBUG_PORT = 8787;
        int HTTP_PORT = 8080;
    }
}
