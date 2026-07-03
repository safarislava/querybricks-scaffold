package com.querybricks.processor.name;

import java.util.ArrayList;
import java.util.List;

public final class CamelCaseNameWords implements Words {
    private final CharSequence name;

    public CamelCaseNameWords(CharSequence name) {
        this.name = name;
    }

    @Override
    public List<String> asList() {
        List<String> words = new ArrayList<>();
        String nameString = this.name.toString();
        int indexWordBeginning = 0;
        for (int i = 0; i < nameString.length(); i++) {
            if (Character.isUpperCase(nameString.charAt(i))) {
                if (indexWordBeginning == i) {
                    continue;
                }
                words.add(nameString.substring(indexWordBeginning, i).toLowerCase());
                indexWordBeginning = i;
            }
        }
        words.add(nameString.substring(indexWordBeginning).toLowerCase());
        return words;
    }
}
