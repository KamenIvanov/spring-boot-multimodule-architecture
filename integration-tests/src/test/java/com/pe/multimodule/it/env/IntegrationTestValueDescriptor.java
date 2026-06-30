package com.pe.multimodule.it.env;

import java.lang.annotation.Annotation;
import java.util.function.BiFunction;

public record IntegrationTestValueDescriptor<AnnotationType extends Annotation, ValueType>(
        Class<AnnotationType> annotationType,
        Class<ValueType> valueType,
        BiFunction<DockerEnvironment, ? super AnnotationType, ? extends ValueType> valueGetter
) {

}
