# QueryBricks Scaffold

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](#)
[![Java Version](https://img.shields.io/badge/java-25-blue.svg)](#)
[![Paradigm](https://img.shields.io/badge/paradigm-elegant_objects-orange.svg)](#)

**QueryBricks Scaffold** is an annotation processor for **QueryBricks** that automatically generates concrete, type-safe database table implementations from Java interfaces.

Designed in strict compliance with the **Elegant Objects (EO)** paradigm, it eliminates the boilerplate code needed to map columns, table names, and column lists.

---

## Key Features

* **Automatic Column Mapping**:
  * Scans interface methods returning `BoundColumn<T>`.
  * Automatically maps method names to database column names, converting **camelCase** (e.g., `createdAt()`) to **snake_case** (e.g., `created_at`).
* **Boilerplate Reduction**:
  * Automatically implements the `columns()` method returning a list of all defined columns (`List.of(...)`).
  * Implements `sql()` to return the custom table name passed via the constructor.
* **Compile-Time Generation**:
  * Relies entirely on `javax.annotation.processing` and [JavaPoet](https://github.com/square/javapoet) to produce source files during the compilation phase, requiring zero runtime reflection.

---

## Installation

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}
```

```
dependencies {
    implementation("com.github.safarislava:querybricks:0.1.0")
    annotationProcessor("com.github.safarislava:querybricks-scaffold:0.1.0")
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```
<dependencies>
    <dependency>
        <groupId>com.github.safarislava</groupId>
        <artifactId>querybricks</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.13.0</version>
            <configuration>
                <release>25</release>
                <annotationProcessorPaths>
                    <path>
                        <groupId>com.github.safarislava</groupId>
                        <artifactId>querybricks-scaffold</artifactId>
                        <version>0.1.0</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

---

## How to Use

### 1. Define the Table Interface
Annotate your table interface with `@Table` and extend `FilterableTable`. Define the column methods returning `BoundColumn<Type>`:

```java
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
```

### 2. Auto-Generated Implementation
During compilation, **QueryBricks Scaffold** generates the following implementation:

```java
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
    public final BoundColumn<Long> id() {
        return new TableColumn<>(this, new RawColumn<>("id"));
    }

    @Override
    public final BoundColumn<String> username() {
        return new TableColumn<>(this, new RawColumn<>("username"));
    }

    @Override
    public final BoundColumn<String> status() {
        return new TableColumn<>(this, new RawColumn<>("status"));
    }

    @Override
    public final BoundColumn<Instant> createdAt() {
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
```

### 3. Build Queries
Now you can instantiate the generated class by passing the physical table name to the constructor, and construct query trees:

```java
UsersTable users = new DbUsersTable("users");

var query = new SelectQuery(
    new ColumnsSelection(users.id(), users.username()),
    new FilteredTable<>(
        users,
        new Equals(users.status(), new StringLiteral("active"))
    )
);
```
