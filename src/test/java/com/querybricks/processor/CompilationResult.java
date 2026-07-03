package com.querybricks.processor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Result of a test compilation.
 */
final class CompilationResult {
    private final boolean success;
    private final Path sourceOutputDir;
    private final List<Diagnostic<? extends JavaFileObject>> diagnostics;

    CompilationResult(
        boolean success,
        Path sourceOutputDir,
        List<Diagnostic<? extends JavaFileObject>> diagnostics
    ) {
        this.success = success;
        this.sourceOutputDir = sourceOutputDir;
        this.diagnostics = diagnostics;
    }

    boolean isSuccess() {
        return this.success;
    }

    String fileContent(String relativePath) {
        try {
            final Path file = this.sourceOutputDir.resolve(relativePath);
            if (!Files.exists(file)) {
                return "";
            }
            return new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read generated file: " + relativePath, e);
        }
    }

    String diagnosticsSummary() {
        final StringBuilder sb = new StringBuilder();
        for (Diagnostic<? extends JavaFileObject> diagnostic : this.diagnostics) {
            sb.append(diagnostic.toString()).append("\n");
        }
        return sb.toString();
    }
}
