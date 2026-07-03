package com.querybricks.processor.generation;

import com.squareup.javapoet.FieldSpec;

/**
 * Represents a generated class field.
 */
public interface GeneratedField {
    /**
     * Get the JavaPoet specification of the field.
     * @return FieldSpec of the generated field.
     */
    FieldSpec spec();
}
