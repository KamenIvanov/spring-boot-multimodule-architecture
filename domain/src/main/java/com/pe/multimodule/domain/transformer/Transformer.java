package com.pe.multimodule.domain.transformer;

public interface Transformer<Input, Output> {

    Output createOutput(Input input);

    /**
     * Copy the data from entityVo to entity.
     *
     * @param input  the input entity
     * @param output the output entity
     */
    default void copyToOutput(Input input, Output output) {

    }
}

