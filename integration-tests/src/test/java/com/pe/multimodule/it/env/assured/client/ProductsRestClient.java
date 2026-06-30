package com.pe.multimodule.it.env.assured.client;

import com.pe.multimodule.api.rest.RestUrl;
import com.pe.multimodule.dto.product.NewProductDto;
import com.pe.multimodule.dto.product.ProductDto;
import com.pe.multimodule.it.HeadersProvider;

public class ProductsRestClient extends AbstractCrudRestClient<NewProductDto, ProductDto> {

    public ProductsRestClient(HeadersProvider headerUtils, String endpointUrl) {
        super(headerUtils, endpointUrl + RestUrl.SECURED_PRODUCTS);
    }
}
