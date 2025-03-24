package authentications;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import pages.AuthenticationsPage;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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
        Type userListType = new TypeToken<List<Map<String, Object>>>() {}.getType(); // Properly define the type
        try (FileReader reader = new FileReader("src/main/resources/data/auth_data.json")) {
            List<Map<String, Object>> users = gson.fromJson(reader, userListType);

            if (users != null && !users.isEmpty()) {
                // Randomly select a user
                Random random = new Random();
                authData = users.get(random.nextInt(users.size()));
            } else {
                throw new RuntimeException("No user data found in auth_data.json");
            }
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
    @Order(3)
    public void verifyUnsuccessfulLogin(){
        loginPage = homePage.clickAuthNavigation();
        loginPage.setLoginEmail(faker.internet().emailAddress());
        loginPage.setLoginPasswordElement(faker.internet().password());
        homePage = loginPage.clickLogin();
        String loginFailureText = loginPage.getLoginFailureText();
        assertThat("Failure Note should be displayed", loginFailureText, containsStringIgnoringCase("incorrect!"));
    }
}
