# Cucumber BDD Testing Setup

This project now includes Cucumber for Behavior-Driven Development (BDD) testing.

## Dependencies Added

The following dependencies have been added to enable Cucumber testing:

- `io.quarkiverse.cucumber:quarkus-cucumber` - Quarkus Cucumber extension
- `io.cucumber:cucumber-java` - Cucumber Java implementation
- `io.cucumber:cucumber-junit-platform-engine` - JUnit Platform integration
- `org.junit.platform:junit-platform-suite-api` - JUnit Platform Suite API
- `org.junit.platform:junit-platform-suite-engine` - JUnit Platform Suite Engine

## Project Structure

```
src/test/
├── java/
│   └── integration/
│       ├── steps/
│       │   ├── CalculatorSteps.java        # Example BDD step definitions
│       │   └── CartManagementSteps.java    # Cart-specific step definitions
│       └── CucumberTestRunner.java         # Test runner
└── resources/
    ├── features/
    │   ├── calculator.feature              # Example feature file
    │   └── cart-management.feature         # Cart-specific scenarios
    ├── application.properties              # Test configuration
    └── cucumber.properties                 # Cucumber configuration
```

## Feature Files

Feature files are located in `src/test/resources/features/` and written in Gherkin syntax.

Example:
```gherkin
Feature: Basic Calculator
  As a user
  I want to use a calculator
  So that I can perform basic arithmetic operations

  Scenario: Adding two numbers
    Given I have a calculator
    When I add 5 and 3
    Then the result should be 8
```

## Step Definitions

Step definitions are Java methods that implement the steps described in feature files. They are located in `src/test/java/integration/steps/`.

Example:
```java
@Given("I have a calculator")
public void iHaveACalculator() {
    // Setup code
}

@When("I add {int} and {int}")
public void iAddAnd(int num1, int num2) {
    result = num1 + num2;
}

@Then("the result should be {int}")
public void theResultShouldBe(int expectedResult) {
    assertEquals(expectedResult, result);
}
```

## Running Tests

To run all tests including Cucumber scenarios:
```bash
mvn test
```

To run only unit tests:
```bash
mvn test -Dtest="*Test"
```

To run Cucumber tests specifically (using the quarkus-cucumber extension):
```bash
mvn test -Dtest="CucumberTestRunner"
```

## Configuration

Cucumber configuration is available in:
- `src/test/resources/application.properties` - Quarkus-specific configuration
- `src/test/resources/cucumber.properties` - Cucumber-specific settings

## Benefits of BDD with Cucumber

1. **Living Documentation**: Feature files serve as executable documentation
2. **Collaboration**: Business analysts, testers, and developers can collaborate using Gherkin
3. **Traceability**: Requirements are directly linked to tests
4. **Behavior Focus**: Tests focus on behavior rather than implementation details
5. **Communication**: Clear, readable scenarios in plain English

## Best Practices

1. Write scenarios from the user's perspective
2. Use descriptive scenario names
3. Keep steps simple and reusable
4. Use Background for common setup steps
5. Avoid technical implementation details in feature files
6. Use Scenario Outlines for data-driven tests

## Example Cart Management Scenario

```gherkin
Scenario: Add item to cart
  Given the cart system is available
  When I add an item with name "Sample Product" and price 25.99
  Then the item should be successfully added to the cart
  And the cart should contain 1 item
```

This setup allows you to write comprehensive BDD tests for your cart management system using natural language scenarios that can be understood by both technical and non-technical stakeholders.