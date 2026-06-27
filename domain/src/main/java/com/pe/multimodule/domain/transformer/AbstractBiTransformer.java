package com.pe.multimodule.domain.transformer;

/**
 * @author kamen on 1.06.22 г.
 */
public abstract class AbstractBiTransformer<Domain, Dto> implements BiTransformer<Domain, Dto> {

    @Override
    public Domain createInput(Dto dto) {
        throw new IllegalArgumentException("Not implemented yet");
    }

    @Override
    public Dto createOutput(Domain domain) {
        throw new IllegalArgumentException("Not implemented yet");
    }
}
