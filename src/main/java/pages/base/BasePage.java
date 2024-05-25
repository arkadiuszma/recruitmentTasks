package pages.base;

import config.ConfigProperties;
import logging.Log;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.assertj.core.api.Assertions;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected Actions action;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        initDriver(driver);
        PageFactory.initElements(driver, this);
        setPageLoadTimeout();
    }

    public BasePage(WebDriver driver, WebElement element) {
        initDriver(driver);
        PageFactory.initElements(new DefaultElementLocatorFactory(element), this);
    }

    protected void initDriver(WebDriver driver) {
        this.driver = driver;
        action = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigProperties.timeout));
    }

    protected void checkPageTitle(String expectedTitle) {
        Log.info("Check page title contains: '" + expectedTitle + "'");
        try {
            wait.until(ExpectedConditions.titleContains(expectedTitle));
        } catch (TimeoutException e) {
            Log.error("Page title isn't contains: '" + expectedTitle + "' or wait time is to short");
        }
        Assertions.assertThat(driver.getTitle()).as("Page Title").contains(expectedTitle);
    }

    protected void checkHeaderTitle(String expectedTitle) {
        Log.info("Check header title contains: '" + expectedTitle + "'");
        By header = By.cssSelector("h1");
        try {
            wait.until(ExpectedConditions.textToBe(header, expectedTitle));
        } catch (TimeoutException e) {
            Log.error("Header title isn't contains: '" + expectedTitle + "' or wait time is to short");
        }
        Assertions.assertThat(driver.findElement(header).getText()).as("Page Title").contains(expectedTitle);
    }

    protected void setPageLoadTimeout() {
        setPageLoadTimeout(ConfigProperties.timeout);
    }

    protected void setPageLoadTimeout(Long timeout) {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
    }

    protected void clickElement(WebElement element) {
        clickElement(element, ConfigProperties.timeout);
    }

    protected void clickElement(WebElement element, long timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    @SneakyThrows
    protected String getWebElementText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        if (element.getText().isEmpty()) Log.error("Element text is empty");
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void moveToElement(WebElement element) {
        action.moveToElement(element).perform();
    }
}
