package com.querybricks.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Processor;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

/**
 * Runs programmatic compilation with an annotation processor.
 */
final class Compilation {
    private final List<JavaFileObject> sources;
    private final Processor processor;

    Compilation(JavaFileObject source, Processor processor) {
        this.sources = Collections.singletonList(source);
        this.processor = processor;
    }

    CompilationResult result() {
        try {
            final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            final StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
            final Path tempDir = Files.createTempDirectory("codegen-test");
            fileManager.setLocation(StandardLocation.SOURCE_OUTPUT, Collections.singletonList(tempDir.toFile()));

            final String classpath = System.getProperty("java.class.path");

            final JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                diagnostics,
                List.of("-cp", classpath),
                null,
                this.sources
            );
            task.setProcessors(Collections.singletonList(this.processor));
            final boolean success = task.call();
            fileManager.close();

            for (javax.tools.Diagnostic<? extends JavaFileObject> diag : diagnostics.getDiagnostics()) {
                System.out.println(diag.getMessage(null));
            }

            return new CompilationResult(success, tempDir, diagnostics.getDiagnostics());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to run compilation test", e);
        }
    }
}
