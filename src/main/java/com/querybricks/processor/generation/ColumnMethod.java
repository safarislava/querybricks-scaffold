package com.querybricks.processor.generation;

import com.querybricks.processor.name.CamelCaseNameWords;
import com.querybricks.processor.name.SnakeCaseName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

/**
 * Generates a method representing a table column.
 */
public final class ColumnMethod implements GeneratedMethod {
    private final ExecutableElement element;
    private final String columnName;

    /**
     * Primary constructor.
     * @param element ExecutableElement method representation.
     * @param columnName String representation of the database column name.
     */
    public ColumnMethod(ExecutableElement element, String columnName) {
        this.element = element;
        this.columnName = columnName;
    }

    /**
     * Convenience constructor.
     * @param element ExecutableElement method representation.
     */
    public ColumnMethod(ExecutableElement element) {
        this(element, new SnakeCaseName(new CamelCaseNameWords(element.getSimpleName())).asString());
    }

    @Override
    public MethodSpec spec() {
        ClassName tableColumn = ClassName.get("com.querybricks.column", "TableColumn");
        ClassName rawColumn = ClassName.get("com.querybricks.column", "RawColumn");
        return MethodSpec.methodBuilder(element.getSimpleName().toString())
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .returns(TypeName.get(element.getReturnType()))
            .addStatement("return new $T<>(this, new $T<>($S))", tableColumn, rawColumn, columnName)
            .build();
    }
}
