package integration;

import io.quarkiverse.cucumber.CucumberOptions;
import io.quarkiverse.cucumber.CucumberQuarkusTest;

@CucumberOptions(
        glue = {"integration.steps"},
        plugin = {"json:report/index.json", "html:report/index.html"},
        features = "src/test/java/integration/features"
)
public class CucumberTestRunner extends CucumberQuarkusTest {

    public static void main(String[] args) {
        runMain(CucumberTestRunner.class, args);
    }
}