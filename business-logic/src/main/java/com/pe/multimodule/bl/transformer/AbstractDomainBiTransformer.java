package com.pe.multimodule.bl.transformer;

import com.pe.multimodule.domain.AbstractDomain;
import com.pe.multimodule.domain.transformer.AbstractBiTransformer;
import com.pe.multimodule.dto.AbstractEntityDto;

import java.util.UUID;

public abstract class AbstractDomainBiTransformer<Domain extends AbstractDomain<UUID>, Dto extends AbstractEntityDto> extends AbstractBiTransformer<Domain, Dto> {

    @Override
    public void copyToOutput(Domain domain, Dto dto) {
        dto.setId(domain.getId());
        dto.setCreatedAt(domain.getCreatedAt());
        dto.setUpdatedAt(domain.getUpdatedAt());
    }

    @Override
    public void copyToInput(Dto dto, Domain domain) {
        domain.setId(dto.getId());
        domain.setCreatedAt(dto.getCreatedAt());
        domain.setUpdatedAt(dto.getUpdatedAt());
    }
}
