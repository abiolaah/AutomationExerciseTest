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
import java.util.HashMap;

public class BaseTest {
    public WebDriver driver;
    protected HomePage homePage;

    // Define the download path in a platform-agnostic way
    public final String downloadPath = System.getProperty("user.home") + File.separator + "Downloads";

    @BeforeAll
        public static void setUp(){
            // use WebDriverManager a java library dependency to manage the drivers required by Selenium web driver
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
        takeScreenshot(testInfo.getDisplayName());
        driver.quit();
    }


    private void takeScreenshot(String testMethodName) {
        // Define the screenshot folder path
        File screenshotDir = new File("src/main/resources/screenshot");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        // Clean up the test method name
        String cleanName = testMethodName.toLowerCase()
                .replaceAll("\\s+", "_")  // Replace whitespace with underscore
                .replaceAll("[^a-z0-9_]", "");  // Remove special characters

        // Delete any existing screenshots with the same display name
        File[] existingScreenshots = screenshotDir.listFiles((dir, name) ->
                name.startsWith(cleanName) && name.endsWith(".png"));

        if (existingScreenshots != null) {
            for (File existingFile : existingScreenshots) {
                if (!existingFile.delete()) {
                    System.err.println("Failed to delete existing screenshot: " + existingFile.getAbsolutePath());
                }
            }
        }

        // Capture screenshot and save it in resources/screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotName = cleanName + ".png";

        // Define the screenshot file path
        File destinationFile = new File(screenshotDir, screenshotName);
        try {
            Files.copy(screenshot.toPath(), destinationFile.toPath());
            System.out.println("Screenshot saved: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }
}
