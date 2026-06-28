package com.pe.multimodule.domain.transformer;

public abstract class AbstractTransformer<Domain, Dto> implements Transformer<Domain, Dto> {

    @Override
    public Dto createOutput(Domain domain) {
        throw new IllegalArgumentException("Not implemented yet");
    }
}
