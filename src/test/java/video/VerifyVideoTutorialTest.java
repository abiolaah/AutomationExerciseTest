package video;

import baseTests.BaseTest;
import org.junit.jupiter.api.*;
import pages.VideoTutorialsPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyVideoTutorialTest extends BaseTest {
    protected VideoTutorialsPage videoPage;

    @Test
    @DisplayName("Confirm Page Title")
    @Order(1)
    public void verifyPageTitle(){
        videoPage = homePage.clickVideoTutorialsNavigation();
        assertThat("Page Title read 'AutomationExercise - YouTube'", videoPage.getPageTitle(), equalToIgnoringCase("AutomationExercise - YouTube"));
    }

    @Test
    @DisplayName("Confirm Page Current URL")
    @Order(2)
    public void verifyPageUrl(){
        videoPage = homePage.clickVideoTutorialsNavigation();
        assertThat("Page URL read 'https://www.youtube.com/c/AutomationExercise'", videoPage.getCurrentUrl(), equalToIgnoringCase("https://www.youtube.com/c/AutomationExercise"));
    }
}
