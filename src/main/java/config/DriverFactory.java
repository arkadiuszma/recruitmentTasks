package config;

import logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    private final String browser = ConfigProperties.browser;
    private WebDriver driver;


    public WebDriver getDriver() {
        switch (browser) {
            case "CHROME" -> driver = new ChromeDriver();
            case "FIREFOX" -> driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.get(ConfigProperties.baseUIUrl);
        Log.info("Open browser: " + browser + " on: " + ConfigProperties.baseUIUrl);
        return driver;
    }
}
