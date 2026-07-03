package com.querybricks.processor.generation;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates a package-private constructor that initializes all fields of the class.
 */
public final class TableConstructor implements GeneratedMethod {
    private final List<GeneratedField> fields;

    /**
     * Constructor.
     * @param fields Fields to initialize.
     */
    public TableConstructor(List<GeneratedField> fields) {
        this.fields = new ArrayList<>(fields);
    }

    @Override
    public MethodSpec spec() {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC);
        for (GeneratedField field : fields) {
            FieldSpec spec = field.spec();
            builder.addParameter(spec.type, spec.name);
            builder.addStatement("this.$N = $N", spec, spec);
        }
        return builder.build();
    }
}
