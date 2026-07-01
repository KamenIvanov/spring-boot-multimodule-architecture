package com.pe.multimodule.it.rest.secured.products;

import com.pe.multimodule.domain.asserter.DeepEqualsAsserter;
import com.pe.multimodule.dto.product.NewProductDto;
import com.pe.multimodule.dto.product.ProductDto;
import com.pe.multimodule.it.env.assured.client.AbstractCrudRestClient;
import com.pe.multimodule.it.rest.secured.AbstractCrudIntegrationTestCase;

import java.math.BigDecimal;
import java.util.UUID;

class ProductsRestServiceIntegrationTestCase extends AbstractCrudIntegrationTestCase<NewProductDto, ProductDto> {

    @Override
    protected AbstractCrudRestClient<NewProductDto, ProductDto> getCrudClient() {
        return getProductsRestClient();
    }

    @Override
    protected NewProductDto createEntityDto() {
        final var newProduct = new NewProductDto();
        newProduct.setName("Simple name");
        newProduct.setPrice(BigDecimal.TWO);
        newProduct.setSku(UUID.randomUUID().toString());
        return newProduct;
    }

    @Override
    protected NewProductDto createInvalidEntityDto() {
        final var newProduct = new NewProductDto();
        newProduct.setName(null);
        newProduct.setPrice(BigDecimal.TWO);
        newProduct.setSku(UUID.randomUUID().toString());
        return newProduct;
    }

    @Override
    protected void updateEntityVo(ProductDto productDto) {
        productDto.setPrice(BigDecimal.valueOf(2.28));
    }

    @Override
    protected DeepEqualsAsserter<ProductDto> getAsserter() {
        return ProductDeepEqualsAsserter.instance;
    }
}
