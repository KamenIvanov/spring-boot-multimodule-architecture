package com.pe.multimodule.dao.impl.transformer;

import com.pe.multimodule.dao.impl.AbstractNamedEntity;
import com.pe.multimodule.domain.AbstractNamedDomain;

import java.util.UUID;

public abstract class AbstractNamedEntityTransformer<
        S extends AbstractNamedEntity,
        D extends AbstractNamedDomain<UUID>
    > extends AbstractEntityTransformer<S, D> {

    @Override
    public void copyToInput(D dest, S source) {
        super.copyToInput(dest, source);
        source.setName(dest.getName());
    }

    @Override
    public void copyToOutput(S source, D dest) {
        super.copyToOutput(source, dest);
        dest.setName(source.getName());
    }
}
