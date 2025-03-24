package authentications;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import pages.AccountCreatedPage;
import pages.AccountSetUpPage;
import pages.AuthenticationsPage;

import java.io.IOException;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalToIgnoringCase;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyAccountSetUpTest extends BaseTest {
    protected AuthenticationsPage registerPage;
    protected AccountSetUpPage setUpPage;

    // Initialize Faker
    private final Faker faker = new Faker(Locale.of("en","CA"));

    @Test
    @DisplayName("Verify Account Setup Section Title")
    @Order(1)
    public void verifyAccountSetUpSectionText() throws IOException {
        // Generate random data
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();

        // Perform registration
        registerPage = homePage.clickAuthNavigation();
        registerPage.setRegisterEmailElement(email);
        registerPage.setRegisterNameElement(name);
        setUpPage= registerPage.clickRegister();

        assertThat("The page header should contains 'Enter Account Information'", setUpPage.getSectionTitle(), containsStringIgnoringCase("Account Information"));
    }

    @Test
    @DisplayName("Verify Name Value")
    @Order(2)
    public void verifyNameValue() throws IOException{
        // Generate random data
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();

        // Perform registration
        registerPage = homePage.clickAuthNavigation();
        registerPage.setRegisterEmailElement(email);
        registerPage.setRegisterNameElement(name);
        setUpPage= registerPage.clickRegister();

        String username = setUpPage.getNameValue();;

        assertThat("The name value is should not be empty", username, equalToIgnoringCase(name));
    }

    @Test
    @DisplayName("Verify Email Value")
    @Order(3)
    public void verifyEmailValue() throws IOException{
        // Generate random data
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();

        // Perform registration
        registerPage = homePage.clickAuthNavigation();
        registerPage.setRegisterEmailElement(email);
        registerPage.setRegisterNameElement(name);
        setUpPage= registerPage.clickRegister();

        assertThat("The email value is should not be empty", setUpPage.getEmailValue(), equalToIgnoringCase(email));
    }

}