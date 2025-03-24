package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {
    private final WebDriver driver;
    private final By sectionTitleElement = By.cssSelector("h2.heading");
    private final By formCardNameElement = By.cssSelector("input[name=\"name_on_card\"]");
    private final By formCardNumberElement = By.cssSelector("input[name=\"card_number\"]");
    private final By formCVCElement = By.cssSelector("input[name=\"cvc\"]");
    private final By formExpiryMonthElement = By.cssSelector("input[name=\"expiry_month\"]");
    private final By formExpiryYearElement = By.cssSelector("input[name=\"expiry_year\"]");
    private final By formSubmitButtonElement = By.id("submit");
    private final By formSuccessMessage = By.cssSelector("div#success_message > div.alert-success");
    private final By logoutButton = By.cssSelector("a[href=\"/logout\"]");

    public PaymentPage(WebDriver driver){
        this.driver = driver;
    }

    // method to get section title
    public String getSectionTitle(){
        WebElement sectionTitle = driver.findElement(sectionTitleElement);
        return sectionTitle.getText();
    }
    // method to set card name value
    public void setCardName(String cardName){
        driver.findElement(formCardNameElement).sendKeys(cardName);
    }
    // method to set card number value
    public void setCardNumber(String cardNumber){
        driver.findElement(formCardNumberElement).sendKeys(cardNumber);
    }
    // method to set card cvc value
    public void setCardCVC(String cardCvc){
        driver.findElement(formCVCElement).sendKeys(cardCvc);
    }
    // method to set card expiry month value
    public void setCardExpiryMonth(String cardExpiryMonth){
        driver.findElement(formExpiryMonthElement).sendKeys(cardExpiryMonth);
    }
    // method to set card expiry year value
    public void setCardExpiryYear(String cardExpiryYear){
        driver.findElement(formExpiryYearElement).sendKeys(cardExpiryYear);
    }
    // method to verify success message
    public String getSuccessMessage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the submit button to be present in the DOM
        WebElement submitButtonElement = wait.until(ExpectedConditions.presenceOfElementLocated(formSubmitButtonElement));

        // Scroll the logout button into view using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButtonElement);

        // Wait for the button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(submitButtonElement));

        // Click the submit button
        submitButtonElement.click();

        WebElement formSuccessMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(formSuccessMessage));

        return formSuccessMessageElement.getText();
    }

    public String getSuccessMessageBeforeRedirect() {
        // Submit the payment form
        driver.findElement(formSubmitButtonElement).click();

        // Debug: Print the current page source
        System.out.println("Page Source After Submission:\n" + driver.getPageSource());

        // Wait for the success message to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(formSuccessMessage));

        // Retrieve the success message text
        String messageText = successMessage.getText();

        // Debug: Print the success message
        System.out.println("Success Message: " + messageText);

        // Return the success message text
        return messageText;
    }

    // method to click submit button: call the verify success message method here and this leads us to a payment_done page
    public PaymentDonePage clickSubmitButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the submit button to be present in the DOM
        WebElement submitButtonElement = wait.until(ExpectedConditions.presenceOfElementLocated(formSubmitButtonElement));

        // Scroll the logout button into view using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButtonElement);

        // Wait for the button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(submitButtonElement));

        // Click the submit button
        submitButtonElement.click();
        return new PaymentDonePage(driver);
    }

    //Method to confirm logged or logout
    public HomePage clickLogout(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the logout button to be present in the DOM
        WebElement logoutButtonElement = wait.until(ExpectedConditions.presenceOfElementLocated(logoutButton));

        // Scroll the logout button into view using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutButtonElement);

        // Wait for the button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(logoutButtonElement));

        // Click the logout button
        logoutButtonElement.click();

        return new HomePage(driver);
    }
}
