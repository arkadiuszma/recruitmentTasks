package ui.base;

import config.ConfigManager;
import config.DriverFactory;
import logging.Log;
import lombok.SneakyThrows;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class BaseUITest implements TestWatcher {
    protected WebDriver driver;
    protected SoftAssertions s = new SoftAssertions();

    @BeforeAll
    public static void loadConfig() {
        ConfigManager.getInstance();
    }

    @BeforeEach
    public void setupDriver() {
        driver = new DriverFactory().getDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        Log.info("Browser was closed");
    }

    @SneakyThrows
    private void takeScreenshot(String testName) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(screenshotFile.toPath(), Paths.get("screenshots", testName + ".png"));
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (context.getExecutionException().isPresent()) takeScreenshot(context.getDisplayName());
    }
}
