package com.pe.multimodule.bl.service.secured;

import com.pe.multimodule.api.JsonSerializer;
import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.bl.service.DomainEventPublisher;
import com.pe.multimodule.bl.transformer.SortDirectionTransformer;
import com.pe.multimodule.bl.transformer.product.NewProductTransformer;
import com.pe.multimodule.bl.transformer.product.ProductSortTransformer;
import com.pe.multimodule.bl.transformer.product.ProductTransformer;
import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.dao.api.product.ProductSearchQuery;
import com.pe.multimodule.domain.exceptions.AuthorizationException;
import com.pe.multimodule.domain.outbox.OutboxEvent;
import com.pe.multimodule.domain.product.Product;
import com.pe.multimodule.domain.transformer.BiTransformer;
import com.pe.multimodule.domain.transformer.Transformer;
import com.pe.multimodule.dto.SortDirectionDto;
import com.pe.multimodule.dto.product.NewProductDto;
import com.pe.multimodule.dto.product.ProductDto;
import com.pe.multimodule.dto.product.ProductSortOptionDto;
import com.pe.multimodule.dto.product.ProductsDto;
import com.pe.multimodule.events.TopicNames;
import com.pe.multimodule.events.products.AbstractProductEventDto;

import java.util.UUID;

public class ProductsServiceImpl extends AbstractCrudService<Product, NewProductDto, ProductDto, ProductDao> implements ProductsRestService {

    private final DomainEventPublisher domainEventPublisher;

    public ProductsServiceImpl(ProductDao dao, DomainEventPublisher domainEventPublisher) {
        super(dao);
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public ProductDto create(NewProductDto entityVo, UUID requesterId) {
        final var product = super.create(entityVo, requesterId);

        final var createEvent = ProductEventFactory.productCreatedEvent(product, requesterId);
        domainEventPublisher.publish(createEvent);

        return product;
    }

    @Override
    public ProductDto update(ProductDto dto, UUID requesterId) {
        final var product = super.update(dto, requesterId);

        final var updateEvent = ProductEventFactory.productUpdatedEvent(product, requesterId);
        domainEventPublisher.publish(updateEvent);

        return product;
    }

    @Override
    public void delete(UUID id, UUID requesterId) {
        super.delete(id, requesterId);

        final var deleteEvent = ProductEventFactory.productDeletedEvent(id, requesterId);
        domainEventPublisher.publish(deleteEvent);
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
