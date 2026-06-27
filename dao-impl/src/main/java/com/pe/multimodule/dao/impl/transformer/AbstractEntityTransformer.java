package com.pe.multimodule.dao.impl.transformer;

import com.pe.multimodule.dao.impl.AbstractEntity;
import com.pe.multimodule.domain.AbstractDomain;
import com.pe.multimodule.domain.transformer.BiTransformer;

import java.util.UUID;

public abstract class AbstractEntityTransformer<S extends AbstractEntity, D extends AbstractDomain<UUID>> implements BiTransformer<S, D> {

    @Override
    public void copyToInput(D dest, S source) {
        source.setId(dest.getId());
        source.setCreatedAt(dest.getCreatedAt());
        source.setUpdatedAt(dest.getUpdatedAt());
    }

    @Override
    public void copyToOutput(S source, D dest) {
        dest.setId(source.getId());
        dest.setCreatedAt(source.getCreatedAt());
        dest.setUpdatedAt(source.getUpdatedAt());
    }
}
