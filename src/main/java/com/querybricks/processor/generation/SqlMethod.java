package com.querybricks.processor.generation;

import com.squareup.javapoet.MethodSpec;
import javax.lang.model.element.Modifier;

/**
 * Generates the overridden sql() method returning the name of the table.
 */
public final class SqlMethod implements GeneratedMethod {

    @Override
    public MethodSpec spec() {
        return MethodSpec.methodBuilder("sql")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(String.class)
            .addStatement("return this.name")
            .build();
    }
}
