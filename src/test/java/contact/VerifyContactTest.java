package contact;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import pages.ContactUsPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyContactTest extends BaseTest {
    protected ContactUsPage contactPage;
    Faker faker = new Faker();
    @Test
    @DisplayName("Verify Page Header Text")
    @Order(1)
    public void verifyPageHeaderText(){
        contactPage = homePage.clickContactUsNavigation();
        assertThat("Page header text should read 'Contact Us'", contactPage.getPageHeaderText(), equalToIgnoringCase("Contact Us"));
    }

    @Test
    @DisplayName("Verify Feedback Section Title Text")
    @Order(2)
    public void verifyFeedbackTitle(){
        contactPage = homePage.clickContactUsNavigation();
        assertThat("Feedback Section Title reads ''", contactPage.getFeedbackSectionTitle(), equalToIgnoringCase("Feedback for Us"));
    }

    @Test
    @DisplayName("Verify Feedback Paragraph contains 'Thank You'")
    @Order(3)
    public void verifyFeedbackParagraphContainThankYou(){
        contactPage = homePage.clickContactUsNavigation();
        assertThat("Feedback Section Contains 'Thank you'", contactPage.getFeedbackText(), containsStringIgnoringCase("Thank You"));
    }

    @Test
    @DisplayName("Verify Text of Feedback Email")
    @Order(4)
    public void verifyFeedbackEmailText(){
        contactPage = homePage.clickContactUsNavigation();
        assertThat("Feedback Section contains email", contactPage.getFeedbackEmail(), equalToIgnoringCase("feedback@automationexercise.com"));
    }

    @Test
    @DisplayName("Verify Contact Form Section Title Text")
    @Order(5)
    public void verifyFormTitle(){
        contactPage = homePage.clickContactUsNavigation();
        assertThat("Contain Section Header Text should read ''", contactPage.getFormTitleText(), equalToIgnoringCase("Get In Touch"));
    }

    @Test
    @DisplayName("Verify Alert Text")
    @Order(6)
    public void verifyAlertText(){
        contactPage = homePage.clickContactUsNavigation();
        contactPage.setName(faker.name().fullName());
        contactPage.setEmail(faker.internet().emailAddress());
        contactPage.setSubject("Issue with Purchase");
        contactPage.setMessage("No confirmation email was received for order ORD_900");
        contactPage.clickSubmitButton();

        // Handle the alert explicitly before any other operations
        String alertText = contactPage.getAlertText();
        contactPage.clickToDismissAlert();

        assertThat("Alert Text should read 'Automation exercise says'", alertText, containsStringIgnoringCase("Press OK to proceed"));
    }

    @Test
    @DisplayName("Verify Alert Dismiss Button Works")
    @Order(7)
    public void verifyDismissButton(){
        contactPage = homePage.clickContactUsNavigation();
        contactPage.setName(faker.name().fullName());
        contactPage.setEmail(faker.internet().emailAddress());
        contactPage.setSubject("Issue with Purchase");
        contactPage.setMessage("No confirmation email was received for order ORD_900");
        contactPage.clickSubmitButton();
        contactPage.clickToDismissAlert();
        assertThat("Page should still remain at Contact Page'", contactPage.getPageUrl(), containsStringIgnoringCase("contact"));
    }

    @Test
    @DisplayName("Verify Success Contact Form Submission, Upload")
    @Order(8)
    public void verifySuccessMessageUpload(){
        contactPage = homePage.clickContactUsNavigation();
        contactPage.setName(faker.name().fullName());
        contactPage.setEmail(faker.internet().emailAddress());
        contactPage.setSubject("Issue with Purchase");
        contactPage.setMessage("No confirmation email was received for order ORD_900");
        contactPage.uploadFile("/Users/victoria/Documents/Work/Projects/Testing/Web/AutomationExerciseTest/src/main/resources/upload/California-backyard-1.webp");
        contactPage.clickSubmitButton();
        contactPage.clickToAcceptAlert();
        assertThat("Contact form should be replaced with success message", contactPage.getSuccessMessage(), containsStringIgnoringCase("Success"));
    }

    @Test
    @DisplayName("Verify Success Contact Form Submission, No Upload")
    @Order(9)
    public void verifySuccessMessageNoUpload(){
        contactPage = homePage.clickContactUsNavigation();
        contactPage.setName(faker.name().fullName());
        contactPage.setEmail(faker.internet().emailAddress());
        contactPage.setSubject("Issue with Purchase");
        contactPage.setMessage("No confirmation email was received for order ORD_900");
        contactPage.clickSubmitButton();
        contactPage.clickToAcceptAlert();
        assertThat("Contact form should be replaced with success message", contactPage.getSuccessMessage(), containsStringIgnoringCase("Success"));
    }

    @Test
    @DisplayName("Verify Home Button works")
    @Order(10)
    public void verifyHomeButtonFunctionality(){
        contactPage = homePage.clickContactUsNavigation();
        contactPage.setName(faker.name().fullName());
        contactPage.setEmail(faker.internet().emailAddress());
        contactPage.setSubject("Issue with Purchase");
        contactPage.setMessage("No confirmation email was received for order ORD_900");
        contactPage.clickSubmitButton();
        contactPage.clickToAcceptAlert();
        homePage = contactPage.clickHomeButton();
        assertThat("Routed to home page", homePage.pageHeaderTextValue(), containsStringIgnoringCase("AutomationExercise"));
    }
}
