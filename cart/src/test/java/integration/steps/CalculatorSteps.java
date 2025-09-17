package integration.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorSteps {

    private int result;

    @Given("I have a calculator")
    public void iHaveACalculator() {
        // Initialize calculator - for this simple example, we don't need an actual object
        result = 0;
    }

    @When("I add {int} and {int}")
    public void iAddAnd(int num1, int num2) {
        result = num1 + num2;
    }

    @When("I subtract {int} from {int}")
    public void iSubtractFrom(int num2, int num1) {
        result = num1 - num2;
    }

    @Then("the result should be {int}")
    public void theResultShouldBe(int expectedResult) {
        assertEquals(expectedResult, result);
    }
}