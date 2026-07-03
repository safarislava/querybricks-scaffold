package com.querybricks.processor;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CamelCaseNameWordsTest {
    @Test
    public void testAsList() {
        MatcherAssert.assertThat(
            new CamelCaseNameWords("createdAt").asList(),
            Matchers.equalTo(List.of("created", "at"))
        );
    }

    @Test
    public void testAsListWithFirstCapitalLetter() {
        MatcherAssert.assertThat(
            new CamelCaseNameWords("UsersTable").asList(),
            Matchers.equalTo(List.of("users", "table"))
        );
    }
}
