package com.pe.multimodule.it.env;

import com.pe.multimodule.it.HeadersProvider;
import com.pe.multimodule.it.env.assured.RestClientProvider;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DockerEnvironment extends BootstrapDockerSupport {

    private HeadersProvider headersProvider;
    private RestClientProvider restClientProvider;

    public DockerEnvironment(ExtensionContext ctx) {
        super(ctx);
    }

    public HeadersProvider getHeaderProvider() {
        if (headersProvider == null) {
            headersProvider = new HeadersProvider();
        }
        return headersProvider;
    }

    public RestClientProvider getRestClientProvider() {
        if (restClientProvider == null) {
            restClientProvider = new RestClientProvider(getHeaderProvider(), getBaseRestUri());
        }
        return restClientProvider;
    }
}
