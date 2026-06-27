package com.pe.multimodule.bl.transformer;

import com.pe.multimodule.domain.AbstractNamedDomain;
import com.pe.multimodule.dto.AbstractNamedEntityDto;

import java.util.UUID;

/**
 * @author kamen on 1.06.22 г.
 */
public abstract class AbstractNamedEntityBiTransformer<Entity extends AbstractNamedDomain<UUID>, Dto extends AbstractNamedEntityDto> extends AbstractDomainBiTransformer<Entity, Dto> {

    @Override
    public void copyToOutput(Entity entity, Dto dto) {
        super.copyToOutput(entity, dto);
        dto.setName(entity.getName());
    }

    @Override
    public void copyToInput(Dto dto, Entity entity) {
        super.copyToInput(dto, entity);
        entity.setName(dto.getName());
    }
}
