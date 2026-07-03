package com.querybricks.processor;

import java.util.List;

public class FakeWords implements Words {
    private final List<String> words;

    public FakeWords(List<String> words) {
        this.words = words;
    }

    @Override
    public List<String> asList() {
        return words;
    }
}
