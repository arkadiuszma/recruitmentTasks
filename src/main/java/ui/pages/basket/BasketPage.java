package ui.pages.basket;

import logging.Log;
import ui.models.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.pages.base.BasePage;
import ui.pages.product.ProductDetailsPage;

public class BasketPage extends BasePage {
    public BasketPage(WebDriver driver) {
        super(driver);
        checkHeaderTitle("Twój koszyk");
    }

    @FindBy(css = "h2")
    private WebElement productName;
    @FindBy(xpath = "//div[@data-qa='BKT_TotalupFrontCurrCOde']")
    private WebElement startPrice;
    @FindBy(xpath = "//div[@data-qa='BKT_TotalMonthlyCurrCOde']")
    private WebElement monthlyPayment;
    @FindBy(css = "[data-qa='BKT_back']")
    private WebElement comebackBtn;

    public Product getProductDetails() {
        Log.info("Get product details from basket");
        return new Product(getWebElementText(productName), getWebElementText(startPrice), getWebElementText(monthlyPayment));
    }

    public ProductDetailsPage comebackToProductDetailsPage() {
        Log.info("Click comeback btn");
        clickElement(comebackBtn);
        return new ProductDetailsPage(driver);
    }
}
