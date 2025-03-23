package baseTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.HomePage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DownloadBaseTest {
    public WebDriver driver;
    protected HomePage homePage;

    // Define the download path in a platform-agnostic way
    public final String downloadPath = System.getProperty("user.dir") + File.separator + "downloads";

    @BeforeAll
        public static void setUp(){
            // use WebDriverManager a java library dependency to manage the drivers required by Selenium webdriver
            WebDriverManager.chromedriver().setup();
        }

        @BeforeEach
        public void setUpTest(){
            // Set Chrome options
            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadPath);
            options.setExperimentalOption("prefs", chromePrefs);

            // Create the download directory if it doesn't exist
            File downloadDir = new File(downloadPath);
            if (!downloadDir.exists()) {
                boolean dirCreated = downloadDir.mkdirs();
                if (!dirCreated) {
                    throw new RuntimeException("Failed to create download directory: " + downloadPath);
                }
            }

            //instantiate with chromedriver
            driver = new ChromeDriver();

            //use the get method from driver class to launch browser and load the website
            driver.get("https://automationexercise.com/");

            // Resize current window
            driver.manage().window().maximize();

        //Load the home page
        homePage = new HomePage(driver);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo){
//        takeScreenshot(testInfo.getDisplayName());
        driver.quit();
    }


    private void takeScreenshot(String testMethodName) {
        // Capture screenshot and save it in resources/screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName = testMethodName + "_" + timestamp + ".png";

        // Define the screenshot folder path
        File screenshotDir = new File("resources/screenshot");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        // Define the screenshot file path
        File destinationFile = new File(screenshotDir, screenshotName);
        try {
            Files.copy(screenshot.toPath(), destinationFile.toPath());
            System.out.println("Screenshot saved: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }

    @Test
    @Disabled
    void practiceTest()
    {
        driver.get("https://automationexercise.com/");
        String title = driver.getTitle();
        assertThat(title, equalTo("Automation Exercise"));
    }

    @Test
    @Disabled
    void practiceTest2()
    {
        homePage.clickVideoTutorialsNavigation();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String title = driver.getTitle();
        System.out.println(title);
        String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl);
        //https://www.youtube.com/c/AutomationExercise
    }
}
