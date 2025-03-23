package products;

import baseTests.BaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import pages.ProductDetailPage;
import pages.ProductsPage;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyProductDetailsReviewTest extends BaseTest {
    protected ProductsPage productsPage;
    protected ProductDetailPage productDetailsPage;

    @Test
    @DisplayName("Confirm Review Section Header read 'Write Your Review'")
    @Order(1)
    public void verifyReviewSectionHeaderText(){
        productsPage = homePage.clickProductNavigation();
        productDetailsPage = productsPage.clickViewProductButton();
        String reviewSectionText = productDetailsPage.getReviewSectionHeaderText();
        assertThat("The Review section header Text reads 'Write Your Review'", reviewSectionText, equalToIgnoringCase("Write Your Review"));
    }

    @Test
    @DisplayName("Confirm Successful Review Submission")
    @Order(2)
    public void verifySuccessReviewSubmission(){
        Faker faker = new Faker(Locale.of("en", "CA"));
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String reviewMessage = faker.lorem().sentence(10);
        productsPage = homePage.clickProductNavigation();
        productDetailsPage = productsPage.clickViewProductButton();
        productDetailsPage.setReviewNameInputElement(name);
        productDetailsPage.setReviewEmailInputElement(email);
        productDetailsPage.setReviewTextBoxElement(reviewMessage);
        productDetailsPage.clickReviewSubmitButton();
        String reviewSuccessMessage = productDetailsPage.getReviewConfirmationMessage();
        assertThat("Successful Review Confirmation Message should read 'Thank you for your review'", reviewSuccessMessage, equalToIgnoringCase("Thank you for your review."));
    }
}
