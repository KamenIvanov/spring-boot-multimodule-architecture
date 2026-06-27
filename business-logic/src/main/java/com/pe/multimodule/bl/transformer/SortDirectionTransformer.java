package com.pe.multimodule.bl.transformer;

import com.pe.multimodule.dao.api.SortDirection;
import com.pe.multimodule.domain.transformer.AbstractEnumTransformer;
import com.pe.multimodule.dto.SortDirectionDto;

public class SortDirectionTransformer extends AbstractEnumTransformer<SortDirection, SortDirectionDto> {

    public static final SortDirectionTransformer instance = new SortDirectionTransformer();

    private SortDirectionTransformer() {
        super(SortDirection.class, SortDirectionDto.class);
    }
}
