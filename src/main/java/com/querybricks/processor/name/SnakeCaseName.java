package com.querybricks.processor.name;

public final class SnakeCaseName implements Name {
    private final Words words;

    public SnakeCaseName(Words words) {
        this.words = words;
    }

    @Override
    public String asString() {
        return String.join("_", this.words.asList());
    }
}
