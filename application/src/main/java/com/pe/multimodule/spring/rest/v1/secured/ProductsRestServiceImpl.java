package com.pe.multimodule.spring.rest.v1.secured;

import com.pe.multimodule.api.rest.RestUrl;
import com.pe.multimodule.api.rest.secured.ProductsRestService;
import com.pe.multimodule.dto.SortDirectionDto;
import com.pe.multimodule.dto.product.NewProductDto;
import com.pe.multimodule.dto.product.ProductDto;
import com.pe.multimodule.dto.product.ProductSortOptionDto;
import com.pe.multimodule.dto.product.ProductsDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.pe.multimodule.api.rest.HeaderConstants.REQUESTER_ID;

@RestController
@RequestMapping(
        value = RestUrl.SECURED_PRODUCTS,
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = "Accept=application/json"
)
public class ProductsRestServiceImpl extends AbstractCrudRestServiceImpl<NewProductDto, ProductDto, ProductsRestService> implements ProductsRestService {

    public ProductsRestServiceImpl(ProductsRestService productsService) {
        super(productsService);
    }

    @GetMapping
    @Override
    public ProductsDto getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") ProductSortOptionDto sort,
            @RequestParam("direction") SortDirectionDto direction,
            @RequestHeader(REQUESTER_ID) UUID requesterId) {
        return getServiceWorker().getProducts(page, size, sort, direction, requesterId);
    }
}
