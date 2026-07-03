package com.querybricks.processor.name;

import java.util.List;

/**
 * Represents a collection of words parsed from a name.
 */
public interface Words {
    /**
     * Get the words as a list.
     * @return List of words.
     */
    List<String> asList();
}
