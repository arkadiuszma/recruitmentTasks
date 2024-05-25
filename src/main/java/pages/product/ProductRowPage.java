package pages.product;

import models.ui.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

public class ProductRowPage extends BasePage {

    public ProductRowPage(WebDriver driver, WebElement element) {
        super(driver, element);
    }

    @FindBy(css = "h2")
    private WebElement productName;
    @FindBy(css = ".leftText .actualText")
    private WebElement startPrice;
    @FindBy(css = ".rightText .actualText")
    private WebElement monthlyPayment;
    @FindBy(css = ".dt_productCards a")
    private WebElement productChooserBtn;


    public Product getProductDetails() {
        return new Product(getWebElementText(productName), getWebElementText(startPrice), getWebElementText(monthlyPayment));
    }

    public ProductDetailsPage clickOnProduct() {
        clickElement(productChooserBtn);
        return new ProductDetailsPage(driver);
    }
}
