package ui.step_definitions;

import config.ConfigManager;
import config.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import logging.Log;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import ui.models.Product;
import ui.pages.basket.BasketPage;
import ui.pages.home.HomePage;
import ui.pages.product.ProductDetailsPage;
import ui.pages.product.ProductListPage;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductsTestStepDef {
    private HomePage homePage;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;
    private BasketPage basketPage;
    private Product productDetailsOnList;
    private Product productDetailsInBasket;
    private Product productOnDetailsPage;
    private int numberOfProductInBasket;
    private final SoftAssertions s = new SoftAssertions();
    private WebDriver driver;

    @Before
    public void setupDriver() {
        ConfigManager.getInstance();
        driver = new DriverFactory().getDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
        Log.info("Browser was closed");
    }

    @Given("User open T-Mobile home page")
    public void openHomePage() {
        homePage = new HomePage(driver);

        homePage.acceptCookies();
    }

    @When("User choose devices option and click smartwatches and bands without subscription")
    public void chooseDevicesBanner() {
        productListPage = homePage.moveToDevices()
                .clickSmartwatchesAndBandsWithoutSubscription();
    }

    @When("User choose product from list with index: {int}")
    public void chooseProductFromListWithIndex(int index) {
        productDetailsOnList = productListPage.getProductDetailsByIndex(index);
        productDetailsPage = productListPage.clickProductFromListByIndex(index);
    }

    @When("User click add to basket button")
    public void clickAddToBasket() {
        productOnDetailsPage = productDetailsPage.getProductDetails();
        basketPage = productDetailsPage.clickAddToBasketBtn();
        productDetailsInBasket = basketPage.getProductDetails();
    }

    @When("User go back to home page")
    public void goBackToHomePage() {
        numberOfProductInBasket = basketPage.comebackToProductDetailsPage()
                .clickHomeBtn()
                .getNumberOfProductInBasket();
    }

    @Then("Verify product on each page had same parameters: name, monthly cost, start price")
    public void verifyProductsData() {
        Log.info("Verify product parameters");
        s.assertThat(productDetailsOnList).as("Product Details").isEqualTo(productOnDetailsPage);
        s.assertThat(productDetailsOnList).as("Product Details").isEqualTo(productDetailsInBasket);
        s.assertThat(productDetailsInBasket).as("Product Details").isEqualTo(productOnDetailsPage);
        s.assertAll();
    }

    @Then("Verify number of product shown on basket icon is: {int}")
    public void verifyNumberOfProductsInBasket(int numberOfProducts) {
        Log.info("Verify number of product shown on basket icon is: " + numberOfProducts);
        assertThat(numberOfProductInBasket).as("Number Of Products In Basket").isEqualTo(numberOfProducts);
    }
}