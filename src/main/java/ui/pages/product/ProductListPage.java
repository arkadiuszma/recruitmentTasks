package ui.pages.product;

import logging.Log;
import ui.models.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.pages.base.NavigationAbstractPage;

import java.util.List;

public class ProductListPage extends NavigationAbstractPage<ProductListPage> {
    public ProductListPage(WebDriver driver) {
        super(driver);
        checkHeaderTitle("Urządzenia");
        checkProductsAreDisplayed();
    }

    @FindBy(css = "[class*='dt_productCards']")
    private List<WebElement> productsList;

    private List<ProductRowPage> getDevicesRows() {
        return productsList.stream()
                .map(device -> new ProductRowPage(driver, device))
                .toList();
    }

    public Product getProductDetailsByIndex(int productIndex) {
        Log.info("Get product details from products list with index: " + productIndex);
        return getDevicesRows().get(productIndex - 1).getProductDetails();
    }

    public ProductDetailsPage clickProductFromListByIndex(int productIndex) {
        Log.info("Click on product with index: " + productIndex);
        return getDevicesRows().get(productIndex - 1).clickOnProduct();
    }

    private void checkProductsAreDisplayed() {
        Log.info("Check products are displayed in list");
        assert !getDevicesRows().isEmpty();
    }
}
