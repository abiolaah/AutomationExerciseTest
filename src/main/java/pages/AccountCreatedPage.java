package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountCreatedPage {
    private final WebDriver driver;
    private final By sectionTitleElement = By.cssSelector("section#form> div.container > div.row > div > h2 > b");
    private final By sectionTextElement = By.cssSelector("section#form> div.container > div.row > div > p");
    private final By continueButtonElement = By.cssSelector("div.pull-right > a");

    public AccountCreatedPage(WebDriver driver) {
        this.driver = driver;
    }

    // method to get section Title
    public String getSectionTitleText(){
        return driver.findElement(sectionTitleElement).getText();
    }

    // method to get section text
    public String getSectionParagraphText(){
        return driver.findElement(sectionTextElement).getText();
    }

    // method to click continue button: this routes back to home page
    public HomePage clickContinueButton(){
        driver.findElement(continueButtonElement).click();
        return new HomePage(driver);
    }

    //Content
//    Account Created!
//    Congratulations! Your new account has been successfully created!
//    You can now take advantage of member privileges to enhance your online shopping experience with us.
}
