package com.pe.multimodule.bl.transformer;

import com.pe.multimodule.domain.AbstractNamedDomain;
import com.pe.multimodule.dto.AbstractNamedEntityDto;

import java.util.UUID;

public abstract class AbstractNamedEntityTransformer<Entity extends AbstractNamedDomain<UUID>, Dto extends AbstractNamedEntityDto> extends AbstractDomainTransformer<Entity, Dto> {

    @Override
    public void copyToOutput(Entity entity, Dto dto) {
        super.copyToOutput(entity, dto);
        dto.setName(entity.getName());
    }
}
