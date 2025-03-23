package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentPage {
    private final WebDriver driver;
    private final By sectionTitleElement = By.className("heading");
    private final By formCardNameElement = By.cssSelector("input[name=\"name_on_card\"]");
    private final By formCardNumberElement = By.cssSelector("input[name=\"card_number\"]");
    private final By formCVCElement = By.cssSelector("input[name=\"cvc\"]");
    private final By formExpiryMonthElement = By.cssSelector("input[name=\"expiry_month\"]");
    private final By formExpiryYearElement = By.cssSelector("input[name=\"expiry_year\"]");
    private final By formSubmitButtonElement = By.id("submit");
    private final By formSuccessMessage = By.className("alert-success alert");
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
        driver.findElement(formSubmitButtonElement).click();
        return driver.findElement(formSuccessMessage).getText();
    }
    // method to click submit button: call the verify success message method here and this leads us to a payment_done page
    public PaymentDonePage clickSubmitButton(){
        driver.findElement(formSubmitButtonElement).click();
        return new PaymentDonePage(driver);
    }

    //Method to confirm loggged or logout
    public HomePage clickLogout(){
        driver.findElement(logoutButton).click();
        return new HomePage(driver);
    }
}
