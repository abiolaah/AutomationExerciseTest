package authentications;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import pages.AuthenticationsPage;

import java.io.FileReader;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyLoginTest extends BaseTest {
    protected AuthenticationsPage loginPage;
    private Map<String, Object> authData;

    // Initialize Faker
    private final Faker faker = new Faker(Locale.of("en","CA"));

    @BeforeEach
    public void loadAuthData() throws Exception {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("src/main/resources/data/auth_data.json")) {
            authData = gson.fromJson(reader, Map[].class)[0]; // Load the first user from the file
        }
    }

    @Test
    @DisplayName("Verify Login Section Text")
    @Order(1)
    public void verifyLoginSectionText(){
        loginPage = homePage.clickAuthNavigation();
        String loginText = loginPage.getLoginTitleText();
        assertThat("The Login Section Header should read 'Login'", loginText, containsStringIgnoringCase("Login"));
    }

    @Test
    @DisplayName("Verify Successful Login")
    @Order(2)
    public void verifySuccessfulLogin(){
        loginPage = homePage.clickAuthNavigation();
        loginPage.setLoginEmail(authData.get("email").toString());
        loginPage.setLoginPasswordElement(authData.get("password").toString());
        homePage = loginPage.clickLogin();
        String homePageHeader = homePage.pageHeaderTextValue();
        assertThat("Routed to home page", homePageHeader, containsStringIgnoringCase("AutomationExercise"));
    }

    @Test
    @DisplayName("Verify unSuccessful Login")
    @Order(2)
    public void verifyUnsuccessfulLogin(){
        loginPage = homePage.clickAuthNavigation();
        loginPage.setLoginEmail(faker.internet().emailAddress());
        loginPage.setLoginPasswordElement(faker.internet().password());
        homePage = loginPage.clickLogin();
        String loginFailureText = loginPage.getLoginFailureText();
        assertThat("Failure Note should be displayed", loginFailureText, containsStringIgnoringCase("incorrect!"));
    }
}
