package com.pe.multimodule.bl.service.secured;

import com.pe.multimodule.dto.product.ProductDto;
import com.pe.multimodule.events.products.AbstractProductEventDto;
import com.pe.multimodule.events.products.ProductCreatedEventDto;
import com.pe.multimodule.events.products.ProductDeletedEventDto;
import com.pe.multimodule.events.products.ProductUpdatedEventDto;

import java.time.Instant;
import java.util.UUID;

public class ProductEventFactory {

    private ProductEventFactory() {
        // Helper
    }

    public static ProductCreatedEventDto productCreatedEvent(ProductDto product, UUID requesterId) {
        final var event = new ProductCreatedEventDto();
        hydrate(event, product.getId(), requesterId);
        return event;
    }

    public static ProductUpdatedEventDto productUpdatedEvent(ProductDto product, UUID requesterId) {
        final var event = new ProductUpdatedEventDto();
        hydrate(event, product.getId(), requesterId);
        return event;
    }

    public static ProductDeletedEventDto productDeletedEvent(UUID productId, UUID requesterId) {
        final var event = new ProductDeletedEventDto();
        hydrate(event, productId, requesterId);
        return event;
    }

    private static void hydrate(AbstractProductEventDto productEvent, UUID productId, UUID requesterId) {
        productEvent.setId(UUID.randomUUID());
        productEvent.setVersion(UUID.randomUUID());
        productEvent.setTimestamp(Instant.now());
        productEvent.setAccountId(requesterId);
        productEvent.setProductId(productId);
    }
}
