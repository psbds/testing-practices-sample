# GitHub Copilot Code Review Instructions

## Mandatory Requirements for Pull Request Reviews

When GitHub Copilot performs code reviews on pull requests, the following requirements MUST be enforced:

### 1. **UNIT TEST COVERAGE VERIFICATION**
- **REQUIREMENT**: Every Java class in `src/main/java` MUST have a corresponding unit test file in `src/test/java/unit`
- **ACTION**: Before reviewing any other aspects of the code, immediately check for missing test files
- **RESPONSE**: If test files are missing, flag this as the highest priority issue and suggest creating them

### 2. **TEST FILE NAMING AND LOCATION VALIDATION**
- **REQUIREMENT**: Test files must follow strict naming conventions
  - Test file name: `{ClassName}Test.java` (append `Test` to the original class name)
  - Location: `src/test/java/unit/{package.structure}/{ClassName}Test.java`
- **ACTION**: Verify that existing test files follow the correct naming pattern and location
- **EXAMPLE**: `src/main/java/com/example/MyClass.java` → `src/test/java/unit/com/example/MyClassTest.java`

### 3. **ANNOTATION TESTING ENFORCEMENT**
- **REQUIREMENT**: Do NOT suggest adding null checks before accessing annotation properties in tests
- **RATIONALE**: NullPointerException indicates missing annotations and provides valuable diagnostic information
- **CORRECT APPROACH**:
  ```java
  NotNull notNull = keyField.getAnnotation(NotNull.class);
  assertEquals("Key is required", notNull.message(), 
      "key field should have correct NotNull message");
  ```
- **AVOID SUGGESTING**:
  ```java
  NotNull notNull = keyField.getAnnotation(NotNull.class);
  assertNotNull(notNull); // This check is redundant
  ```

### 4. **MISSING TEST FILE DETECTION**
- **REQUIREMENT**: When reviewing any Java file in `src/main/java`, immediately verify test file existence
- **MANDATORY ACTIONS**:
  - Check if corresponding test file exists in `src/test/java/unit`
  - If missing, flag as critical issue requiring immediate attention
  - Provide exact file path where test should be created
  - Offer to generate basic test template
  - Do not proceed with other review comments until test coverage is addressed

### 5. **NEW CLASS VALIDATION**
- **REQUIREMENT**: Any new Java class added in a PR MUST be accompanied by its corresponding test file
- **ACTION**: Flag new classes without tests as blocking issues
- **RESPONSE**: Request test file creation before approving the PR

### 6. **EXISTING CODEBASE COMPLIANCE**
- **REQUIREMENT**: Flag existing classes that lack unit tests during reviews
- **CURRENT VIOLATIONS** (these should be flagged when encountered):
  - `BaseAuthenticatedResource.java` → missing `BaseAuthenticatedResourceTest.java`
  - `ItemResource.java` → missing `ItemResourceTest.java`
  - `CreateItemService.java` → missing `CreateItemServiceTest.java`
  - `BusinessException.java` → missing `BusinessExceptionTest.java`
  - `ItemFactory.java` → missing `ItemFactoryTest.java`
  - All other classes in the main source tree without corresponding tests

### 7. **TEST COVERAGE IMPROVEMENT SUGGESTIONS**
- **REQUIREMENT**: For classes that already have tests, suggest improvements to test coverage
- **ACTION**: Identify untested methods, edge cases, or missing scenarios
- **RESPONSE**: Provide specific suggestions for additional test cases

### 8. **PRIORITY ORDER FOR REVIEW COMMENTS**
When providing review feedback, follow this priority order:
1. **CRITICAL**: Missing test files (highest priority)
2. **HIGH**: Test quality and coverage issues
3. **MEDIUM**: Code quality improvements
4. **LOW**: Style and formatting suggestions

## Enforcement Notes

- **BLOCKING ISSUES**: Missing test files should be treated as blocking issues that prevent PR approval
- **CONSISTENCY**: Apply these requirements consistently across all Java files
- **IMMEDIATE ACTION**: Address test coverage before any other code review activities
