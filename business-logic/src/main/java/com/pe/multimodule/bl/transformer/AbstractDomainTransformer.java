package com.pe.multimodule.bl.transformer;

import com.pe.multimodule.domain.AbstractDomain;
import com.pe.multimodule.domain.transformer.AbstractTransformer;
import com.pe.multimodule.dto.AbstractEntityDto;

import java.util.UUID;

/**
 * @author kamen on 1.06.22 г.
 */
public abstract class AbstractDomainTransformer<Domain extends AbstractDomain<UUID>, Dto extends AbstractEntityDto> extends AbstractTransformer<Domain, Dto> {

    @Override
    public void copyToOutput(Domain domain, Dto dto) {
        dto.setId(domain.getId());
        dto.setCreatedAt(domain.getCreatedAt());
        dto.setUpdatedAt(domain.getUpdatedAt());
    }
}
