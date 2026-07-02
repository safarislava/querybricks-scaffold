package com.querybrick;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class CodegenTest {
    @Test
    public void test() {
        Codegen codegen = new Codegen();
        MatcherAssert.assertThat(
            codegen.hello(),
            Matchers.equalTo("hello")
        );
    }
}
