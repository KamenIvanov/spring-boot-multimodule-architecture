package com.pe.multimodule.bl.service.pub;

import com.pe.multimodule.api.rest.pub.ProductsFilteringRestService;
import com.pe.multimodule.bl.service.AbstractService;
import com.pe.multimodule.bl.transformer.SortDirectionTransformer;
import com.pe.multimodule.bl.transformer.product.ProductDescriptionTransformer;
import com.pe.multimodule.bl.transformer.product.ProductSortTransformer;
import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.dao.api.product.ProductSearchQuery;
import com.pe.multimodule.dto.SortDirectionDto;
import com.pe.multimodule.dto.product.ProductDescriptionDto;
import com.pe.multimodule.dto.product.ProductDescriptionsDto;
import com.pe.multimodule.dto.product.ProductSortOptionDto;

import java.util.UUID;

public class ProductsFilteringRestServiceImpl extends AbstractService implements ProductsFilteringRestService {

    private final ProductDao productDao;

    public ProductsFilteringRestServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ProductDescriptionsDto getProducts(int page, int size, ProductSortOptionDto sort, SortDirectionDto direction) {
        final var query = new ProductSearchQuery();
        query.setPage(page);
        query.setSize(size);
        query.setSortBy(ProductSortTransformer.instance.createInput(sort));
        query.setSortDirection(SortDirectionTransformer.instance.createInput(direction));

        final var result = productDao.search(query);
        final var dtos = ProductDescriptionTransformer.instance.createOutput(result.elements());
        return new ProductDescriptionsDto(result.totalPages(), result.totalHits(), dtos);
    }

    @Override
    public ProductDescriptionDto getProduct(UUID id) {
        final var product = productDao.loadById(id);
        return ProductDescriptionTransformer.instance.createOutput(product);
    }
}
