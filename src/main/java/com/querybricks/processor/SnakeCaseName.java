package com.querybricks.processor;

public final class SnakeCaseName implements Name {
    private final Words words;

    SnakeCaseName(Words words) {
        this.words = words;
    }

    @Override
    public String asString() {
        return String.join("_", this.words.asList());
    }
}
