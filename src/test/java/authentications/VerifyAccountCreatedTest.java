package authentications;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import pages.AccountCreatedPage;
import pages.AccountSetUpPage;
import pages.AuthenticationsPage;
import utils.RegistrationUtils;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyAccountCreatedTest extends BaseTest {
    protected AuthenticationsPage registerPage;
    protected AccountSetUpPage setUpPage;
    protected AccountCreatedPage accountCreatedPage;

    // Initialize Faker
    private final Faker faker = new Faker(Locale.of("en","CA"));
    private final Map<String, Object> registrationData = new HashMap<>();

    @Test
    @DisplayName("Verify Account Created Section Paragraph")
    @Order(1)
    public void verifyAccountCreatedSectionText() throws IOException {
        // Generate random data
        String firstName, lastName, email;

        int attempts = 0;
        int maxAttempts = 10;

        do{
            firstName = faker.name().firstName();
            lastName = faker.name().lastName();
            email = faker.internet().emailAddress();

            attempts++;

            if (attempts >= maxAttempts){
                throw new RuntimeException("Failed to generate unique user data after "+maxAttempts+ " attempts");
            }
        } while (RegistrationUtils.isEmailExists(email) || RegistrationUtils.isNameExists(firstName, lastName));

        String name = firstName + " " +lastName;
        String company = faker.company().name();
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

        // Save the registration data to data/auth_data.json
        try {
        RegistrationUtils.saveRegistrationData(registrationData);
        } catch (IllegalArgumentException e) {
            verifyAccountCreatedSectionText();
            return;
        }
        assertThat("Account created successfully", accountCreatedPage.getSectionParagraphText(), containsStringIgnoringCase("Congratulations"));
    }

    @Test
    @DisplayName("Verify ReRouting to Homepage")
    @Order(2)
    public void verifyContinueButtonFunction() throws IOException {

        // Generate random data
        String firstName, lastName, email;

        int attempts = 0;
        int maxAttempts = 10;

        do{
            firstName = faker.name().firstName();
            lastName = faker.name().lastName();
            email = faker.internet().emailAddress();

            attempts++;

            if (attempts >= maxAttempts){
                throw new RuntimeException("Failed to generate unique user data after "+maxAttempts+ " attempts");
            }
        } while (RegistrationUtils.isEmailExists(email) || RegistrationUtils.isNameExists(firstName, lastName));


        // Generate rest of the  random data
        String name = firstName + " " +lastName;
        String company = faker.company().name();
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
        try {
        // Save the registration data to auth_data.json
        RegistrationUtils.saveRegistrationData(registrationData);
        }
        catch (IllegalArgumentException e) {
            verifyContinueButtonFunction();
            return;
        }
        homePage = accountCreatedPage.clickContinueButton();
        String homePageHeader = homePage.pageHeaderTextValue();
        assertThat("Routed to home page", homePageHeader, containsStringIgnoringCase("AutomationExercise"));
    }

}