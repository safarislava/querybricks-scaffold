package com.querybricks.processor.generation;

import com.squareup.javapoet.MethodSpec;

/**
 * Represents a generated class method.
 */
public interface GeneratedMethod {
    /**
     * Get the JavaPoet specification of the method.
     * @return MethodSpec of the generated method.
     */
    MethodSpec spec();
}
