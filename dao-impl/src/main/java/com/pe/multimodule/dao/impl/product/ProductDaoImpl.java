package com.pe.multimodule.dao.impl.product;

import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.dao.api.product.ProductSearchQuery;
import com.pe.multimodule.dao.impl.SearchableDaoImpl;
import com.pe.multimodule.dao.impl.transformer.product.ProductTransformer;
import com.pe.multimodule.domain.product.Product;
import jakarta.validation.Validator;

import java.util.UUID;

public class ProductDaoImpl extends SearchableDaoImpl<
        UUID,
        Product,
        ProductEntity,
        ProductRepository,
        ProductSearchQuery
        > implements ProductDao {

    public ProductDaoImpl(ProductRepository repository, Validator validator) {
        super(repository, ProductTransformer.instance, validator);
    }

}
