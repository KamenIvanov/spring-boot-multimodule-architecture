package com.pe.multimodule.spring.rest.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRestServiceImpl<ServiceWorker> {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractRestServiceImpl.class);

    private final ServiceWorker serviceWorker;

    protected AbstractRestServiceImpl(ServiceWorker serviceWorker) {
        this.serviceWorker = serviceWorker;
    }

    protected ServiceWorker getServiceWorker() {
        return serviceWorker;
    }
}
