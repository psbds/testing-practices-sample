```instructions
---
applyTo: "**/*.java"
---

**MANDATORY REQUIREMENT**: Every Java class in `src/main/java` MUST have a corresponding unit test file in the `src/test/java/unit` directory. GitHub Copilot should actively check for missing test files and suggest creating them.

**ENFORCEMENT**: When reviewing code changes or suggesting improvements for any Java file, always verify that a corresponding test file exists. If missing, immediately suggest creating the test file before any other changes.

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

## Active Enforcement for GitHub Copilot

4. **Missing Test File Detection**: When working with any Java file in `src/main/java`, GitHub Copilot must:
   - Immediately check if the corresponding test file exists in `src/test/java/unit`
   - If the test file is missing, suggest creating it as the first priority
   - Provide the exact file path where the test should be created
   - Offer to generate a basic test template

5. **Code Review Requirements**: During code reviews, GitHub Copilot should:
   - Flag any Java class that lacks a corresponding unit test
   - Suggest test coverage improvements for existing classes
   - Ensure new classes are accompanied by their test files

6. **Examples of Missing Test Files**: Based on the current codebase, the following classes are missing tests and should be flagged:
   - `BaseAuthenticatedResource.java` → needs `BaseAuthenticatedResourceTest.java`
   - `ItemResource.java` → needs `ItemResourceTest.java`
   - `CreateItemService.java` → needs `CreateItemServiceTest.java`
   - `BusinessException.java` → needs `BusinessExceptionTest.java`
   - `ItemFactory.java` → needs `ItemFactoryTest.java`
   - And all other classes in the main source tree