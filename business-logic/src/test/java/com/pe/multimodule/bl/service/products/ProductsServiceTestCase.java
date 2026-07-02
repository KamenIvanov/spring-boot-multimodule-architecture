package com.pe.multimodule.bl.service.products;

import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.bl.service.AbstractCrudServiceTestCase;
import com.pe.multimodule.bl.service.DomainEventPublisher;
import com.pe.multimodule.bl.service.secured.ProductsServiceImpl;
import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.domain.product.Product;
import com.pe.multimodule.dto.product.NewProductDto;
import com.pe.multimodule.dto.product.ProductDto;
import com.pe.multimodule.events.products.ProductCreatedEventDto;
import com.pe.multimodule.events.products.ProductDeletedEventDto;
import com.pe.multimodule.events.products.ProductUpdatedEventDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ProductsServiceTestCase extends AbstractCrudServiceTestCase<Product, NewProductDto, ProductDto, ProductDao, ProductsRestService> {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductsServiceImpl productsService;

    @Mock
    private DomainEventPublisher eventPublisher;

    @Test
    void createWhenValidDataShouldSaveAndReturnDto() {
        final NewProductDto newDto = createValidNewEntityDto();
        final Product savedProduct = new Product();
        savedProduct.setId(UUID.randomUUID());
        savedProduct.setName(newDto.getName());

        when(productDao.save(any(Product.class))).thenReturn(savedProduct);
        productsService.create(newDto, requesterId);
        verify(productDao, times(1)).save(any(Product.class));
    }

    @Test
    void createWhenValidDataShouldSaveAndPublishEvent() {
        final NewProductDto newDto = createValidNewEntityDto();
        final Product savedProduct = new Product();
        savedProduct.setId(UUID.randomUUID());
        savedProduct.setName(newDto.getName());

        when(productDao.save(any(Product.class))).thenReturn(savedProduct);

        productsService.create(newDto, requesterId);

        verify(productDao, times(1)).save(any(Product.class));

        final var eventCaptor = ArgumentCaptor.forClass(ProductCreatedEventDto.class);
        verify(eventPublisher, times(1)).publish(eventCaptor.capture());

        final ProductCreatedEventDto publishedEvent = eventCaptor.getValue();
        Assertions.assertEquals(savedProduct.getId(), publishedEvent.getProductId());
    }

    @Test
    void updateWhenValidDataShouldSaveAndPublishUpdateEvent() {
        final ProductDto updateDto = createValidDto();
        final Product updatedProduct = new Product();
        updatedProduct.setId(updateDto.getId());
        updatedProduct.setName("Updated LEGO");
        updatedProduct.setCreatedById(requesterId);
        when(getMockDao().loadById(updateDto.getId())).thenReturn(updatedProduct);

        when(productDao.save(any(Product.class))).thenReturn(updatedProduct);

        productsService.update(updateDto, requesterId);

        verify(productDao, times(1)).save(any(Product.class));

        final var eventCaptor = ArgumentCaptor.forClass(ProductUpdatedEventDto.class);
        verify(eventPublisher, times(1)).publish(eventCaptor.capture());

        final ProductUpdatedEventDto publishedEvent = eventCaptor.getValue();
        Assertions.assertEquals(updatedProduct.getId(), publishedEvent.getProductId());
    }

    @Test
    void deleteShouldRemoveFromDaoAndPublishDeleteEvent() {
        final Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setCreatedById(requesterId);
        when(getMockDao().loadById(product.getId())).thenReturn(product);
        doNothing().when(productDao).delete(product);

        productsService.delete(product.getId(), requesterId);

        verify(productDao, times(1)).delete(product);

        final var eventCaptor = ArgumentCaptor.forClass(ProductDeletedEventDto.class);
        verify(eventPublisher, times(1)).publish(eventCaptor.capture());

        final ProductDeletedEventDto publishedEvent = eventCaptor.getValue();
        Assertions.assertEquals(product.getId(), publishedEvent.getProductId());
    }

    @Override
    protected ProductsRestService getService() {
        return productsService;
    }

    @Override
    protected ProductDao getMockDao() {
        return productDao;
    }

    @Override
    protected NewProductDto createValidNewEntityDto() {
        final var dto = new NewProductDto();
        dto.setName("LEGO Star Wars");
        dto.setSku("LEGO-75355");
        dto.setPrice(BigDecimal.valueOf(239.99));
        return dto;
    }

    @Override
    protected ProductDto createValidDto() {
        ProductDto dto = new ProductDto();
        dto.setId(UUID.randomUUID());
        dto.setName("LEGO Star Wars");
        dto.setSku("LEGO-75355");
        dto.setPrice(BigDecimal.valueOf(239.99));
        return dto;
    }
}
