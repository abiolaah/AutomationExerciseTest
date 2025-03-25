package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentDonePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By sectionTitleElement = By.cssSelector("section#form> div.container > div.row > div > h2 > b");
    private final By sectionTextElement = By.cssSelector("section#form> div.container > div.row > div > p");
    private final By downloadButtonElement = By.cssSelector("section#form> div.container > div.row > div > a");
    private final By continueButtonElement = By.cssSelector("div.pull-right > a");
    private final By logoutButton = By.cssSelector("a[href=\"/logout\"]");

    public PaymentDonePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // method to get section Title
    public String getSectionTitle(){
        WebElement sectionTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(sectionTitleElement));
        return sectionTitle.getText();
    }

    // method to get section text
    public String getSectionText(){
        WebElement sectionText = wait.until(ExpectedConditions.visibilityOfElementLocated(sectionTextElement));
        return sectionText.getText();
    }
    // method to click download invoice button: triggers download
    public void clickDownloadInvoice(){
        WebElement downloadButton = wait.until(ExpectedConditions.elementToBeClickable(downloadButtonElement));
        downloadButton.click();
    }

    // method to click continue button: this routes back to home page
    public HomePage clickContinueButton(){
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(continueButtonElement));
        continueButton.click();
        return new HomePage(driver);
    }

    //Method to confirm logged or logout
    public AuthenticationsPage clickLogout(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the logout button to be present in the DOM
        WebElement logoutButtonElement = wait.until(ExpectedConditions.presenceOfElementLocated(logoutButton));

        // Scroll the logout button into view using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutButtonElement);

        // Wait for the button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(logoutButtonElement));

        // Click the logout button
        logoutButtonElement.click();
        return new AuthenticationsPage(driver);
    }
}
