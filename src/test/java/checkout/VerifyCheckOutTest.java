package checkout;

import baseTests.BaseTest;
import org.junit.jupiter.api.*;
import pages.*;
import utils.CartProduct;
import utils.JsonUtils;
import utils.ProductData;
import utils.UserData;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyCheckOutTest extends BaseTest {
    protected AuthenticationsPage authPage;
    protected CartPage cartPage;
    protected CheckOutPage checkOutPage;
    protected ProductDetailPage productDetailPage;
    protected ProductsPage productsPage;


    @Test
    @DisplayName("Confirm Checkout Crumb")
    @Order(1)
    public void verifyCheckoutCartCrumb(){
        // Navigate to auth page
        authPage = homePage.clickAuthNavigation();

        //set login email
        authPage.setLoginEmail("andrewamy@gmail.com");
        //set login password
        authPage.setLoginPasswordElement("AmyAndrew@1997");

        //Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        //Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigationAfterLogin();

        // Check if the cart is empty
        String emptyCartText = cartPage.getEmptyCartText();
        if (emptyCartText.contains("Cart is empty!")) {
            // If cart is empty, redirect to products page
            productsPage = cartPage.redirectToProducts();

            // Add a product to the cart and get its details
            productsPage.clickAddToCartFilterButton();

            // Navigate back to cart page
            cartPage = productsPage.clickModalContentViewCartButton();

            // Click checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        } else {
            // If cart is not empty, proceed to checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        }

        String breadCrumbText = checkOutPage.getCheckOutBreadCrumb();
        assertThat("The page breadcrumb should read 'Checkout'", breadCrumbText, equalToIgnoringCase("Checkout"));
        checkOutPage.clickLogout();
    }

    @Test
    @DisplayName("Verify Address Header Text ")
    @Order(2)
    public void verifyAddressSectionHeaderText(){
        // Navigate to auth page
        authPage = homePage.clickAuthNavigation();

        //set login email
        authPage.setLoginEmail("andrewamy@gmail.com");
        //set login password
        authPage.setLoginPasswordElement("AmyAndrew@1997");

        //Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        //Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigationAfterLogin();

        // Check if the cart is empty
        String emptyCartText = cartPage.getEmptyCartText();
        if (emptyCartText.contains("Cart is empty!")) {
            // If cart is empty, redirect to products page
            productsPage = cartPage.redirectToProducts();

            // Add a product to the cart and get its details
            productsPage.clickAddToCartFilterButton();

            // Navigate back to cart page
            cartPage = productsPage.clickModalContentViewCartButton();

            // Click checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        } else {
            // If cart is not empty, proceed to checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        }

        String addressSectionHeader = checkOutPage.getAddressSectionTitle();
        assertThat("The first section should read 'Address Details'", addressSectionHeader, equalToIgnoringCase("Address Details"));
        checkOutPage.clickLogout();
    }

    @Test
    @DisplayName("Verify Delivery Address Data")
    @Order(3)
    public void verifyDeliveryAddressDataText(){
        // Read user data from auth_data.json
        List<Map<String, Object>> users = JsonUtils.readJsonFile("src/main/resources/data/auth_data.json");

        // Pick a random user
        Random random = new Random();
        Map<String, Object> randomUser = users.get(random.nextInt(users.size()));

        // Create a UserData object from the random user
        UserData userData = new UserData(
                (String) randomUser.get("title") + "." + " " + (String) randomUser.get("first_name") + " " + (String) randomUser.get("last_name"), // Include title in name
                (String) randomUser.get("email"),
                (String) randomUser.get("title"),
                (String) randomUser.get("first_name"),
                (String) randomUser.get("last_name"),
                (String) randomUser.get("address"),
                (String) randomUser.get("country"),
                (String) randomUser.get("state"),
                (String) randomUser.get("city"),
                (String) randomUser.get("zip_code"),
                (String) randomUser.get("phone_number")
        );

        // Navigate to auth page
        authPage = homePage.clickAuthNavigation();

        //set login email
        authPage.setLoginEmail(userData.getEmail());
        //set login password
        authPage.setLoginPasswordElement((String) randomUser.get("password"));

        //Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        //Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigationAfterLogin();

        // Check if the cart is empty
        String emptyCartText = cartPage.getEmptyCartText();
        if (emptyCartText.contains("Cart is empty!")) {
            // If cart is empty, redirect to products page
            productsPage = cartPage.redirectToProducts();

            // Add a product to the cart and get its details
            productsPage.clickAddToCartFilterButton();

            // Navigate back to cart page
            cartPage = productsPage.clickModalContentViewCartButton();

            // Click checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        } else {
            // If cart is not empty, proceed to checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        }

        // Retrieve delivery address from the website

        String deliveryName = checkOutPage.getDeliveryName(); // Includes title (e.g., "Mrs. Amy Andrew")
        String deliveryAddress = checkOutPage.getDeliveryAddress(); // Full address
        String deliveryCityStateZip = checkOutPage.getDeliveryCityStateZip(); // e.g., "Montreal Quebec T5N 0I9"
        String deliveryCountry = checkOutPage.getDeliveryCountry(); // e.g., "Canada"
        String deliveryPhone = checkOutPage.getDeliveryPhone(); // e.g., "7603218970"

        // Split city, state, and zip code
        String[] cityStateZipParts = deliveryCityStateZip.split(" ");
        String deliveryCity = cityStateZipParts[0]; // e.g., "Montreal"
        String deliveryState = cityStateZipParts[1]; // e.g., "Quebec"
        String deliveryZipCode = cityStateZipParts[2] + " " + cityStateZipParts[3]; // e.g., "T5N 0I9"

        // Retrieve delivery address from the website
        UserData deliveryAddressData = new UserData(
                deliveryName,
                userData.getEmail(),
                userData.getTitle(),
                userData.getFirstName(),
                userData.getLastName(),
                deliveryAddress,
                deliveryCountry,
                deliveryState,
                deliveryCity,
                deliveryZipCode,
                deliveryPhone
        );

        // Assert that the delivery address matches the user data
        assertThat("The delivery address should match the user's address in auth_data.json",
                deliveryAddressData, equalTo(userData));

        checkOutPage.clickLogout();
    }

    @Test
    @DisplayName("Verify Billing Address Data")
    @Order(4)
    public void verifyBillingAddressDataText(){
        // Read user data from auth_data.json
        List<Map<String, Object>> users = JsonUtils.readJsonFile("src/main/resources/data/auth_data.json");

        // Pick a random user
        Random random = new Random();
        Map<String, Object> randomUser = users.get(random.nextInt(users.size()));

        // Create a UserData object from the random user
        UserData userData = new UserData(
                (String) randomUser.get("name"),
                (String) randomUser.get("email"),
                (String) randomUser.get("title"),
                (String) randomUser.get("first_name"),
                (String) randomUser.get("last_name"),
                (String) randomUser.get("address"),
                (String) randomUser.get("country"),
                (String) randomUser.get("state"),
                (String) randomUser.get("city"),
                (String) randomUser.get("zip_code"),
                (String) randomUser.get("phone_number")
        );

        // Navigate to auth page
        authPage = homePage.clickAuthNavigation();

        //set login email
        authPage.setLoginEmail(userData.getEmail());
        //set login password
        authPage.setLoginPasswordElement((String) randomUser.get("password"));

        //Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        //Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigationAfterLogin();

        // Check if the cart is empty
        String emptyCartText = cartPage.getEmptyCartText();
        if (emptyCartText.contains("Cart is empty!")) {
            // If cart is empty, redirect to products page
            productsPage = cartPage.redirectToProducts();

            // Add a product to the cart and get its details
            productsPage.clickAddToCartFilterButton();

            // Navigate back to cart page
            cartPage = productsPage.clickModalContentViewCartButton();

            // Click checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        } else {
            // If cart is not empty, proceed to checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        }

        // Retrieve billing address from the website
        UserData billingAddress = new UserData(
                checkOutPage.getInvoiceName(),
                userData.getEmail(),
                userData.getTitle(),
                userData.getFirstName(),
                userData.getLastName(),
                checkOutPage.getInvoiceAddress(),
                checkOutPage.getInvoiceCountry(),
                userData.getState(),
                userData.getCity(),
                checkOutPage.getInvoicePostal(),
                checkOutPage.getInvoicePhone()
        );

        // Assert that the billing address matches the user data
        assertThat("The billing address should match the user's address in auth_data.json",
                billingAddress, equalTo(userData));

        checkOutPage.clickLogout();
    }

    @Test
    @DisplayName("Confirm Order Section Header")
    @Order(5)
    public void verifyOrderSectionHeaderText(){
        // Navigate to auth page
        authPage = homePage.clickAuthNavigation();

        //set login email
        authPage.setLoginEmail("andrewamy@gmail.com");
        //set login password
        authPage.setLoginPasswordElement("AmyAndrew@1997");

        //Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        //Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigationAfterLogin();

        // Check if the cart is empty
        String emptyCartText = cartPage.getEmptyCartText();
        if (emptyCartText.contains("Cart is empty!")) {
            // If cart is empty, redirect to products page
            productsPage = cartPage.redirectToProducts();

            // Add a product to the cart and get its details
            productsPage.clickAddToCartFilterButton();

            // Navigate back to cart page
            cartPage = productsPage.clickModalContentViewCartButton();

            // Click checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        } else {
            // If cart is not empty, proceed to checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        }

        String orderSectionHeader = checkOutPage.getOrderSectionTitle();
        assertThat("The second section should read 'Review Your Order'", orderSectionHeader, equalToIgnoringCase("Review Your Order"));
        checkOutPage.clickLogout();
    }

    @Test
    @DisplayName("Verify Products in Order Section")
    @Order(6)
    public void verifyProductsInOrderSection(){
        // Navigate to auth page
        authPage = homePage.clickAuthNavigation();

        // Set login email and password
        authPage.setLoginEmail("andrewamy@gmail.com");
        authPage.setLoginPasswordElement("AmyAndrew@1997");

        // Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        // Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigationAfterLogin();

        // Check if the cart is empty
        String emptyCartText = cartPage.getEmptyCartText();
        if (emptyCartText.contains("Cart is empty!")) {
            // If cart is empty, redirect to products page
            productsPage = cartPage.redirectToProducts();
        }
        else {
            // If cart is not empty, proceed to checkout
            cartPage.deleteAllProductsInCart();
            productsPage = cartPage.redirectToProductsPage();
        }

        // Add multiple products to the cart with different quantities
        List<ProductData> addedProducts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            productDetailPage = productsPage.clickViewProductButton();
            String quantity = i == 0 ? "1" : String.valueOf(i + 2); // Set different quantities
            productDetailPage.changeProductQuantity(quantity);
            CartProduct cartProduct = productDetailPage.clickAddToCartButton();

            // Calculate the total for the product
            double price = Double.parseDouble(cartProduct.getPrice().replace("Rs. ", ""));
            int productQuantity = Integer.parseInt(cartProduct.getQuantity());
            double total = price * productQuantity;

            // Store the product data
            addedProducts.add(new ProductData(cartProduct.getName(), cartProduct.getPrice(), productQuantity, total));

            // Navigate back to the products page to add another product
            productsPage = cartPage.redirectToProductsPage();
        }

        // Navigate back to cart page
        cartPage = productsPage.clickAddToCartNavMenu();

        // Proceed to checkout
        checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();

        // Get product details from the order section
        List<String> productNames = checkOutPage.getProductNames();
        List<String> productPrices = checkOutPage.getProductPrices();
        List<String> productQuantities = checkOutPage.getProductQuantity();
        List<String> productTotalPrices = checkOutPage.getProductTotalPrice();

        // Verify the products in the order section
        for (int i = 0; i < productNames.size(); i++) {
            ProductData orderProduct = new ProductData(
                    productNames.get(i),
                    productPrices.get(i),
                    Integer.parseInt(productQuantities.get(i)),
                    Double.parseDouble(productTotalPrices.get(i).replace("Rs. ", ""))
            );

            // Find the corresponding added product
            ProductData addedProduct = addedProducts.stream()
                    .filter(p -> p.equals(orderProduct))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Product not found in added products: " + orderProduct.getName()));

            // Assert that the product details match
            assertThat("The product details in the order section should match the added product",
                    orderProduct, equalTo(addedProduct));
            System.out.println("Assertion Done");
        }
        // Ensure the logout button is visible before clicking it
        if (checkOutPage.isLogoutButtonVisible()) {
            checkOutPage.clickLogout();
            System.out.println("Logout Done");
        } else {
            throw new IllegalStateException("Logout button is not visible. User might not be logged in.");
        }
    }

    @Test
    @DisplayName("Verify Total Amount in Order Section")
    @Order(7)
    public void verifyTotalAmountOfProductsInOrderSection(){
        // Navigate to auth page
        authPage = homePage.clickAuthNavigation();

        // Set login email and password
        authPage.setLoginEmail("andrewamy@gmail.com");
        authPage.setLoginPasswordElement("AmyAndrew@1997");

        // Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        // Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigationAfterLogin();

        // Check if the cart is empty
        String emptyCartText = cartPage.getEmptyCartText();
        if (emptyCartText.contains("Cart is empty!")){
            //If cart is empty, redirect to products page
            productsPage = cartPage.redirectToProductsPage();
        }
        else  {
            // If cart is not empty, delete all products in cart and redirect to products page
            cartPage.deleteAllProductsInCart();
            productsPage = cartPage.redirectToProductsPage();
        }

        // Add multiple products to the cart with different quantities
        List<ProductData> addedProducts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            productDetailPage = productsPage.clickViewProductButton();
            String quantity = i == 0 ? "1" : String.valueOf(i + 2); // Set different quantities
            productDetailPage.changeProductQuantity(quantity);
            CartProduct cartProduct = productDetailPage.clickAddToCartButton();

            // Calculate the total for the product
            double price = Double.parseDouble(cartProduct.getPrice().replace("Rs. ", ""));
            int productQuantity = Integer.parseInt(cartProduct.getQuantity());
            double total = price * productQuantity;

            // Store the product data
            addedProducts.add(new ProductData(cartProduct.getName(), cartProduct.getPrice(), productQuantity, total));

            // Navigate back to the products page to add another product
            productsPage = cartPage.redirectToProductsPage();
        }

        // Navigate back to cart page
        cartPage = productsPage.clickAddToCartNavMenu();

        // Proceed to checkout
        checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();



            // Verify the total amount
            double expectedOverallTotal = addedProducts.stream()
                    .mapToDouble(ProductData::getTotal)
                    .sum();

            double actualOverallTotal = Double.parseDouble(checkOutPage.getTotalAmount().replace("Rs. ", ""));

            assertThat("The total amount should be correct", actualOverallTotal, equalTo(expectedOverallTotal));

        // Ensure the logout button is visible before clicking it
        if (checkOutPage.isLogoutButtonVisible()) {
            checkOutPage.clickLogout();
            System.out.println("Logout Done");
        } else {
            throw new IllegalStateException("Logout button is not visible. User might not be logged in.");
        }
        }
}
