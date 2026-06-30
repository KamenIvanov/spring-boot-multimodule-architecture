package com.pe.multimodule.it.env.assured.client;

import com.pe.multimodule.api.rest.RestUrl;
import com.pe.multimodule.it.HeadersProvider;
import com.pe.multimodule.it.env.assured.AbstractRestClient;

public class ProductsFilterRestClient extends AbstractRestClient {

    public ProductsFilterRestClient(HeadersProvider headerUtils, String endpointUrl) {
        super(headerUtils, endpointUrl + RestUrl.PRODUCTS);
    }
}
