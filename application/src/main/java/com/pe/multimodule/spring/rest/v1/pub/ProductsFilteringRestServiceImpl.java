package com.pe.multimodule.spring.rest.v1.pub;

import com.pe.multimodule.api.rest.RestUrl;
import com.pe.multimodule.api.rest.pub.ProductsFilteringRestService;
import com.pe.multimodule.dto.SortDirectionDto;
import com.pe.multimodule.dto.product.ProductDescriptionDto;
import com.pe.multimodule.dto.product.ProductDescriptionsDto;
import com.pe.multimodule.dto.product.ProductSortOptionDto;
import com.pe.multimodule.spring.rest.v1.AbstractRestServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(
        value = RestUrl.PRODUCTS,
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = "Accept=application/json"
)
public class ProductsFilteringRestServiceImpl extends AbstractRestServiceImpl<ProductsFilteringRestService> implements ProductsFilteringRestService {

    public ProductsFilteringRestServiceImpl(ProductsFilteringRestService productsFilteringService) {
        super(productsFilteringService);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Override
    public ProductDescriptionsDto getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") ProductSortOptionDto sort,
            @RequestParam("direction") SortDirectionDto direction
    ) {
        return getServiceWorker().getProducts(page, size, sort, direction);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id:[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}}")
    @Override
    public ProductDescriptionDto getProduct(@PathVariable("id") UUID id) {
        return getServiceWorker().getProduct(id);
    }
}
