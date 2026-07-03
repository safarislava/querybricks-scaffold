package com.querybricks.processor.generation;

import com.squareup.javapoet.FieldSpec;

import javax.lang.model.element.Modifier;

public final class NameField implements GeneratedField {

    @Override
    public FieldSpec spec() {
        return FieldSpec.builder(String.class, "name", Modifier.PRIVATE, Modifier.FINAL).build();
    }
}
