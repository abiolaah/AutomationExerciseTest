package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class TestCasePage {
    private WebDriver driver;
    // Locator for test case links
    private final By testCaseLinks = By.cssSelector(".panel-heading .panel-title a");
    private final By pageHeader = By.cssSelector("div>h2.title>b");

    public TestCasePage(WebDriver driver) {
        this.driver = driver;
    }

    // Get test case page header
    public String getPageHeader(){
        return driver.findElement(pageHeader).getText();
    }

    // Get all test case names displayed on the page
    public List<String> getDisplayedTestCases() {
        List<WebElement> testCases = driver.findElements(testCaseLinks);
        List<String> displayedTestCases = new ArrayList<>();
        for (WebElement testCase : testCases) {
            displayedTestCases.add(testCase.getText().replace("Test Case", "").trim());
        }
        return displayedTestCases;
    }
}
