---
applyTo: "**/*.java"
---

Ensure that all Java files have a corresponding unit test file in the `src/test/java/unit` directory that adheres to the following guidelines:

1. **Naming Convention**: The test file should be named by appending `Test` to the original class name. For example, `MyClass.java` should have a test file named `MyClassTest.java`.
2. **Unit Test File Path**: The test file should be located in the `src/test/java/unit` directory, mirroring the package structure of the source file.
Example: If the source file is located at `src/main/java/com/example/MyClass.java`, the test file should be at `src/test/java/unit/com/example/MyClassTest.java`.

## Annotation Testing Guidelines

3. **Annotation Null Checks**: When testing annotations on fields, methods, or classes, do NOT add null checks before accessing annotation properties. If an annotation is expected to be present, let the test fail with a NullPointerException if the annotation is missing - this indicates a real problem with the code under test.

   **Preferred approach:**
   ```java
   NotNull notNull = keyField.getAnnotation(NotNull.class);
   assertEquals("Key is required", notNull.message(), 
       "key field should have correct NotNull message");
   ```

   **Avoid unnecessary null checks:**
   ```java
   // DON'T do this - let it throw NPE if annotation is missing
   NotNull notNull = keyField.getAnnotation(NotNull.class);
   assertNotNull(notNull); // This check is redundant
   assertEquals("Key is required", notNull.message(), 
       "key field should have correct NotNull message");
   ```

   The NullPointerException serves as a clear indicator that the expected annotation is not present, which is valuable diagnostic information.