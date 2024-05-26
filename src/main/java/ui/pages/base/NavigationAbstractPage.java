package ui.pages.base;

import logging.Log;
import lombok.Getter;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.pages.home.HomePage;
import ui.pages.product.ProductListPage;

import java.util.List;

@Getter
public abstract class NavigationAbstractPage<T extends NavigationAbstractPage<T>> extends BasePage {
    public NavigationAbstractPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[contains(text(), 'Urządzenia')]")
    private WebElement devicesBtn;
    @FindBy(css = "p[class*='text-[#262626]")
    private List<WebElement> bannerWrapersList;
    @FindBy(xpath = "//a[@data-ga-ea='nav-links - Urządzenia/Smartwatche i opaski/Bez abonamentu']")
    private WebElement smartwatchesAndBandsWithoutSubscriptionHref;
    @FindBy(css = "[data-qa='HDR_HomeIcon']")
    private WebElement homeBtn;
    @FindBy(css = "[title='Koszyk'] svg")
    private WebElement basketIcon;
    @FindBy(css = ".rounded-full")
    private WebElement basketProductCount;

    public T moveToDevices() {
        Log.info("Move mouse to devices banner");
        moveToElement(devicesBtn);
        checkBannerWrappersAreDisplayed();
        return (T) this;
    }

    @SneakyThrows
    private void checkBannerWrappersAreDisplayed() {
        if (bannerWrapersList.isEmpty()) throw new Exception("Wrong selector in banner wrappers list");
        List<String> logsList = bannerWrapersList.stream()
                .filter(this::isDisplayed)
                .map(element -> "Element: '" + getWebElementText(element) + "' is visible")
                .toList();

        logsList.forEach(Log::info);

        if (logsList.isEmpty()) throw new Exception("Devices banner didn't displayed");
        else Log.info("Devices banner was displayed");
    }

    public ProductListPage clickSmartwatchesAndBandsWithoutSubscription() {
        Log.info("Click smartwatches and bands without subscription button");
        clickElement(smartwatchesAndBandsWithoutSubscriptionHref);
        return new ProductListPage(driver);
    }

    public HomePage clickHomeBtn() {
        Log.info("Click go to main page");
        clickElement(homeBtn);
        return new HomePage(driver);
    }

    @SneakyThrows
    public int getNumberOfProductInBasket() {
        if (isDisplayed(basketIcon)) return Integer.parseInt(getWebElementText(basketProductCount));
        else throw new Exception("Basket icon isn't visible");
    }
}
