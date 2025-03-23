package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PaymentDonePage {
    private final WebDriver driver;
    private final By sectionTitleElement = By.cssSelector("section#form> div.container > div.row > div > h2 > b");
    private final By sectionTextElement = By.cssSelector("section#form> div.container > div.row > div > p");
    private final By downloadButtonElement = By.cssSelector("section#form> div.container > div.row > div > a");
    private final By continueButtonElement = By.cssSelector("div.pull-right > a");
    private final By logoutButton = By.cssSelector("a[href=\"/logout\"]");

    public PaymentDonePage(WebDriver driver){
        this.driver = driver;
    }

    // method to get section Title
    public String getSectionTitle(){
        WebElement sectionTitle = driver.findElement(sectionTitleElement);
        return sectionTitle.getText();
    }
    // method to get section text
    public List<String> getSectionText(){
        List<WebElement> sectionTextElements = driver.findElements(sectionTextElement);
        List<String> text = new ArrayList<>();
        for (WebElement element: sectionTextElements){
            text.add(element.getText().trim());
        }
        return text;
    }
    // method to click download invoice button: triggers download
    public void clickDownloadInvoice(){
        driver.findElement(downloadButtonElement).click();
    }

    // method to click continue button: this routes back to home page
    public HomePage clickContinueButton(){
        driver.findElement(continueButtonElement).click();
        return new HomePage(driver);
    }

    //Method to confirm loggged or logout
    public AuthenticationsPage clickLogout(){
        driver.findElement(logoutButton).click();
        return new AuthenticationsPage(driver);
    }
}
