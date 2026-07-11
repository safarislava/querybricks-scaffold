package com.querybricks.processor;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for TableProcessor.
 */
final class TableProcessorTest {
    @Test
    public void testGeneratedClassContent() {
        MatcherAssert.assertThat(
            new Compilation(
                new StringSourceFile(
                    "com.querybricks.example.UsersTable",
                    """
                        package com.querybricks.example;
                        import com.querybricks.annotation.Table;
                        import com.querybricks.column.BoundColumn;
                        import com.querybricks.table.FilterableTable;
                        import java.time.Instant;
                        
                        @Table
                        public interface UsersTable extends FilterableTable {
                            BoundColumn<Long> id();
                            BoundColumn<String> username();
                            BoundColumn<String> status();
                            BoundColumn<Instant> createdAt();
                        }
                        """
                ),
                new TableProcessor()
            ).result().fileContent("com/querybricks/example/DbUsersTable.java"),
            Matchers.equalTo(
                """
                    package com.querybricks.example;
                    
                    import com.querybricks.column.BoundColumn;
                    import com.querybricks.column.RawColumn;
                    import com.querybricks.column.TableColumn;
                    import java.lang.Long;
                    import java.lang.Override;
                    import java.lang.String;
                    import java.time.Instant;
                    import java.util.List;
                    
                    public final class DbUsersTable implements UsersTable {
                        private final String name;
                    
                        public DbUsersTable(String name) {
                            this.name = name;
                        }
                    
                        @Override
                        public BoundColumn<Long> id() {
                            return new TableColumn<>(this, new RawColumn<>("id"));
                        }
                    
                        @Override
                        public BoundColumn<String> username() {
                            return new TableColumn<>(this, new RawColumn<>("username"));
                        }
                    
                        @Override
                        public BoundColumn<String> status() {
                            return new TableColumn<>(this, new RawColumn<>("status"));
                        }
                    
                        @Override
                        public BoundColumn<Instant> createdAt() {
                            return new TableColumn<>(this, new RawColumn<>("created_at"));
                        }
                    
                        @Override
                        public List<BoundColumn<?>> columns() {
                            return List.of(id(), username(), status(), createdAt());
                        }
                    
                        @Override
                        public String sql() {
                            return this.name;
                        }
                    }
                    """
            )
        );
    }
}
