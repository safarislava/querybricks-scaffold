package com.querybricks.processor.generation;

import com.squareup.javapoet.TypeSpec;

/**
 * Represents a generated Java class structure.
 */
public interface GeneratedClass {
    /**
     * Get the JavaPoet specification of the class.
     * @return TypeSpec of the generated class.
     */
    TypeSpec spec();
}
