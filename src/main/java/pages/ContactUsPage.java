package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class ContactUsPage {
    private WebDriver driver;
    private final By pageHeaderElement = By.cssSelector("div.col-sm-12 > h2");
    private final By formTitleElement = By.cssSelector("div.contact-form> h2");
    private final By formNameElement = By.cssSelector("input[name=\"name\"]");
    private final By formEmailElement = By.cssSelector("input[name=\"email\"]\n");
    private final By formSubjectElement = By.cssSelector("input[name=\"subject\"]");
    private final By formMessageElement = By.cssSelector("textarea[name=\"message\"]");
    private final By formUploadButtonElement = By.cssSelector("input[name=\"upload_file\"]");
    private final By formSubmitButtonElement = By.cssSelector("input[type=\"submit\"]");
    private final By formSuccessMessage = By.cssSelector("div.contact-form > div.status");
    private final By homeButton = By.cssSelector("div#form-section a");
    private final By feedbackTitleElement = By.cssSelector("div.contact-info h2");
    private final By feedbackTextElement = By.cssSelector("div.contact-info address");
    private final By feedbackEmailElement = By.cssSelector("div.contact-info a");

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageHeaderText(){
        WebElement pageHeader = driver.findElement(pageHeaderElement);
        return pageHeader.getText();
    }

    public String getFormTitleText(){
        WebElement formTitle = driver.findElement(formTitleElement);
        return formTitle.getText();
    }

    public void setName(String name){
        driver.findElement(formNameElement).sendKeys(name);
    }

    public void setEmail(String email){
        driver.findElement(formEmailElement).sendKeys(email);
    }

    public void setSubject(String subject){
        driver.findElement(formSubjectElement).sendKeys(subject);
    }

    public void setMessage(String message){
        driver.findElement(formMessageElement).sendKeys(message);
    }

    public void uploadFile(String absolutePath){
        driver.findElement(formUploadButtonElement).sendKeys(absolutePath);
    }

    public void clickSubmitButton(){
        WebElement submitButton = driver.findElement(formSubmitButtonElement);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        submitButton.click();
    }

    public String getAlertText(){
        return driver.switchTo().alert().getText();
    }

    public void clickToDismissAlert(){
        driver.switchTo().alert().dismiss();
    }

    public void clickToAcceptAlert(){
        driver.switchTo().alert().accept();
    }

    public String getPageUrl(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.getCurrentUrl();
    }

    public String getSuccessMessage(){
        WebElement successMessage = driver.findElement(formSuccessMessage);
        return successMessage.getText();
    }

    public HomePage clickHomeButton (){
        driver.findElement(homeButton).click();
        return new HomePage(driver);
    }

    public String getFeedbackSectionTitle(){
        WebElement sectionTitle = driver.findElement(feedbackTitleElement);
        return sectionTitle.getText();
    }

    public String getFeedbackText(){
        WebElement sectionParagraphText = driver.findElement(feedbackTextElement);
        return sectionParagraphText.getText();
    }

    public String getFeedbackEmail(){
        WebElement sectionParagraphEmail = driver.findElement(feedbackEmailElement);
        return sectionParagraphEmail.getText();
    }
}
