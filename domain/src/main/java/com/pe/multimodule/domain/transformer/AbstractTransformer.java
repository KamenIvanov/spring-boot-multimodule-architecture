package com.pe.multimodule.domain.transformer;

/**
 * @author kamen on 1.06.22 г.
 */
public abstract class AbstractTransformer<Domain, Dto> implements Transformer<Domain, Dto> {

    @Override
    public Dto createOutput(Domain domain) {
        throw new IllegalArgumentException("Not implemented yet");
    }
}
