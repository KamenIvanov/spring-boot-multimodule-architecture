package com.pe.multimodule.dao.impl.transformer.product;

import com.pe.multimodule.dao.impl.product.ProductEntity;
import com.pe.multimodule.dao.impl.transformer.AbstractNamedEntityTransformer;
import com.pe.multimodule.domain.product.Product;

public class ProductTransformer extends AbstractNamedEntityTransformer<ProductEntity, Product> {

    public static final ProductTransformer instance = new ProductTransformer();

    private ProductTransformer() {
        // POJO
    }

    @Override
    public void copyToInput(Product dest, ProductEntity source) {
        super.copyToInput(dest, source);

        source.setSku(dest.getSku());
        source.setPrice(dest.getPrice());
    }

    @Override
    public void copyToOutput(ProductEntity source, Product dest) {
        super.copyToOutput(source, dest);

        dest.setSku(source.getSku());
        dest.setPrice(source.getPrice());
    }

    @Override
    public ProductEntity createInput(Product product) {
        if (product == null) {
            return null;
        }

        final var entity = new ProductEntity();
        copyToInput(product, entity);
        return entity;
    }

    @Override
    public Product createOutput(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        final var product = new Product();
        copyToOutput(entity, product);
        return product;
    }
}
