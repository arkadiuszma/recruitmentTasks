package ui.pages.home;

import logging.Log;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.pages.base.NavigationAbstractPage;

public class HomePage extends NavigationAbstractPage<HomePage> {
    public HomePage(WebDriver driver) {
        super(driver);
        checkPageTitle("Telefony, Tablety, Laptopy, Szybki Internet - Dołącz do T-Mobile");
    }

    @FindBy(css = "#didomi-notice-agree-button")
    private WebElement allowCookiesBtn;
    @FindBy(css = ".rounded-full")
    private WebElement basketProductCount;

    public HomePage acceptCookies() {
        Log.info("Accept cookies");
        allowCookiesBtn.click();
        return this;
    }

    @SneakyThrows
    public int getNumberOfProductInBasket() {
        if (isDisplayed(getBasketIcon())) return Integer.parseInt(getWebElementText(basketProductCount));
        else throw new Exception("Basket icon isn't visible");
    }
}
