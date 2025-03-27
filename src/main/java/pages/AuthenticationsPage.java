package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthenticationsPage {
    private final WebDriver driver;
    private final By loginSectionTitleElement = By.cssSelector("div.login-form > h2");
    private final By registerSectionTitleElement = By.cssSelector("div.signup-form > h2");
    private final By loginEmailElement = By.cssSelector("input[data-qa=\"login-email\"]");
    private final By loginPasswordElement = By.cssSelector("input[name=\"password\"]");
    private final By loginButtonElement = By.cssSelector("button[data-qa=\"login-button\"]");
    private final By loginFailureNoteElement = By.cssSelector("form[action=\"/login\"] > p");
    private final By registerNameElement = By.cssSelector("input[name=\"name\"]");
    private final By registerEmailElement = By.cssSelector("input[data-qa=\"signup-email\"]");
    private final By registerButtonElement = By.cssSelector("button[data-qa=\"signup-button\"]");
    private final By registerFailureNoteElement = By.cssSelector("form[action=\"/login\"] > p");

    public AuthenticationsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getCurrentUrl(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(driver.getCurrentUrl()));
        return driver.getCurrentUrl();
    }

    // method to get login section Title
    public String getLoginTitleText(){
        return driver.findElement(loginSectionTitleElement).getText();
    }
    // method to get register section Title
    public String getRegisterTitleText(){
        return driver.findElement(registerSectionTitleElement).getText();
    }

    // method to set login email value
    public void setLoginEmail(String email){
        driver.findElement(loginEmailElement).sendKeys(email);
    }
    // method to get login password value
    public void setLoginPasswordElement(String password){
        driver.findElement(loginPasswordElement).sendKeys(password);
    }
    // method to click login button: this routes back to home page
    public HomePage clickLogin(){
        driver.findElement(loginButtonElement).click();
        return new HomePage(driver);
    }

    //Method to get login failure note
    public String getLoginFailureText(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginFailureNoteElement));
        return driver.findElement(loginFailureNoteElement).getText();
    }

    // method to get register name value
    public void setRegisterNameElement(String name){
        driver.findElement(registerNameElement).sendKeys(name);
    }
    // method to get register email value
    public void setRegisterEmailElement(String email){
        driver.findElement(registerEmailElement).sendKeys(email);
    }
    // method to click signup button: this routes to signup page
    public AccountSetUpPage clickRegister(){
        driver.findElement(registerButtonElement).click();
        // Wait for new page to load
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("signup")); // adjust URL fragment as needed
        return new AccountSetUpPage(driver);
    }

    //Method to get register failure note
    public String getRegisterFailureText(){
        return driver.findElement(registerFailureNoteElement).getText();
    }
}
