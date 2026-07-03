package com.querybricks.processor;

import com.querybricks.annotation.Table;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
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
            .filter(element -> element.getKind() == ElementKind.INTERFACE).toList();

        for (Element element : interfaces) {
            this.processingEnv.getMessager().printMessage(
                Diagnostic.Kind.NOTE,
                "Таблица: " + new SnakeCaseName(new CamelCaseNameWords(element.getSimpleName())).asString()
            );
        }

        return true;
    }
}
