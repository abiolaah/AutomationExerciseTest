package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class APITestingPage {
    private WebDriver driver;
    // Locator for test case links
    private final By apiListLinks = By.cssSelector(".panel-heading .panel-title a");
    private final By pageHeader = By.cssSelector("div>h2.title>b");

    public APITestingPage(WebDriver driver) {
        this.driver = driver;
    }

    // Get test case page header
    public String getPageHeader(){
        return driver.findElement(pageHeader).getText(); //Output: APIs List for practice
    }

    // Get all test case names displayed on the page
    public List<String> getDisplayedApiLists() {
        List<WebElement> apiLists = driver.findElements(apiListLinks);
        List<String> displayedApiLists = new ArrayList<>();
        for (WebElement api : apiLists) {
            displayedApiLists.add(api.getText().replace("API", "").trim());
        }
        return displayedApiLists;
    }
}
