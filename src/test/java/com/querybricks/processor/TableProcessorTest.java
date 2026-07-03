package com.querybricks.processor;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for TableProcessor.
 */
class TableProcessorTest {

    @Test
    public void testCompilationSuccess() {
        MatcherAssert.assertThat(
            new Compilation(
                new StringSourceFile(
                    "com.querybricks.example.UsersTable",
                    "package com.querybricks.example;\n"
                    + "import com.querybricks.annotation.Table;\n"
                    + "import com.querybricks.column.BoundColumn;\n"
                    + "import com.querybricks.table.FilterableTable;\n"
                    + "import java.time.Instant;\n\n"
                    + "@Table\n"
                    + "public interface UsersTable extends FilterableTable {\n"
                    + "    BoundColumn<Long> id();\n"
                    + "    BoundColumn<String> username();\n"
                    + "    BoundColumn<String> status();\n"
                    + "    BoundColumn<Instant> createdAt();\n"
                    + "}\n"
                ),
                new TableProcessor()
            ).result().isSuccess(),
            Matchers.equalTo(true)
        );
    }

    @Test
    @Disabled("Enable this once code generation is implemented")
    public void testGeneratedClassContent() {
        MatcherAssert.assertThat(
            new Compilation(
                new StringSourceFile(
                    "com.querybricks.example.UsersTable",
                    "package com.querybricks.example;\n"
                    + "import com.querybricks.annotation.Table;\n"
                    + "import com.querybricks.column.BoundColumn;\n"
                    + "import com.querybricks.table.FilterableTable;\n"
                    + "import java.time.Instant;\n\n"
                    + "@Table\n"
                    + "public interface UsersTable extends FilterableTable {\n"
                    + "    BoundColumn<Long> id();\n"
                    + "}\n"
                ),
                new TableProcessor()
            ).result().fileContent("com/querybricks/example/DbUsersTable.java"),
            Matchers.containsString("public final class DbUsersTable implements UsersTable")
        );
    }
}
