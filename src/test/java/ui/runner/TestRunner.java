package ui.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/ui/features",
        glue = "ui/step_definitions",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class TestRunner {
}
