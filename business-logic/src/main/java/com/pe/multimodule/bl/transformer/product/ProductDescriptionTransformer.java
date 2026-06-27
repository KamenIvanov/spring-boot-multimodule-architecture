package com.pe.multimodule.bl.transformer.product;

import com.pe.multimodule.domain.product.Product;
import com.pe.multimodule.domain.transformer.AbstractTransformer;
import com.pe.multimodule.dto.product.ProductDescriptionDto;

public class ProductDescriptionTransformer extends AbstractTransformer<Product, ProductDescriptionDto> {

    public static final ProductDescriptionTransformer instance = new ProductDescriptionTransformer();

    private ProductDescriptionTransformer() {
        // Singleton
    }

    @Override
    public void copyToOutput(Product product, ProductDescriptionDto productDto) {
        super.copyToOutput(product, productDto);

        productDto.setSku(product.getSku());
    }

    @Override
    public ProductDescriptionDto createOutput(Product product) {
        if (product == null) {
            return null;
        }

        final var dto = new ProductDescriptionDto();
        copyToOutput(product, dto);
        return dto;
    }
}
