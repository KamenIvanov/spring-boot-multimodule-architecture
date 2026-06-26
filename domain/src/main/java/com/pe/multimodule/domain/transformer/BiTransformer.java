package com.pe.multimodule.domain.transformer;

public interface BiTransformer<Input, Output> extends Transformer<Input, Output> {

    Input createInput(Output output);

    default void copyToInput(Output output, Input input) {

    }
}
