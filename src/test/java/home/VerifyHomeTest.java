package home;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyHomeTest extends BaseTest {
    Faker faker = new Faker(Locale.of("en", "CA"));
    @Test
    @DisplayName("Confirm Header Text")
    @Order(1)
    public void confirmHeaderText(){
        assertThat(homePage.pageHeaderTextValue(), equalToIgnoringCase("AutomationExercise"));
    }

    @Test
    @DisplayName("Confirm Items Section Header Text")
    @Order(2)
    public void confirmItemsSectionHeaderText(){
        assertThat(homePage.featureItemsElementTextValue(), equalToIgnoringCase("Features Items"));
    }

    @Test
    @DisplayName("Confirm Filter Section Header Text")
    @Order(3)
    public void confirmFilterSectionHeaderText(){
        assertThat(homePage.categoryElementTextValue(), equalToIgnoringCase("Category"));
    }

    @Test
    @DisplayName("Confirm Footer Text")
    @Order(4)
    public void confirmFooterText(){
        assertThat("Footer text should contain 'Copyright'", homePage.footerTextValue(), containsString("Copyright"));
    }

    @Test
    @DisplayName("Email Subscription")
    @Order(5)
    public void verifySubscriptionFunctionality(){
        homePage.setSubscriptionEmail(faker.internet().emailAddress());
        homePage.clickSubscriptionButton();
        String expectedMessage = "You have been successfully subscribed!";
        String actualMessage = homePage.getSubscriptionSuccessMessage();
        assertThat("The subscription success message should be equal to expected message", actualMessage, equalToIgnoringCase(expectedMessage));
    }
}
