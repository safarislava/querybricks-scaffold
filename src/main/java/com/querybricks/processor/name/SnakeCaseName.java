package com.querybricks.processor.name;

/**
 * Represents a name in snake_case format.
 */
public final class SnakeCaseName implements Name {
    private final Words words;

    /**
     * Constructor.
     * @param words Words to combine.
     */
    public SnakeCaseName(Words words) {
        this.words = words;
    }

    @Override
    public String asString() {
        return String.join("_", words.asList());
    }
}
