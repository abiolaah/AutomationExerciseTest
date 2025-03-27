package checkout;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import pages.*;
import utils.JsonUtils;
import utils.UserData;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyPaymentTest extends BaseTest {
    protected AuthenticationsPage authPage;
    protected CartPage cartPage;
    protected CheckOutPage checkOutPage;
    protected ProductDetailPage productDetailPage;
    protected ProductsPage productsPage;
    protected PaymentPage paymentPage;
    protected PaymentDonePage paymentDonePage;

    Faker faker = new Faker(Locale.of("en", "CA"));

    @Test
    @DisplayName("Verify Payment Page Section Title")
    @Order(1)
    public void verifyPaymentPageSectionTitleText(){
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

        // Check if cart is empty
        String emptyCartText = cartPage.getEmptyCartText();;
        if (emptyCartText.contains("Cart is empty!")){
            // If cart is empty, redirect to products page
            productsPage = cartPage.redirectToProducts();

            // Add a product to the cart
            productsPage.clickAddToCartFilterButton();

            productsPage.clickModalFooterContinueButton();

            cartPage = productsPage.clickAddToCartNavMenu();

            // Click checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        }
        else {
            // If cart is not empty, proceed to checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        }

        paymentPage = checkOutPage.clickPlaceOrderButton();

        // Click submit button and verify the success message
        String sectionTitle = paymentPage.getSectionTitle();
        assertThat("The Section Title should be displayed", sectionTitle, equalToIgnoringCase("Payment"));

        //Logout
        homePage = paymentPage.clickLogout();
    }

    @Test
    @DisplayName("Verify Payment Done Reroute")
    @Order(2)
    public void verifyPaymentDoneReroute(){
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
                (String) randomUser.get("phone_number"),
                (String) randomUser.get("company"));

        // Navigate to auth page
        authPage = homePage.clickAuthNavigation();

        //set login email
        authPage.setLoginEmail(userData.getEmail());

        //set login password
        authPage.setLoginPasswordElement((String) randomUser.get("password"));

        // Navigate to homepage from auth page
        homePage = authPage.clickLogin();

        // Navigate to cart page from homepage
        cartPage = homePage.clickCartNavigationAfterLogin();

        // Check if the cart is empty
        String emptyCartText = cartPage.getEmptyCartText();
        if (emptyCartText.contains("Cart is empty!")){
            // If cart is empty, redirect to products page
            productsPage = cartPage.redirectToProducts();

            // Add a product to the cart and get its details
            productsPage.clickAddToCartFilterButton();

            productsPage.clickModalFooterContinueButton();

            cartPage = productsPage.clickAddToCartNavMenu();

            // Click checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        }
        else {
            // If cart is not empty, proceed to checkout
            checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();
        }

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
        String paymentDoneTitle = paymentDonePage.getSectionTitle();
        assertThat("The page should route to Payment Done page", paymentDoneTitle, equalToIgnoringCase("Order Placed!"));

        // Logout
        checkOutPage.clickLogout();
    }
}
