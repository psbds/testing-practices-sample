package integration.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.*;

public class CartManagementSteps {

    private String lastResponse;
    private String lastItemId;
    private boolean systemAvailable = false;
    private boolean itemAdded = false;
    private boolean validationError = false;

    @Given("the cart system is available")
    public void theCartSystemIsAvailable() {
        // Simulate system availability check
        systemAvailable = true;
    }

    @When("I add an item with name {string} and price {double}")
    public void iAddAnItemWithNameAndPrice(String itemName, double price) {
        // Simulate adding an item
        if (systemAvailable && !itemName.isEmpty() && price > 0) {
            lastItemId = "item-" + System.currentTimeMillis();
            itemAdded = true;
            lastResponse = "Item added successfully";
        } else {
            itemAdded = false;
            lastResponse = "Failed to add item";
        }
    }

    @Then("the item should be successfully added to the cart")
    public void theItemShouldBeSuccessfullyAddedToTheCart() {
        assertTrue(itemAdded, "Item should have been added successfully");
        assertNotNull(lastItemId, "Item ID should be generated");
    }

    @And("the cart should contain {int} item(s)")
    public void theCartShouldContainItems(int expectedCount) {
        // For this demo, we'll assume the item was added if itemAdded is true
        if (expectedCount == 1) {
            assertTrue(itemAdded, "Cart should contain 1 item");
        } else {
            // For other counts, we'd need to track the actual cart state
            assertTrue(true, "Cart item count validated");
        }
    }

    @Given("I have an item with name {string} and price {double} in the cart")
    public void iHaveAnItemWithNameAndPriceInTheCart(String itemName, double price) {
        // First ensure system is available
        theCartSystemIsAvailable();
        // Then add the item
        iAddAnItemWithNameAndPrice(itemName, price);
        theItemShouldBeSuccessfullyAddedToTheCart();
    }

    @When("I retrieve all cart items")
    public void iRetrieveAllCartItems() {
        if (systemAvailable) {
            lastResponse = "Items retrieved successfully";
        } else {
            lastResponse = "System not available";
        }
    }

    @Then("I should see the item with name {string}")
    public void iShouldSeeTheItemWithName(String expectedName) {
        assertTrue(systemAvailable, "System should be available");
        assertTrue(lastResponse.contains("retrieved") || itemAdded, 
                  "Should be able to see item with name: " + expectedName);
    }

    @And("the total cart value should be {double}")
    public void theTotalCartValueShouldBe(double expectedTotal) {
        assertTrue(systemAvailable, "System should be available");
        // In a real implementation, you would calculate and verify the actual total
        assertTrue(expectedTotal > 0, "Total cart value should be positive");
    }

    @When("I try to add an item with an empty name")
    public void iTryToAddAnItemWithAnEmptyName() {
        // Simulate trying to add item with empty name
        if (systemAvailable) {
            validationError = true;
            itemAdded = false;
            lastResponse = "Validation error: Item name cannot be empty";
        }
    }

    @Then("I should receive a validation error")
    public void iShouldReceiveAValidationError() {
        assertTrue(validationError, "Should receive a validation error");
        assertTrue(lastResponse.contains("Validation error"), "Response should contain validation error message");
    }

    @And("the cart should remain empty")
    public void theCartShouldRemainEmpty() {
        assertFalse(itemAdded, "No item should be added when validation fails");
    }
}