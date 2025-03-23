package authentications;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import pages.AccountCreatedPage;
import pages.AccountSetUpPage;
import pages.AuthenticationsPage;
import pages.DeleteAccountPage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyAccountDeletionTest extends BaseTest {
    protected AuthenticationsPage registerPage;
    protected AccountSetUpPage setUpPage;
    protected AccountCreatedPage accountCreatedPage;
    protected DeleteAccountPage deleteAccountPage;

    // Initialize Faker
    private final Faker faker = new Faker(Locale.of("en","CA"));
    private final Map<String, Object> registrationData = new HashMap<>();

    @Test
    @DisplayName("Verify Delete Account Page Section Title")
    @Order(1)
    public void verifyDeleteAccountPageTitle() throws IOException {
        // Generate random data
        String name = faker.name().fullName();
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        String company = faker.company().name();
        String email = faker.internet().emailAddress();
        String title = faker.bool().bool() ? "Mr": "Mrs";
        String password = faker.internet().password(8, 16);
        String day = String.valueOf(faker.number().numberBetween(1, 28));
        String month = String.valueOf(faker.number().numberBetween(1, 12));
        String year = String.valueOf(faker.number().numberBetween(1950, 2007));
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String zipCode = faker.address().zipCode();
        String phoneNumber = faker.phoneNumber().cellPhone();

        // Populate registration data map
        registrationData.put("name", name);
        registrationData.put("email", email);
        registrationData.put("title", title);
        registrationData.put("password", password);
        registrationData.put("dob", Map.of("day", day, "month", month, "year", year));
        registrationData.put("first_name", firstName);
        registrationData.put("last_name", lastName);
        registrationData.put("address", address);
        registrationData.put("company", company);
        registrationData.put("country", "Canada");
        registrationData.put("state", state);
        registrationData.put("city", city);
        registrationData.put("zip_code", zipCode);
        registrationData.put("phone_number", phoneNumber);

        // Perform registration
        registerPage = homePage.clickAuthNavigation();
        registerPage.setRegisterEmailElement(email);
        registerPage.setRegisterNameElement(name);
        setUpPage= registerPage.clickRegister();

        setUpPage.setTitleValue(title);
        setUpPage.setPasswordValue(password);
        setUpPage.setDayValue(day);
        setUpPage.setMonthValue(month);
        setUpPage.setYearValue(year);
        setUpPage.setSignUpCheckbox();
        setUpPage.setSpecialOfferCheckbox();
        setUpPage.setFirstNameValue(firstName);
        setUpPage.setLastNameValue(lastName);
        setUpPage.setCompanyValue(company);
        setUpPage.setAddressLine1Value(address);
        setUpPage.setCityValue(city);
        setUpPage.setStateValue(state);
        setUpPage.setZipCodeValue(zipCode);
        setUpPage.setPhoneNumberValue(phoneNumber);
        setUpPage.setCountryValue("Canada");

        accountCreatedPage = setUpPage.clickCreateButton();
        // Save the registration data to auth_data.json
//        saveRegistrationData();
        homePage = accountCreatedPage.clickContinueButton();
        deleteAccountPage = homePage.clickDeleteAccount();
        String deletePageHeader = deleteAccountPage.getSectionTitleText();

        assertThat("Delete Account page Section Title should contain 'Delete'", deletePageHeader, containsStringIgnoringCase("Delete"));
    }

    @Test
    @DisplayName("Verify Delete Account Page Paragraph")
    @Order(2)
    public void verifyDeleteAccountPageParagraph() throws IOException {
        // Generate random data
        String name = faker.name().fullName();
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        String company = faker.company().name();
        String email = faker.internet().emailAddress();
        String title = faker.bool().bool() ? "Mr": "Mrs";
        String password = faker.internet().password(8, 16);
        String day = String.valueOf(faker.number().numberBetween(1, 28));
        String month = String.valueOf(faker.number().numberBetween(1, 12));
        String year = String.valueOf(faker.number().numberBetween(1950, 2007));
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String zipCode = faker.address().zipCode();
        String phoneNumber = faker.phoneNumber().cellPhone();

        // Populate registration data map
        registrationData.put("name", name);
        registrationData.put("email", email);
        registrationData.put("title", title);
        registrationData.put("password", password);
        registrationData.put("dob", Map.of("day", day, "month", month, "year", year));
        registrationData.put("first_name", firstName);
        registrationData.put("last_name", lastName);
        registrationData.put("address", address);
        registrationData.put("company", company);
        registrationData.put("country", "Canada");
        registrationData.put("state", state);
        registrationData.put("city", city);
        registrationData.put("zip_code", zipCode);
        registrationData.put("phone_number", phoneNumber);

        // Perform registration
        registerPage = homePage.clickAuthNavigation();
        registerPage.setRegisterEmailElement(email);
        registerPage.setRegisterNameElement(name);
        setUpPage= registerPage.clickRegister();

        setUpPage.setTitleValue(title);
        setUpPage.setPasswordValue(password);
        setUpPage.setDayValue(day);
        setUpPage.setMonthValue(month);
        setUpPage.setYearValue(year);
        setUpPage.setSignUpCheckbox();
        setUpPage.setSpecialOfferCheckbox();
        setUpPage.setFirstNameValue(firstName);
        setUpPage.setLastNameValue(lastName);
        setUpPage.setCompanyValue(company);
        setUpPage.setAddressLine1Value(address);
        setUpPage.setCityValue(city);
        setUpPage.setStateValue(state);
        setUpPage.setZipCodeValue(zipCode);
        setUpPage.setPhoneNumberValue(phoneNumber);
        setUpPage.setCountryValue("Canada");

        accountCreatedPage = setUpPage.clickCreateButton();
        // Save the registration data to auth_data.json
//        saveRegistrationData();
        homePage = accountCreatedPage.clickContinueButton();
        deleteAccountPage = homePage.clickDeleteAccount();
        String deletePageParagraph = deleteAccountPage.getSectionParagraphText();

        assertThat("Delete Account page paragraph should contain 'Delete'", deletePageParagraph, containsStringIgnoringCase("Delete"));
    }

    @Test
    @DisplayName("Verify Delete Account")
    @Order(3)
    public void verifyDeleteAccount() throws IOException {
        // Generate random data
        String name = faker.name().fullName();
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        String company = faker.company().name();
        String email = faker.internet().emailAddress();
        String title = faker.bool().bool() ? "Mr": "Mrs";
        String password = faker.internet().password(8, 16);
        String day = String.valueOf(faker.number().numberBetween(1, 28));
        String month = String.valueOf(faker.number().numberBetween(1, 12));
        String year = String.valueOf(faker.number().numberBetween(1950, 2007));
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String zipCode = faker.address().zipCode();
        String phoneNumber = faker.phoneNumber().cellPhone();

        // Perform registration
        registerPage = homePage.clickAuthNavigation();
        registerPage.setRegisterEmailElement(email);
        registerPage.setRegisterNameElement(name);
        setUpPage= registerPage.clickRegister();

        setUpPage.setTitleValue(title);
        setUpPage.setPasswordValue(password);
        setUpPage.setDayValue(day);
        setUpPage.setMonthValue(month);
        setUpPage.setYearValue(year);
        setUpPage.setSignUpCheckbox();
        setUpPage.setSpecialOfferCheckbox();
        setUpPage.setFirstNameValue(firstName);
        setUpPage.setLastNameValue(lastName);
        setUpPage.setCompanyValue(company);
        setUpPage.setAddressLine1Value(address);
        setUpPage.setCityValue(city);
        setUpPage.setStateValue(state);
        setUpPage.setZipCodeValue(zipCode);
        setUpPage.setPhoneNumberValue(phoneNumber);
        setUpPage.setCountryValue("Canada");

        accountCreatedPage = setUpPage.clickCreateButton();
        homePage = accountCreatedPage.clickContinueButton();
        deleteAccountPage = homePage.clickDeleteAccount();
        homePage = deleteAccountPage.clickContinueButton();
        String homePageHeader = homePage.pageHeaderTextValue();

        assertThat("Routed to home page", homePageHeader, containsStringIgnoringCase("AutomationExercise"));
    }
}
