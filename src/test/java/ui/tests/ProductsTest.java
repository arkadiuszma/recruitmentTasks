package ui.tests;

import models.ui.Product;
import org.junit.jupiter.api.Test;
import pages.basket.BasketPage;
import pages.home.HomePage;
import pages.product.ProductDetailsPage;
import pages.product.ProductListPage;
import ui.base.BaseUITest;

public class ProductsTest extends BaseUITest {
    private HomePage homePage;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;
    private BasketPage basketPage;

    @Test
    public void shouldChooseSmartphoneFromProductsList() {
        homePage = new HomePage(driver);

        productListPage = homePage.acceptCookies()
                .moveToDevices()
                .clickSmartwatchesAndBandsWithoutSubscription();

        Product productDetailsOnList = productListPage.getProductDetailsByIndex(1);

        productDetailsPage = productListPage.clickProductFromListByIndex(1);

        Product productOnDetailsPage = productDetailsPage.getProductDetails();

        basketPage = productDetailsPage.clickAddToBasketBtn();

        Product productDetailsInBasket = basketPage.getProductDetails();

        int numberOfProductInBasket = basketPage.comebackToProductDetailsPage()
                .clickHomeBtn()
                .getNumberOfProductInBasket();

        s.assertThat(productDetailsOnList).as("Product Details").isEqualTo(productOnDetailsPage);
        s.assertThat(productDetailsOnList).as("Product Details").isEqualTo(productDetailsInBasket);
        s.assertThat(productDetailsInBasket).as("Product Details").isEqualTo(productOnDetailsPage);
        s.assertThat(numberOfProductInBasket).as("Number Of Products In Basket").isEqualTo(1);
        s.assertAll();
    }
}