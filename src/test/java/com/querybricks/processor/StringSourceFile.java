package com.querybricks.processor;

import java.net.URI;
import javax.tools.SimpleJavaFileObject;

/**
 * In-memory source file wrapper.
 */
final class StringSourceFile extends SimpleJavaFileObject {
    private final String content;

    StringSourceFile(String name, String content) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.content = content;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return this.content;
    }
}
