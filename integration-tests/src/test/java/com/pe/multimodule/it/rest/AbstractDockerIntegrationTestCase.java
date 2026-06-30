package com.pe.multimodule.it.rest;

import com.pe.multimodule.it.env.RestIntegrationTest;
import com.pe.multimodule.it.env.assured.RestClientProvider;
import com.pe.multimodule.it.env.assured.client.ProductsFilterRestClient;
import com.pe.multimodule.it.env.assured.client.ProductsRestClient;
import com.pe.multimodule.it.env.injectors.RestClientInjector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@RestIntegrationTest
public class AbstractDockerIntegrationTestCase {

    @RestClientInjector
    private RestClientProvider restClientProvider;

    @Test
    void testInjectedFields() {
        Assertions.assertNotNull(restClientProvider);
    }

    protected ProductsFilterRestClient getProductsFilterRestClient() {
        return restClientProvider.getProductsFilterRestClient();
    }

    protected ProductsRestClient getProductsRestClient() {
        return restClientProvider.getProductsRestClient();
    }

    protected void setRequesterId(UUID accountId) {
        restClientProvider.setClientCredentials(accountId);
    }

    protected void clearCredentials() {
        restClientProvider.clearCredentials();
    }
}
