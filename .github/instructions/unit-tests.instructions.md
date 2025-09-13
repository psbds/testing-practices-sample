---
applyTo: "**/*.java"
---

Ensure that all Java files have a corresponding unit test file in the `src/test/java/unit` directory that adheres to the following guidelines:

1. **Naming Convention**: The test file should be named by appending `Test` to the original class name. For example, `MyClass.java` should have a test file named `MyClassTest.java`.
2. **Unit Test File Path**: The test file should be located in the `src/test/java/unit` directory, mirroring the package structure of the source file.
Example: If the source file is located at `src/main/java/com/example/MyClass.java`, the test file should be at `src/test/java/unit/com/example/MyClassTest.java`.