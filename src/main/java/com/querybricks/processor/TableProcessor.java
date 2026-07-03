package com.querybricks.processor;

import com.querybricks.annotation.Table;
import com.querybricks.processor.generation.ColumnListMethod;
import com.querybricks.processor.generation.ColumnMethod;
import com.querybricks.processor.generation.GeneratedField;
import com.querybricks.processor.generation.GeneratedMethod;
import com.querybricks.processor.generation.NameField;
import com.querybricks.processor.generation.PoetSourceFile;
import com.querybricks.processor.generation.SqlMethod;
import com.querybricks.processor.generation.TableClass;
import com.querybricks.processor.generation.TableConstructor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ElementKind;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Annotation Processor to generate Db* implementations of interfaces annotated with @Table.
 */
@SupportedAnnotationTypes("com.querybricks.annotation.Table")
public final class TableProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        List<? extends Element> interfaces = roundEnv.getElementsAnnotatedWith(Table.class).stream()
            .filter(element -> element.getKind().equals(ElementKind.INTERFACE))
            .toList();
        for (Element interfaceElement : interfaces) {
            List<ExecutableElement> unimplementedMethods =  interfaceElement.getEnclosedElements().stream()
                .filter(e -> e.getKind().equals(ElementKind.METHOD))
                .map(e -> (ExecutableElement) e)
                .toList();
            List<GeneratedField> fields = List.of(new NameField());
            List<GeneratedMethod> methods = new ArrayList<>(unimplementedMethods.size() + 3);
            methods.add(new TableConstructor(fields));
            for (ExecutableElement unimplementedMethod : unimplementedMethods) {
                methods.add(new ColumnMethod(unimplementedMethod));
            }
            methods.add(new ColumnListMethod(unimplementedMethods));
            methods.add(new SqlMethod());
            TableClass tableClass = new TableClass(interfaceElement, fields, methods);
            String packageName = this.processingEnv.getElementUtils()
                .getPackageOf(interfaceElement)
                .getQualifiedName()
                .toString();
            new PoetSourceFile(this.processingEnv.getFiler(), packageName, tableClass).save();
        }
        return true;
    }
}
