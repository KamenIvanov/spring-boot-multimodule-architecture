package com.pe.multimodule.it.env.docker.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.images.AbstractImagePullPolicy;
import org.testcontainers.images.ImageData;
import org.testcontainers.utility.DockerImageName;

public class ImagePullPolicy extends AbstractImagePullPolicy {

    private static final Logger logger = LoggerFactory.getLogger(ImagePullPolicy.class);

    private final String  networkAlias;
    private final boolean pullImage;

    public ImagePullPolicy(String networkAlias, boolean pullImage) {
        this.networkAlias = networkAlias;
        this.pullImage = pullImage;
    }

    @Override
    protected boolean shouldPullCached(DockerImageName dockerImageName, ImageData imageData) {
        logger.info("Pulling image with network alias {}: {}", networkAlias, pullImage ? "Yes" : "No");
        return pullImage;
    }
}