package integration.steps.shared;

import io.cucumber.java.en.Then;
import integration.lifecycle.Context;
import jakarta.inject.Inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpResponseSteps {
    
    @Inject
    Context context;

    @Then("the response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String expectedStatusCode) {
        assertNotNull(context.getLastResponse(), "No response found in context");
        int expected = Integer.parseInt(expectedStatusCode);
        int actual = context.getLastResponse().getStatusCode();
        assertEquals(expected, actual, 
            "Expected status code " + expected + " but got " + actual);
    }

    @Then("the response should be sucessful")
    public void theResponseShouldBeSuccessful() {
        assertNotNull(context.getLastResponse(), "No response found in context");
        int expected = 200;
        int actual = context.getLastResponse().getStatusCode();
        assertEquals(expected, actual, 
            "Expected status code " + expected + " but got " + actual);
    }
}
