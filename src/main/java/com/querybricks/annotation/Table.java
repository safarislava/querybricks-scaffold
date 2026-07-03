package com.querybricks.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark querybricks interface tables for code generation.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Table {
    /**
     * Optional custom table name in database. If empty, the name will be inferred from the class name.
     * @return Custom table name.
     */
    String value() default "";
}
