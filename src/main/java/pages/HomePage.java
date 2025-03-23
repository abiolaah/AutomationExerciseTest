package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    //Initializing web driver
    private final WebDriver driver;
    private final By pageHeaderText = By.cssSelector("#slider-carousel .item.active .col-sm-6 h1\n");
    private final By categoryElementText = By.cssSelector(".left-sidebar h2");
    private final By featuresItemElementText = By.cssSelector(".features_items .title.text-center");
    private final By footerElementText = By.xpath("//p[@class=\"pull-left\"]");
    private final By footerFormInput = By.id("susbscribe_email");
    private final By footerFormButton = By.id("subscribe");
    private final By footerSuccessMessage = By.id("success-subscribe");
    private final By loginText = By.cssSelector("ul.nav.navbar-nav > li:nth-of-type(10) > a\n");
    private final By logoutButton = By.cssSelector("a[href=\"/logout\"]");
    private final By deleteAccountButton = By.cssSelector("a[href=\"/delete_account\"]");


    //Constructor
    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    //Method to get page header element
    public String pageHeaderTextValue(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(pageHeaderText));
        WebElement pageHeaderElement = driver.findElement(pageHeaderText);
        return pageHeaderElement.getText();
    }

    //Method to get page header element
    public String categoryElementTextValue(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(categoryElementText));
        WebElement pageHeaderElement = driver.findElement(categoryElementText);
        return pageHeaderElement.getText();
    }

    //Method to get page header element
    public String featureItemsElementTextValue(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(featuresItemElementText));
        WebElement pageHeaderElement = driver.findElement(featuresItemElementText);
        return pageHeaderElement.getText();
    }

    //Method to confirm loggged or logout
    public AuthenticationsPage clickLogout(){
        driver.findElement(logoutButton).click();
        return new AuthenticationsPage(driver);
    }
    //Method to confirm loggged or logout
    public DeleteAccountPage clickDeleteAccount(){
        driver.findElement(deleteAccountButton).click();
        return new DeleteAccountPage(driver);
    }

    public String getText(){
        WebElement loginDeets = driver.findElement(loginText);
        return loginDeets.getText();
    }

    //Method to get footer element
    public String footerTextValue(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(footerElementText)));
        WebElement pageHeaderElement = driver.findElement(footerElementText);
        return pageHeaderElement.getText();
    }

    // Method interact with footer subscription

    // Method to fill subscription email field
    public void setSubscriptionEmail(String email){
        driver.findElement(footerFormInput).sendKeys(email);
    }

    //Method to click subscription button
    public void clickSubscriptionButton(){
        driver.findElement(footerFormButton).click();
    }

    // Method to retrieve success message
    public String getSubscriptionSuccessMessage(){
        return driver.findElement(footerSuccessMessage).getText();
    }


    // Methods Belows Redirect to other pages

    // Method to initiate Products page
    public ProductsPage clickProductNavigation(){
        clickLink("a[href=\"/products\"]");
        return new ProductsPage(driver);
    }

    // Method to initiate Cart page
    public CartPage clickCartNavigation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.nav.navbar-nav > li:nth-of-type(3) > a")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartLink);
        return new CartPage(driver);
    }

    // Method to initiate Cart page after login
    public CartPage clickCartNavigationAfterLogin() {
        int attempts = 0;
        while (attempts < 3) { // Retry up to 3 times
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul.nav.navbar-nav > li:nth-of-type(3) > a")));
                cartLink.click();
                return new CartPage(driver);
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("StaleElementReferenceException encountered. Retrying... Attempt: " + attempts);
            }
        }
        throw new RuntimeException("Failed to click cart navigation link after multiple attempts.");
    }

    // Method to initiate Authentication page
    public AuthenticationsPage clickAuthNavigation(){
        clickLink("ul.nav.navbar-nav > li:nth-of-type(4) > a\n");
        return new AuthenticationsPage(driver);
    }

    // Method to initiate Test case page
    public TestCasePage clickTestCaseNavigation(){
        clickLink("ul.nav.navbar-nav > li:nth-of-type(5) > a\n");
        return new TestCasePage(driver);
    }

    // Method to initiate API testing page
    public APITestingPage clickApiTestingNavigation(){
        clickLink("ul.nav.navbar-nav > li:nth-of-type(6) > a\n");
        return new APITestingPage(driver);
    }

    // Method to initiate Test case page via button
    public TestCasePage clickTestCaseButton(){
        clickLink("a.test_cases_list[href=\"/test_cases\"]:first-of-type");
        return new TestCasePage(driver);
    }

    // Method to initiate API testing page via button
    public APITestingPage clickApiTestingButton(){
        clickLink("a.apis_list[href=\"/api_list\"]:nth-of-type(2)");
        return new APITestingPage(driver);
    }

    // Method to initiate Video Tutorial page
    public VideoTutorialsPage clickVideoTutorialsNavigation(){
        clickLink("ul.nav.navbar-nav > li:nth-of-type(7) > a\n");
        return  new VideoTutorialsPage(driver);
    }

    // Method to initiate Contact Us page
    public ContactUsPage clickContactUsNavigation(){
        clickLink("ul.nav.navbar-nav > li:nth-of-type(8) > a\n");
        return new ContactUsPage(driver);
    }


    // helper method to related with navbar menu
    private void clickLink(String linkText){
        driver.findElement(By.cssSelector(linkText)).click();
    }
}