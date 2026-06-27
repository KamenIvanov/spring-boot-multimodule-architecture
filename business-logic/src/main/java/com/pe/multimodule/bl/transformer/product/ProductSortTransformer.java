package com.pe.multimodule.bl.transformer.product;

import com.pe.multimodule.dao.api.product.ProductSortByFields;
import com.pe.multimodule.domain.transformer.AbstractEnumTransformer;
import com.pe.multimodule.dto.product.ProductSortOptionDto;

public class ProductSortTransformer extends AbstractEnumTransformer<ProductSortByFields, ProductSortOptionDto> {

    public static final ProductSortTransformer instance = new ProductSortTransformer();

    private ProductSortTransformer() {
        super(ProductSortByFields.class, ProductSortOptionDto.class);
    }
}
