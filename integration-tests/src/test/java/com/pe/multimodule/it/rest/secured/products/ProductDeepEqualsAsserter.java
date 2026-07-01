package com.pe.multimodule.it.rest.secured.products;

import com.pe.multimodule.domain.asserter.DeepEqualsAsserter;
import com.pe.multimodule.dto.product.ProductDto;

import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductDeepEqualsAsserter implements DeepEqualsAsserter<ProductDto> {

    public static final ProductDeepEqualsAsserter instance = new ProductDeepEqualsAsserter();

    private ProductDeepEqualsAsserter() {
        // Singleton
    }

    @Override
    public void assertDeepEquals(ProductDto expected, ProductDto actual) {
        assertEquals(expected.getId(), actual.getId());
//       TODO assertEquals(expected.getCreatedAt().truncatedTo(ChronoUnit.MILLIS), actual.getCreatedAt().truncatedTo(ChronoUnit.MILLIS));
        if (expected.getUpdatedAt() != null) {
            assertEquals(expected.getUpdatedAt().truncatedTo(ChronoUnit.MILLIS), actual.getUpdatedAt().truncatedTo(ChronoUnit.MILLIS));
        }

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSku(), actual.getSku());
        assertEquals(0, expected.getPrice().compareTo(actual.getPrice()));
    }
}
