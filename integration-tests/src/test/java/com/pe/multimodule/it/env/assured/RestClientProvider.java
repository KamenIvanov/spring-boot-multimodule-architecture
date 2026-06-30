package com.pe.multimodule.it.env.assured;

import com.pe.multimodule.it.HeadersProvider;
import com.pe.multimodule.it.env.assured.client.ProductsFilterRestClient;
import com.pe.multimodule.it.env.assured.client.ProductsRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RestClientProvider {

    private static final Logger logger = LoggerFactory.getLogger(RestClientProvider.class);

    private final Map<Class<?>, Object> serviceRegistry = new HashMap<>();

    private final HeadersProvider headerProvider;

    public RestClientProvider(HeadersProvider headerProvider, String applicationUrl) {
        logger.error("Creating client for : {}", applicationUrl);
        this.headerProvider = headerProvider;

        serviceRegistry.put(ProductsFilterRestClient.class, new ProductsFilterRestClient(headerProvider, applicationUrl));

        // Secured
        serviceRegistry.put(ProductsRestClient.class, new ProductsRestClient(headerProvider, applicationUrl));
    }

    public ProductsFilterRestClient getProductsFilterRestClient() {
        return getService(ProductsFilterRestClient.class);
    }

    // Secured ones
    public ProductsRestClient getProductsRestClient() {
        return getService(ProductsRestClient.class);
    }

    public void setClientCredentials(UUID requesterId) {
        headerProvider.setRequesterHeader(requesterId);
    }

    public void clearCredentials() {
        headerProvider.clearCookies();
    }

    public <T> T getService(Class<T> serviceType) {
        return (T) serviceRegistry.get(serviceType);
    }
}
