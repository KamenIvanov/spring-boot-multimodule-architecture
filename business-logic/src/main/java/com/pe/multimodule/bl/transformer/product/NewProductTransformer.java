package com.pe.multimodule.bl.transformer.product;

import com.pe.multimodule.domain.product.Product;
import com.pe.multimodule.domain.transformer.AbstractTransformer;
import com.pe.multimodule.dto.product.NewProductDto;

public class NewProductTransformer extends AbstractTransformer<NewProductDto, Product> {

    public static final NewProductTransformer instance = new NewProductTransformer();

    private NewProductTransformer() {
        // Singleton
    }

    @Override
    public void copyToOutput(NewProductDto dto, Product product) {
        super.copyToOutput(dto, product);

        dto.setSku(product.getSku());
        dto.setPrice(product.getPrice());
    }

    @Override
    public Product createOutput(NewProductDto product) {
        if (product == null) {
            return null;
        }

        final var dto = new Product();
        copyToOutput(product, dto);
        return dto;
    }
}
