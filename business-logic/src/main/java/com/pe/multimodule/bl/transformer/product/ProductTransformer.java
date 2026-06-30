package com.pe.multimodule.bl.transformer.product;

import com.pe.multimodule.bl.transformer.AbstractNamedEntityBiTransformer;
import com.pe.multimodule.domain.product.Product;
import com.pe.multimodule.dto.product.ProductDto;

public class ProductTransformer extends AbstractNamedEntityBiTransformer<Product, ProductDto> {

    public static final ProductTransformer instance = new ProductTransformer();

    private ProductTransformer() {
        // Singleton
    }

    @Override
    public void copyToInput(ProductDto productDto, Product product) {
        super.copyToInput(productDto, product);

        product.setSku(productDto.getSku());
        product.setPrice(productDto.getPrice());
    }

    @Override
    public void copyToOutput(Product product, ProductDto productDto) {
        super.copyToOutput(product, productDto);

        productDto.setSku(product.getSku());
        productDto.setPrice(product.getPrice());
    }

    @Override
    public Product createInput(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        final var product = new Product();
        copyToInput(productDto, product);
        return product;
    }

    @Override
    public ProductDto createOutput(Product product) {
        if (product == null) {
            return null;
        }

        final var dto = new ProductDto();
        copyToOutput(product, dto);
        return dto;
    }
}
