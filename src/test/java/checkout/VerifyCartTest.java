package checkout;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.StaleElementReferenceException;
import pages.*;
import utils.CartProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyCartTest extends BaseTest {
    protected AuthenticationsPage authPage;
    protected CartPage cartPage;
    protected CheckOutPage checkOutPage;
    protected ProductsPage productsPage;
    protected ProductDetailPage productDetailPage;

    Faker faker = new Faker(Locale.of("en", "CA"));

    @BeforeEach
    public void cleanUpCart(){
        // Ensure clean state before each test
        cartPage = homePage.clickCartNavigation();
        cartPage.deleteAllProductsInCart();
    }

    @Test
    @DisplayName("Confirm Page Header")
    @Order(1)
    public void verifyShoppingCartCrumb(){
        cartPage = homePage.clickCartNavigation();
        String breadCrumbText = cartPage.getBreadCrumb();
        assertThat("The page breadcrumb should read 'Shopping Cart'", breadCrumbText, equalToIgnoringCase("Shopping cart"));
    }

    @Test
    @DisplayName("Confirm Empty Cart")
    @Order(2)
    public void verifyShoppingCartIsEmpty(){
        cartPage = homePage.clickCartNavigation();
        String emptyCartText = cartPage.getEmptyCartText();
        assertThat("The empty cart page should read 'Cart is empty! Click here to buy products.'", emptyCartText, containsStringIgnoringCase("Cart is empty!"));
    }

    @Test
    @DisplayName("Redirects to Product Page Successful")
    @Order(3)
    public void verifyRedirectToProductsPage(){
        cartPage = homePage.clickCartNavigation();
        productsPage = cartPage.redirectToProducts();
        String productHeaderText = productsPage.pageHeaderTextValue();
        assertThat("The product redirect links works",productHeaderText,equalToIgnoringCase("All Products"));
    }

    @Test
    @DisplayName("Add To Cart from Products Page")
    @Order(4)
    public void verifyProductsAddedToCartFromProductsPage(){
        cartPage = homePage.clickCartNavigation();
        productsPage = cartPage.redirectToProducts();
        // Add a product to the cart and get its details
        ProductsPage.Product addedProduct = productsPage.clickAddToCartButton();

        // Navigate back to cart page
        cartPage = productsPage.clickModalContentViewCartButton();

        // Get the product details from the cart
        List<CartProduct> cartProducts = cartPage.getProductDetailsInCart();

        // Verify that the added product is in the cart with the correct details
        boolean productFound = false;
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getName().equalsIgnoreCase(addedProduct.name)) {
                assertThat("The product details in the cart should match the added product", cartProduct.toString(),
                        equalToIgnoringCase(new CartProduct(addedProduct.name, addedProduct.price, "1").toString()));
                productFound = true;
                break;
            }
        }
        assertThat("The added product should be found in the cart", productFound, is(true));
    }

    @Test
    @DisplayName("Add To Cart From Products Details Page")
    @Order(5)
    public void verifyProductAddedToCartFromProductDetailsPage() {
        try {
            // Navigate to the products page
            cartPage = homePage.clickCartNavigation();
            productsPage = cartPage.redirectToProducts();

            // Click on a product to view its details
            productDetailPage = productsPage.clickViewProductButton();

            // Add small delay to ensure page is stable
            Thread.sleep(1000);

            // Add the product to the cart and get its details
            CartProduct addedProduct = productDetailPage.clickAddToCartButton();

            // Add small delay to allow cart to update
            Thread.sleep(1000);

            // Navigate back to the cart page
            cartPage = productDetailPage.clickModalContentViewCartButton();

            // Add small delay to allow cart page to load
            Thread.sleep(1000);

            // Get the product details from the cart
            List<CartProduct> cartProducts = cartPage.getProductDetailsInCart();
            System.out.println("Added product: " + addedProduct);
            System.out.println("Cart contains: " + cartProducts);

            // More flexible matching
            boolean productFound = cartProducts.stream()
                    .anyMatch(p -> p.getName().equalsIgnoreCase(addedProduct.getName())
                            && p.getPrice().trim().equalsIgnoreCase(addedProduct.getPrice().trim()));

            assertThat("The added product should be found in the cart", productFound, is(true));
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Test interrupted: "+ e.getMessage());
        }
    }

    @Test
    @DisplayName("Add More than One Product to Cart")
    @Order(6)
    public void verifyMoreThanOneProductAddedToCart(){
        try{
        // Generate a random quantity between 1 and 9
        String newQuantity = String.valueOf(faker.number().numberBetween(2, 9));

        // Navigate to the products page
        cartPage = homePage.clickCartNavigation();
        productsPage = cartPage.redirectToProducts();

        // Click on a product to view its details
        productDetailPage = productsPage.clickViewProductButton();
        Thread.sleep(1000);

        // Get product details before changing quantity
        String productName = productDetailPage.getProductName();
        String productPrice = productDetailPage.getProductPrice();

        // Change the product quantity
        productDetailPage.changeProductQuantity(newQuantity);

        // Add small delay to ensure quantity is updated
        Thread.sleep(1000);

        // Add the product to the cart and get its details
        productDetailPage.clickAddToCart();

        // Add delay to allow cart to update
        Thread.sleep(1000);

        // Navigate back to the cart page
        cartPage = productDetailPage.clickModalContentViewCartButton();

        // Add delay to allow cart page to load
        Thread.sleep(1000);

        // Get the product details from the cart
        List<CartProduct> cartProducts = cartPage.getProductDetailsInCart();

            // Debug output
            System.out.println("Searching for product: " + productName);
            System.out.println("Cart contains " + cartProducts.size() + " items:");
            cartProducts.forEach(p -> System.out.println(" - " + p.getName()));


            // Verify the product is in the cart
            boolean productFound = cartProducts.stream()
                    .anyMatch(p -> p.getName().equalsIgnoreCase(productName));

            assertThat("The added product should be found in the cart", productFound, is(true));

            // Additional verification for quantity
            CartProduct cartProduct = cartProducts.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(productName))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Product not found in cart"));

            assertThat("Quantity should match", cartProduct.getQuantity(), equalTo(newQuantity));
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            fail("Test interrupted: "+e.getMessage());
        }
    }

    @Test
    @DisplayName("Delete a product from cart")
    @Order(7)
    public void verifyCartDeleteButtonFunctionality() {
        try {
            // Navigate to the products page
            cartPage = homePage.clickCartNavigation();

            // First ensure cart is empty
            cartPage.deleteAllProductsInCart();

            productsPage = cartPage.redirectToProducts();

            // Add multiple products to the cart with explicit quantity 1
            List<CartProduct> addedProducts = new ArrayList<>();
            for (int i = 0; i < 3; i++) { // Add 3 products to the cart
                productDetailPage = productsPage.clickViewProductButton();

                // Explicitly set quantity to 1
                productDetailPage.changeProductQuantity("1");

                // Add small delay to ensure quantity is updated
                Thread.sleep(500);

                // Get the product details as CartProduct
                CartProduct addedProduct = productDetailPage.clickAddToCartButton();
                addedProducts.add(addedProduct);

                productsPage.clickModalFooterContinueButton();

                // Navigate back to the products page to add another product
                productsPage = cartPage.redirectToProductsPage();
            }

            // Navigate back to the cart page
            cartPage = productsPage.clickAddToCartNavMenu();

            // Wait for cart to load
            Thread.sleep(1000);

            // Get the product details from the cart before deletion
            List<CartProduct> cartProductsBeforeDeletion = cartPage.getProductDetailsInCart();
            assertThat("Cart should contain exactly 3 items before deletion",
                    cartProductsBeforeDeletion.size(), equalTo(3));

            // Verify quantities are all 1
            for (CartProduct cartProduct : cartProductsBeforeDeletion) {
                assertThat("Each product should have quantity 1",
                        cartProduct.getQuantity(), equalTo("1"));
            }

            // Rest of the test remains the same...
            List<String> productNamesBeforeDeletion = new ArrayList<>();
            for (CartProduct cartProduct : cartProductsBeforeDeletion) {
                productNamesBeforeDeletion.add(cartProduct.getName());
            }

            // Delete a random product from the cart
            cartPage.clickDeleteRandomProductButton();

            // Wait for deletion to complete
            Thread.sleep(1000);

            // Get the product details from the cart after deletion
            List<CartProduct> cartProductsAfterDeletion = cartPage.getProductDetailsInCart();
            assertThat("Cart should contain exactly 2 items after deletion",
                    cartProductsAfterDeletion.size(), equalTo(2));

            // Verify remaining products still have quantity 1
            for (CartProduct cartProduct : cartProductsAfterDeletion) {
                assertThat("Remaining products should have quantity 1",
                        cartProduct.getQuantity(), equalTo("1"));
            }

            // Store the names of all products after deletion
            List<String> productNamesAfterDeletion = new ArrayList<>();
            for (CartProduct cartProduct : cartProductsAfterDeletion) {
                productNamesAfterDeletion.add(cartProduct.getName());
            }

            // Find the deleted product by comparing the lists
            String deletedProductName = null;
            for (String productName : productNamesBeforeDeletion) {
                if (!productNamesAfterDeletion.contains(productName)) {
                    deletedProductName = productName;
                    break;
                }
            }

            // Verify that the deleted product is no longer in the cart
            assertThat("The deleted product should not be found in the cart after deletion",
                    deletedProductName, is(notNullValue()));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Test interrupted: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Confirm Checkout Modal Header")
    @Order(8)
    public void verifyCheckoutModalHeaderText(){
        cartPage = homePage.clickCartNavigation();
        productsPage = cartPage.redirectToProducts();
        // Add a product to the cart and get its details
        productsPage.clickAddToCartFilterButton();

        // Navigate back to cart page
        cartPage = productsPage.clickModalContentViewCartButton();

        // Click checkout
        cartPage.clickProceedToCheckout();
        String modalTitle = cartPage.getModalTitle();
        assertThat("The Checkout Modal Title reads 'Checkout'", modalTitle, equalToIgnoringCase("Checkout"));
    }

    @Test
    @DisplayName("Confirm Checkout Modal Body")
    @Order(9)
    public void verifyCheckoutModalBodyText(){
        cartPage = homePage.clickCartNavigation();
        productsPage = cartPage.redirectToProducts();
        // Add a product to the cart and get its details
        productsPage.clickAddToCartFilterButton();

        // Navigate back to cart page
        cartPage = productsPage.clickModalContentViewCartButton();

        // Click checkout
        cartPage.clickProceedToCheckout();
        String modalBodyText = cartPage.modalContentBodyText();
        assertThat("The Checkout Modal Body Text should contains 'Register / Login account to proceed on checkout.'", modalBodyText, containsStringIgnoringCase("Register / Login account"));
    }

    @Test
    @DisplayName("Confirm Checkout Modal Footer")
    @Order(10)
    public void verifyCheckoutModalFooterText(){
        cartPage = homePage.clickCartNavigation();
        productsPage = cartPage.redirectToProducts();
        // Add a product to the cart and get its details
        productsPage.clickAddToCartFilterButton();

        // Navigate back to cart page
        cartPage = productsPage.clickModalContentViewCartButton();

        // Click checkout
        cartPage.clickProceedToCheckout();
        String modalFooterText = cartPage.modalContentFooterText();
        assertThat("The Checkout Modal Footer Text should read 'Continue On Cart'", modalFooterText, equalToIgnoringCase("Continue On Cart"));
    }

    @Test
    @DisplayName("Confirm Checkout Modal Body Login Button")
    @Order(11)
    public void verifyLoginRedirectWithLoginButtonClick(){
        cartPage = homePage.clickCartNavigation();
        productsPage = cartPage.redirectToProducts();
        // Add a product to the cart and get its details
        productsPage.clickAddToCartFilterButton();

        // Navigate back to cart page
        cartPage = productsPage.clickModalContentViewCartButton();

        // Click checkout
        cartPage.clickProceedToCheckout();
        authPage = cartPage.clickModalContentAuthButton();
        String loginHeaderText = authPage.getLoginTitleText();
        assertThat("The Login Section Header should read 'Login'", loginHeaderText, containsStringIgnoringCase("Login"));
    }

    @Test
    @DisplayName("Confirm Page remains in Cart after clicking Continue")
    @Order(12)
    public void verifyCheckoutModalFooterContinueButton(){
        cartPage = homePage.clickCartNavigation();
        productsPage = cartPage.redirectToProducts();
        // Add a product to the cart and get its details
        productsPage.clickAddToCartFilterButton();

        // Navigate back to cart page
        cartPage = productsPage.clickModalContentViewCartButton();

        // Click checkout
        cartPage.clickProceedToCheckout();
        cartPage.clickModalFooterContinueButton();
        String currentUrl = cartPage.getCurrentUrl();
        assertThat("The Cart Page Url should contain 'Login'", currentUrl, containsStringIgnoringCase("view_cart"));
    }

    @Test
    @DisplayName("Confirm Checkout Process Pre Login")
    @Order(13)
    public void verifyCheckOutProcessPreLogin(){
        try{
            cartPage = homePage.clickCartNavigation();
            productsPage = cartPage.redirectToProducts();
            // Add a product to the cart and get its details
            productsPage.clickAddToCartFilterButton();

            // Navigate back to cart page
            cartPage = productsPage.clickModalContentViewCartButton();

            // Click checkout
            cartPage.clickProceedToCheckout();
            authPage = cartPage.clickModalContentAuthButton();
            authPage.setLoginEmail("andrewamy@gmail.com");
            authPage.setLoginPasswordElement("AmyAndrew@1997");
            homePage = authPage.clickLogin();
            // Wait for login to complete
            Thread.sleep(2000);

            // Use retry mechanism for cart navigation
            cartPage = retryOnStale(() -> homePage.clickCartNavigationAfterLogin());
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
            String checkOutCrumb = checkOutPage.getCheckOutBreadCrumb();
            assertThat("Verify page breadcrumb reads 'Checkout'", checkOutCrumb, equalToIgnoringCase("Checkout"));
            checkOutPage.clickLogout();
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Confirm Checkout Process Post Login")
    @Order(14)
    public void verifyCheckOutProcessPostLogin(){
        try{
            // Navigate to auth page
            authPage = homePage.clickAuthNavigation();

            //set login email
            authPage.setLoginEmail("andrewamy@gmail.com");
            //set login password
            authPage.setLoginPasswordElement("AmyAndrew@1997");

            //Navigate to homepage from auth page
            homePage = authPage.clickLogin();

            // Wait for login to complete and page to stabilize
            Thread.sleep(2000);

            //Navigate to cart page from homepage
            cartPage = retryOnStale(() ->  homePage.clickCartNavigationAfterLogin());

            // Check if the cart is empty
            String emptyCartText = cartPage.getEmptyCartText();
            if (emptyCartText.contains("Cart is empty!")) {
                // If cart is empty, redirect to products page
                productsPage = cartPage.redirectToProducts();

                // Add a product to the cart and get its details
                productsPage.clickAddToCartFilterButton();

                // Navigate back to cart page
                cartPage = productsPage.clickModalContentViewCartButton();

                Thread.sleep(1000);
            }
            // If cart is not empty, proceed to checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();

            // Get checkout page crumb text
            String checkOutCrumb = checkOutPage.getCheckOutBreadCrumb();

            // Assert crumb text
            assertThat("Verify page breadcrumb reads 'Checkout'", checkOutCrumb, equalToIgnoringCase("Checkout"));
            checkOutPage.clickLogout();
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    private <T> T retryOnStale(Supplier<T> action) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                return action.get();
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts >= 3) throw e;
                try { Thread.sleep(1000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
        throw new RuntimeException("Failed after retries");
    }
}
