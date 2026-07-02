package com.pe.multimodule.bl.service;

import com.pe.multimodule.events.EventDto;

public interface DomainEventPublisher {
    void publish(EventDto event);
}
