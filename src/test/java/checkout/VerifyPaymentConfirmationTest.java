package checkout;

import baseTests.DownloadBaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pages.*;
import utils.CartProduct;
import utils.JsonUtils;
import utils.ProductData;
import utils.UserData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class VerifyPaymentConfirmationTest extends DownloadBaseTest {
    protected AuthenticationsPage authPage;
    protected CartPage cartPage;
    protected CheckOutPage checkOutPage;
    protected ProductDetailPage productDetailPage;
    protected ProductsPage productsPage;
    protected PaymentPage paymentPage;
    protected PaymentDonePage paymentDonePage;

    Faker faker = new Faker(Locale.of("en", "CA"));

    @Test
    @DisplayName("Verify Payment Confirmation Section Header")
    @Order(1)
    public void verifyPaymentConfirmationSectionHeader() {
        // Read user data from auth_data.json
        List<Map<String, Object>> users = JsonUtils.readJsonFile("src/test/resources/auth_data.json");

        // Pick a random user
        Map<String, Object> randomUser = users.get(new Random().nextInt(users.size()));
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

        // Login and proceed to checkout
        authPage = homePage.clickAuthNavigation();
        authPage.setLoginEmail(userData.getEmail());
        authPage.setLoginPasswordElement((String) randomUser.get("password"));
        homePage = authPage.clickLogin();

        // Navigate to cart and add a product
        cartPage = homePage.clickCartNavigation();
        productsPage = cartPage.redirectToProducts();
        addProductToCart();

        // Proceed to checkout
        cartPage = productsPage.clickModalContentViewCartButton();
        checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();

        // Fill payment details
        paymentPage = checkOutPage.clickPlaceOrderButton();
        String cardNumber = faker.finance().creditCard();
        String cvc = String.valueOf(faker.number().numberBetween(100, 999));
        String expiryMonth = String.format("%02d", faker.number().numberBetween(1, 12));
        String expiryYear = String.valueOf(faker.number().numberBetween(2025, 2030));

        paymentPage.setCardName(userData.getName());
        paymentPage.setCardNumber(cardNumber);
        paymentPage.setCardCVC(cvc);
        paymentPage.setCardExpiryMonth(expiryMonth);
        paymentPage.setCardExpiryYear(expiryYear);

        // Submit payment and return the PaymentDonePage
        paymentDonePage = paymentPage.clickSubmitButton();

        // Verify the section header text
        String sectionHeader = paymentDonePage.getSectionTitle();
        assertThat("The section header should match the expected title", sectionHeader, equalToIgnoringCase("Order Placed!"));

        authPage = paymentDonePage.clickLogout();
    }

    @Test
    @DisplayName("Verify Payment Confirmation Section Text")
    @Order(2)
    public void verifyPaymentConfirmationSectionText() {
        // Read user data from auth_data.json
        List<Map<String, Object>> users = JsonUtils.readJsonFile("src/test/resources/auth_data.json");

        // Pick a random user
        Map<String, Object> randomUser = users.get(new Random().nextInt(users.size()));
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

        // Login and proceed to checkout
        authPage = homePage.clickAuthNavigation();
        authPage.setLoginEmail(userData.getEmail());
        authPage.setLoginPasswordElement((String) randomUser.get("password"));
        homePage = authPage.clickLogin();

        // Navigate to cart and add a product
        cartPage = homePage.clickCartNavigation();
        productsPage = cartPage.redirectToProducts();
        addProductToCart();

        // Proceed to checkout
        cartPage = productsPage.clickModalContentViewCartButton();
        checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();

        // Fill payment details
        paymentPage = checkOutPage.clickPlaceOrderButton();
        String cardNumber = faker.finance().creditCard();
        String cvc = String.valueOf(faker.number().numberBetween(100, 999));
        String expiryMonth = String.format("%02d", faker.number().numberBetween(1, 12));
        String expiryYear = String.valueOf(faker.number().numberBetween(2025, 2030));

        paymentPage.setCardName(userData.getName());
        paymentPage.setCardNumber(cardNumber);
        paymentPage.setCardCVC(cvc);
        paymentPage.setCardExpiryMonth(expiryMonth);
        paymentPage.setCardExpiryYear(expiryYear);

        // Submit payment and return the PaymentDonePage
        paymentDonePage = paymentPage.clickSubmitButton();

        // Verify the section text
        List<String> sectionText = paymentDonePage.getSectionText();
        assertThat("The section text should contain the expected confirmation message",
                sectionText, hasItem(containsStringIgnoringCase("Your order has been confirmed!")));

        authPage = paymentDonePage.clickLogout();
    }

    @Test
    @DisplayName("Verify Download Invoice Button download Invoice")
    @Order(4)
    public void verifyInvoiceDownload() throws InterruptedException {
        // Read user data from auth_data.json
        List<Map<String, Object>> users = JsonUtils.readJsonFile("src/test/resources/auth_data.json");

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

        // Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        // Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigation();

        // Navigate to product page from cart page
        productsPage = cartPage.redirectToProducts();

        // Add multiple products to the cart with different quantities
        addProductToCart();

        // Navigate back to cart page
        cartPage = productsPage.clickModalContentViewCartButton();

        // Proceed to checkout
        checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();

        paymentPage = checkOutPage.clickPlaceOrderButton();

        String cardNumber = faker.finance().creditCard();
        String cvc = String.valueOf(faker.number().numberBetween(100, 999));
        String expiryMonth = String.format("%02d", faker.number().numberBetween(1, 12));
        String expiryYear = String.valueOf(faker.number().numberBetween(2025, 2030));

        paymentPage.setCardName(userData.getName());
        paymentPage.setCardNumber(cardNumber);
        paymentPage.setCardCVC(cvc);
        paymentPage.setCardExpiryMonth(expiryMonth);
        paymentPage.setCardExpiryYear(expiryYear);

        // Click submit button and verify the page routes to PaymentDonePage
        paymentDonePage = paymentPage.clickSubmitButton();
        paymentDonePage.clickDownloadInvoice();

        // Determine the download directory and create it if necessary and Wait for the file to download
        String downloadDirectory = "src/test/resources/download";
        File directory = new File(downloadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Poll for the downloaded file
        File downloadedFile = null;
        long startTime = System.currentTimeMillis();
        long timeout = 10000; // 10 seconds timeout
        while (System.currentTimeMillis() - startTime < timeout) {
            File[] files = new File(downloadPath).listFiles((dir, name) -> name.startsWith("invoice") && name.endsWith(".txt"));
            if (files != null && files.length > 0) {
                downloadedFile = files[0];
                break;
            }
            Thread.sleep(500); // Poll every 500ms
        }

        // Verify the downloaded file exists
        assertThat("The invoice file should be downloaded", downloadedFile, notNullValue());

        // Move the downloaded file to the desired directory with a unique name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String newFileName = "Invoice_" + timestamp + ".txt";
        File newFile = new File(downloadDirectory, newFileName);
        boolean isMoved = downloadedFile.renameTo(newFile);

        // Assert that the file was moved successfully
        assertThat("The invoice should be moved to the specified directory", isMoved);
        assertThat("The invoice file should exist in the new directory", newFile.exists());

        // Logout
        checkOutPage.clickLogout();
    }

    @Test
    @DisplayName("Verify Downloaded Invoice Content")
    @Order(5)
    public void verifyDownloadedInvoiceContent() throws InterruptedException {
        // Read user data from auth_data.json
        List<Map<String, Object>> users = JsonUtils.readJsonFile("src/test/resources/auth_data.json");

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

        // Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        // Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigation();

        // Navigate to product page from cart page
        productsPage = cartPage.redirectToProducts();

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
            productsPage = cartPage.redirectToProducts();
        }

        // Navigate back to cart page
        cartPage = productsPage.clickModalContentViewCartButton();

        // Proceed to checkout
        checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();


        // Verify the total amount
        double expectedOverallTotal = addedProducts.stream()
                .mapToDouble(ProductData::getTotal)
                .sum();

        double actualOverallTotal = Double.parseDouble(checkOutPage.getTotalAmount().replace("Rs. ", ""));

        paymentPage = checkOutPage.clickPlaceOrderButton();

        String cardNumber = faker.finance().creditCard();
        String cvc = String.valueOf(faker.number().numberBetween(100, 999));
        String expiryMonth = String.format("%02d", faker.number().numberBetween(1, 12));
        String expiryYear = String.valueOf(faker.number().numberBetween(2025, 2030));

        paymentPage.setCardName(userData.getName());
        paymentPage.setCardNumber(cardNumber);
        paymentPage.setCardCVC(cvc);
        paymentPage.setCardExpiryMonth(expiryMonth);
        paymentPage.setCardExpiryYear(expiryYear);

        // Click submit button and verify the page routes to PaymentDonePage
        paymentDonePage = paymentPage.clickSubmitButton();
        paymentDonePage.clickDownloadInvoice();

        // Determine the download directory and create it if necessary and Wait for the file to download
        String downloadDirectory = "src/test/resources/download";
        File directory = new File(downloadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Poll for the downloaded file
        File downloadedFile = null;
        long startTime = System.currentTimeMillis();
        long timeout = 10000; // 10 seconds timeout
        while (System.currentTimeMillis() - startTime < timeout) {
            File[] files = new File(downloadPath).listFiles((dir, name) -> name.startsWith("invoice") && name.endsWith(".txt"));
            if (files != null && files.length > 0) {
                downloadedFile = files[0];
                break;
            }
            Thread.sleep(500); // Poll every 500ms
        }

        // Move the downloaded file to the desired directory with a unique name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String newFileName = "Invoice_" + timestamp + ".txt";
        File newFile = new File(downloadDirectory, newFileName);
        assert downloadedFile != null;
        boolean isMoved = downloadedFile.renameTo(newFile);

        // Verify the content of the invoice
        verifyInvoiceContent(newFile, userData.getName(), actualOverallTotal);

        // Logout
        checkOutPage.clickLogout();
    }

    @Test
    @DisplayName("Verify Continue reroute to Homepage")
    @Order(6)
    public void verifyContinueButton(){
        // Navigate to auth page
        authPage = homePage.clickAuthNavigation();

        //set login email
        authPage.setLoginEmail("andrewamy@gmail.com");
        //set login password
        authPage.setLoginPasswordElement("AmyAndrew@1997");

        //Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        //Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigation();

        //Navigate to product page from cart page
        productsPage = cartPage.redirectToProducts();

        // Add a product to the cart
        productsPage.clickAddToCartFilterButton();

        // Navigate back to cart page
        cartPage = productsPage.clickModalContentViewCartButton();

        checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        paymentPage = checkOutPage.clickPlaceOrderButton();

        String cardNumber = faker.finance().creditCard();
        String cvc = String.valueOf(faker.number().numberBetween(100, 999));
        String expiryMonth = String.format("%02d", faker.number().numberBetween(1, 12));
        String expiryYear = String.valueOf(faker.number().numberBetween(2025, 2030));

        paymentPage.setCardName("Amy Andrew");
        paymentPage.setCardNumber(cardNumber);
        paymentPage.setCardCVC(cvc);
        paymentPage.setCardExpiryMonth(expiryMonth);
        paymentPage.setCardExpiryYear(expiryYear);

        // Click submit button and verify the page routes to PaymentDonePage
        paymentDonePage = paymentPage.clickSubmitButton();
        homePage = paymentDonePage.clickContinueButton();

        // Click submit button and verify the success message
        String homePageHeader = homePage.pageHeaderTextValue();
        assertThat("The Homepage Header should be displayed", homePageHeader, equalToIgnoringCase("AutomationExercise"));

        //Logout
        authPage = homePage.clickLogout();
    }

    // Method to add one product to cart from product details page
    private void addProductToCart() {
        // Navigate to product detail page
        productDetailPage = productsPage.clickViewProductButton();

        // Set product quantity
        productDetailPage.changeProductQuantity("1");

        // Add product to cart
        productDetailPage.clickAddToCartButton();
    }

    /**
     * Verifies the content of the downloaded invoice file.
     *
     * @param invoiceFile      The downloaded invoice file.
     * @param userName         The name of the user who made the purchase.
     * @param totalPurchaseAmount The total purchase amount.
     */
    private void verifyInvoiceContent(File invoiceFile, String userName, double totalPurchaseAmount) {
        try {
            // Read the content of the invoice file
            String content = new String(Files.readAllBytes(Paths.get(invoiceFile.getAbsolutePath())));

            // Verify that the content contains the user's name
            assertThat("The invoice should contain the user's name", content, containsString(userName));

            // Verify that the content contains the total purchase amount
            String expectedAmount = String.format("%.2f", totalPurchaseAmount);
            assertThat("The invoice should contain the total purchase amount", content, containsString(expectedAmount));

            // Verify the general format of the invoice
            assertThat("The invoice should follow the expected format", content, matchesPattern(".*Hi " + userName + ", Your total purchase amount is " + expectedAmount + "\\. Thank you.*"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the invoice file: " + e.getMessage(), e);
        }
    }
}
