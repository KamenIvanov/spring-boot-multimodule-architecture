package com.pe.multimodule.bl.service.secured;

import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.bl.transformer.SortDirectionTransformer;
import com.pe.multimodule.bl.transformer.product.NewProductTransformer;
import com.pe.multimodule.bl.transformer.product.ProductSortTransformer;
import com.pe.multimodule.bl.transformer.product.ProductTransformer;
import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.dao.api.product.ProductSearchQuery;
import com.pe.multimodule.domain.exceptions.AuthorizationException;
import com.pe.multimodule.domain.product.Product;
import com.pe.multimodule.domain.transformer.BiTransformer;
import com.pe.multimodule.domain.transformer.Transformer;
import com.pe.multimodule.dto.SortDirectionDto;
import com.pe.multimodule.dto.product.NewProductDto;
import com.pe.multimodule.dto.product.ProductDto;
import com.pe.multimodule.dto.product.ProductSortOptionDto;
import com.pe.multimodule.dto.product.ProductsDto;

import java.util.UUID;

public class ProductsServiceImpl extends AbstractCrudService<Product, NewProductDto, ProductDto, ProductDao> implements ProductsRestService {

    public ProductsServiceImpl(ProductDao dao) {
        super(dao);
    }

    @Override
    public ProductsDto getProducts(int page, int size, ProductSortOptionDto sort, SortDirectionDto direction, UUID requesterId) {
        final var query = new ProductSearchQuery();
        query.setPage(page);
        query.setSize(size);
        query.setSortBy(ProductSortTransformer.instance.createInput(sort));
        query.setSortDirection(SortDirectionTransformer.instance.createInput(direction));

        final var result = getDao().search(query);
        final var dtos = getTransformer().createOutput(result.elements());
        return new ProductsDto(result.totalPages(), result.totalHits(), dtos);
    }

    @Override
    protected void preProcessNewEntity(Product product, UUID requesterId) {
        product.setCreatedById(requesterId);
    }

    @Override
    protected Transformer<NewProductDto, Product> getCreateTransformer() {
        return NewProductTransformer.instance;
    }

    @Override
    protected BiTransformer<Product, ProductDto> getTransformer() {
        return ProductTransformer.instance;
    }

    @Override
    protected void authorize(Product domain, UUID requesterId) {
        if (!domain.getCreatedById().equals(requesterId)) {
            throw new AuthorizationException(UNAUTHORIZED);
        }
    }
}
