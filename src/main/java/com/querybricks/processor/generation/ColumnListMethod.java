package com.querybricks.processor.generation;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.WildcardTypeName;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

/**
 * Generates the columns() method returning all bound columns of the table.
 */
public final class ColumnListMethod implements GeneratedMethod {
    private final List<ExecutableElement> elements;

    /**
     * Constructor.
     * @param elements ExecutableElement methods representation of columns.
     */
    public ColumnListMethod(List<ExecutableElement> elements) {
        this.elements = new ArrayList<>(elements);
    }

    @Override
    public MethodSpec spec() {
        List<String> methods = new ArrayList<>(elements.size());
        for (ExecutableElement element : elements) {
            methods.add(element.getSimpleName() + "()");
        }
        ClassName listClass = ClassName.get("java.util", "List");
        ClassName boundColumnClass = ClassName.get("com.querybricks.column", "BoundColumn");
        return MethodSpec.methodBuilder("columns")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(ParameterizedTypeName.get(
                listClass,
                ParameterizedTypeName.get(boundColumnClass, WildcardTypeName.subtypeOf(Object.class))
            ))
            .addStatement("return $T.of(" + String.join(", ", methods) + ")", listClass)
            .build();
    }
}
