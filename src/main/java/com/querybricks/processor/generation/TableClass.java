package com.querybricks.processor.generation;

import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.List;

/**
 * Generates the full database table class implementation.
 */
public final class TableClass implements GeneratedClass {
    private final Element interafaceElement;
    private final List<GeneratedField> fields;
    private final List<GeneratedMethod> methods;

    /**
     * Constructor.
     * @param interafaceElement The interface element annotated with @Table.
     * @param fields The list of fields to generate in the class.
     * @param methods The list of methods to generate in the class.
     */
    public TableClass(Element interafaceElement, List<GeneratedField> fields, List<GeneratedMethod> methods) {
        this.interafaceElement = interafaceElement;
        this.fields = fields;
        this.methods = methods;
    }

    @Override
    public TypeSpec spec() {
        TypeSpec.Builder tableClass = TypeSpec.classBuilder(className())
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addSuperinterface(TypeName.get(interafaceElement.asType()));
        for (GeneratedField field : fields) {
            tableClass.addField(field.spec());
        }
        for (GeneratedMethod method : methods) {
            tableClass.addMethod(method.spec());
        }
        return tableClass.build();
    }

    /**
     * Determine the generated class name based on the interface name.
     * @return Generated class name string.
     */
    private String className() {
        return "Db" + interafaceElement.getSimpleName();
    }
}
