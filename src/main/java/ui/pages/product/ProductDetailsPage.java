package ui.pages.product;

import logging.Log;
import ui.models.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.pages.base.NavigationAbstractPage;
import ui.pages.basket.BasketPage;

public class ProductDetailsPage extends NavigationAbstractPage<ProductDetailsPage> {
    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        checkPageTitle("strona produktu, cena, opis urządzenia | T-Mobile");
    }

    @FindBy(xpath = "(//button[@data-qa='PRD_AddToBasket'])[2]")
    private WebElement addToBasketBtn;
    @FindBy(css = "h1")
    private WebElement productName;
    @FindBy(xpath = "(//div[contains(@class, 'StyledPriceInfo')]//div[contains(@class, 'dt_price_change')])[3]")
    private WebElement startPrice;
    @FindBy(xpath = "(//div[contains(@class, 'priceRightSection')]//div[contains(@class, 'dt_price_change')])[2]")
    private WebElement monthlyPayment;


    public BasketPage clickAddToBasketBtn() {
        Log.info("Click add to basket btn");
        clickElement(addToBasketBtn);
        return new BasketPage(driver);
    }

    public Product getProductDetails() {
        Log.info("Get product from product details page");
        return new Product(getWebElementText(productName), getWebElementText(startPrice), getWebElementText(monthlyPayment));
    }
}
