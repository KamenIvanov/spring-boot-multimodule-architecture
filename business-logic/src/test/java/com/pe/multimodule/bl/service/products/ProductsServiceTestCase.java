package com.pe.multimodule.bl.service.products;

import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.bl.service.AbstractCrudServiceTestCase;
import com.pe.multimodule.bl.service.secured.ProductsServiceImpl;
import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.domain.product.Product;
import com.pe.multimodule.dto.product.NewProductDto;
import com.pe.multimodule.dto.product.ProductDto;
import org.junit.jupiter.api.Test;
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

    @Test
    void createWhenValidDataShouldSaveAndReturnDto() {
        final NewProductDto newDto = createValidNewEntityDto();
        productsService.create(newDto, requesterId);
        verify(productDao, times(1)).save(any(Product.class));
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
