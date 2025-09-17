package integration.lifecycle;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import jakarta.inject.Inject;

public class Hooks {
    
    @Inject
    Context context;
    
    @Before
    public void setUp() {
        // Context is automatically created fresh for each scenario due to @Dependent scope
        // You can add any additional setup here if needed
    }
    
    @After
    public void tearDown() {
        // Clean up context after each scenario
        context.clear();
    }
}