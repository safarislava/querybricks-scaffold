package com.querybricks.processor;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

final class SnakeCaseNameTest {
    @Test
    void testAsString() {
        MatcherAssert.assertThat(
            new SnakeCaseName(new FakeWords(List.of("say", "my", "name"))).asString(),
            Matchers.equalTo("say_my_name")
        );
    }
}
