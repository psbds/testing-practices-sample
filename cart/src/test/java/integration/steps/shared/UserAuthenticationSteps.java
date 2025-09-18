package integration.steps.shared;

import jakarta.inject.Inject;

import integration.lifecycle.Context;
import io.cucumber.java.en.Given;

public class UserAuthenticationSteps {

    @Inject
    private Context context;

    @Given("i'm the user with id {string}")
    public void imTheUserWithId(String userId) {
        context.setCurrentUserId(userId);
    }
    @Given("i'm a valid user")
    public void imAValidUser() {
        context.setCurrentUserId("valid-user-id");
    }
}