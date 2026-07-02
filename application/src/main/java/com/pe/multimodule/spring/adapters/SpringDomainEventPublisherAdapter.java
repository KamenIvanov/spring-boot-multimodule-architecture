package com.pe.multimodule.spring.adapters;

import com.pe.multimodule.bl.service.DomainEventPublisher;
import com.pe.multimodule.events.EventDto;
import org.springframework.context.ApplicationEventPublisher;

public class SpringDomainEventPublisherAdapter implements DomainEventPublisher {

    private final ApplicationEventPublisher springPublisher;

    public SpringDomainEventPublisherAdapter(ApplicationEventPublisher springPublisher) {
        this.springPublisher = springPublisher;
    }

    @Override
    public void publish(EventDto event) {
        springPublisher.publishEvent(event);
    }
}
