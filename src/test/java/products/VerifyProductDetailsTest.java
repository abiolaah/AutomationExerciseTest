package products;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import pages.CartPage;
import pages.ProductDetailPage;
import pages.ProductsPage;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyProductDetailsTest extends BaseTest {
    protected ProductsPage productsPage;
    protected ProductDetailPage productDetailsPage;
    protected CartPage cartPage;

    Faker faker = new Faker(Locale.of("en", "CA"));

    @Test
    @DisplayName("Confirm Product Name is not an Empty Array")
    @Order(1)
    public void verifyProductName(){
        productsPage = homePage.clickProductNavigation();
        productDetailsPage = productsPage.clickViewProductButton();
        String productName = productDetailsPage.getProductName();
        assertThat("The product name should not be empty", productName, is(not(emptyOrNullString())));
    }

    @Test
    @DisplayName("Confirm Product Category is not an Empty Array")
    @Order(2)
    public void verifyProductCategory(){
        productsPage = homePage.clickProductNavigation();
        productDetailsPage = productsPage.clickViewProductButton();
        String productCategory = productDetailsPage.getProductCategory();
        assertThat("The product category should not be empty", productCategory, is(not(emptyOrNullString())));
    }

    @Test
    @DisplayName("Confirm Initial Product Quantity is 1")
    @Order(3)
    public void verifyInitialProductQuantity(){
        productsPage = homePage.clickProductNavigation();
        productDetailsPage = productsPage.clickViewProductButton();
        String productQuantity = productDetailsPage.getProductQuantity();
        assertThat("The product category should not be empty", productQuantity, equalToIgnoringCase("1"));
    }

    @Test
    @DisplayName("Confirm Modal Content Header Text")
    @Order(4)
    public void verifyAddToCartModalHeaderText(){
        productsPage = homePage.clickProductNavigation();
        productDetailsPage = productsPage.clickViewProductButton();
        productDetailsPage.clickAddToCart();
        String modalHeaderText = productDetailsPage.modalContentHeaderText();
        assertThat("The Modal Header Text should contains ''", modalHeaderText, containsStringIgnoringCase(""));
    }

    @Test
    @DisplayName("Confirm Modal Content Body Text")
    @Order(5)
    public void verifyAddToCartModalBodyText(){
        String newQuantity = String.valueOf(faker.number().numberBetween(1,9));
        productsPage = homePage.clickProductNavigation();
        productDetailsPage = productsPage.clickViewProductButton();
        productDetailsPage.changeProductQuantity(newQuantity);
        productDetailsPage.clickAddToCart();
        String modalBodyText = productDetailsPage.modalContentBodyText();
        assertThat("The Modal Body Text should contains ''", modalBodyText, containsStringIgnoringCase(""));
    }

    @Test
    @DisplayName("Confirm Modal Content Footer Text")
    @Order(6)
    public void verifyAddToCartModalFooterText(){
        String newQuantity = String.valueOf(faker.number().numberBetween(1,9));
        productsPage = homePage.clickProductNavigation();
        productDetailsPage = productsPage.clickViewProductButton();
        productDetailsPage.changeProductQuantity(newQuantity);
        productDetailsPage.clickAddToCart();
        String modalFooterText = productDetailsPage.modalContentFooterText();
        assertThat("The Modal Header Text should contains ''", modalFooterText, containsStringIgnoringCase(""));
    }

    @Test
    @DisplayName("Confirm Modal Continue Button")
    @Order(7)
    public void verifyAddToCartModalContinueButton(){
        productsPage = homePage.clickProductNavigation();
        productDetailsPage = productsPage.clickViewProductButton();
        productDetailsPage.clickAddToCart();
        productDetailsPage.clickModalFooterContinueButton();
        String currentUrl = productDetailsPage.getCurrentUrl();
        assertThat("The page is still the product details page after the clicking continue page", currentUrl, containsStringIgnoringCase("product_details"));
    }

    @Test
    @DisplayName("Confirm Modal View Cart Button")
    @Order(8)
    public void verifyAddToCartModalViewCartButton(){
        productsPage = homePage.clickProductNavigation();
        productDetailsPage = productsPage.clickViewProductButton();
        productDetailsPage.clickAddToCart();
        cartPage = productDetailsPage.clickModalContentViewCartButton();
        String shoppingText = cartPage.getBreadCrumb();
        assertThat("The page is the cart page", shoppingText, equalToIgnoringCase("Shopping Cart"));
    }
}
