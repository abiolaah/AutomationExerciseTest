package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class AccountSetUpPage {
    private final WebDriver driver;
    private final By sectionTitleElement = By.cssSelector("div.login-form > h2");
    private final By mrTitleRadioButtonElement = By.id("id_gender1");
    private final By mrsTitleRadioButtonElement = By.id("id_gender2");
    private final By nameElement = By.id("name");
    private final By emailElement = By.id("email");
    private final By passwordElement = By.id("password");
    private final By dayDropDownElement = By.id("days");
    private final By monthDropDownElement = By.id("months");
    private final By yearDropDownElement = By.id("years");
    private final By signUpCheckboxElement = By.id("newsletter");
    private final By specialOfferCheckboxElement = By.id("optin");
    private final By addressSectionTitleElement = By.cssSelector("div.login-form > form > h2");
    private final By firstNameElement = By.id("first_name");
    private final By lastNameElement = By.id("last_name");
    private final By companyElement = By.id("company");
    private final By addressLine1Element = By.id("address1");
    private final By addressLine2Element = By.id("address2");
    private final By countryDropDownElement = By.id("country");
    private final By stateElement = By.id("state");
    private final By cityElement = By.id("city");
    private final By zipCodeElement = By.id("zipcode");
    private final By phoneElement = By.id("mobile_number");
    private final By createButtonElement = By.cssSelector("button[data-qa=\"create-account\"]");

    public AccountSetUpPage(WebDriver driver) {
        this.driver = driver;
    }

    // method to get main section Title
    public String getSectionTitle(){
        return driver.findElement(sectionTitleElement).getText();
    }
    // method to verify value in Name
    public String getNameValue(){
        WebElement nameInput = driver.findElement(nameElement);
        return nameInput.getDomAttribute("value");
    }
    // method to verify value in email
    public String getEmailValue(){
        WebElement emailInput = driver.findElement(emailElement);
        return emailInput.getDomAttribute("value");
    }
    // method to set title radio button value
    public void setMrTitleValue(){
        driver.findElement(mrTitleRadioButtonElement).click();
    }
    public void setMrsTitleValue(){
        driver.findElement(mrsTitleRadioButtonElement).click();
    }

    public void setTitleValue(String title){
        if (title.equalsIgnoreCase("Mrs")){
          setMrsTitleValue();
        }
        else {
            setMrTitleValue();
        }
    }
    // method to set password value
    public void setPasswordValue(String passwordValue){
        driver.findElement(passwordElement).sendKeys(passwordValue);
    }
    // method to set day value
    public void setDayValue(String option){
        findDropDownElement(dayDropDownElement).selectByValue(option);
    }
    // method to set month value
    public void setMonthValue(String option){
        findDropDownElement(monthDropDownElement).selectByValue(option);
    }
    // method to set year value
    public void setYearValue(String option){
        findDropDownElement(yearDropDownElement).selectByValue(option);
    }
    // method to set signUp checkbox value
    public void setSignUpCheckbox(){
        WebElement signUpCheckbox = driver.findElement(signUpCheckboxElement);

        // Scroll the element into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", signUpCheckbox);

        // Wait for the element to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(signUpCheckbox));

        // Click the element
        signUpCheckbox.click();
    }
    // method to set special offer checkbox value
    public void setSpecialOfferCheckbox(){
        WebElement specialOfferCheckbox = driver.findElement(specialOfferCheckboxElement);

        // Scroll the element into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", specialOfferCheckbox);

        // Wait for the element to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(specialOfferCheckbox));

        specialOfferCheckbox.click();
    }
    // method to get address section Title
    public String getAddressSectionTitle(){
        return driver.findElement(addressSectionTitleElement).getText();
    }
    // method to set first name value
    public void setFirstNameValue(String firstName){
        driver.findElement(firstNameElement).sendKeys(firstName);
    }
    // method to set last name value
    public void setLastNameValue(String lastName){
        driver.findElement(lastNameElement).sendKeys(lastName);
    }
    // method to set company value
    public void setCompanyValue(String company){
        driver.findElement(companyElement).sendKeys(company);
    }
    // method to set address line 1 value
    public void setAddressLine1Value(String addressOne){
        driver.findElement(addressLine1Element).sendKeys(addressOne);
    }
    // method to set address line 2 value
    public void setAddressLine2Value(String addressTwo){
        driver.findElement(addressLine2Element).sendKeys(addressTwo);
    }
    // method to set country dropdown value
    public void setCountryValue(String option){
        findDropDownElement(countryDropDownElement).selectByValue(option);
    }

    public List<String> getSelectedCountryOptions(){
        List<String> selectedCountryOptionText = getSelectedOptions(countryDropDownElement);
        return selectedCountryOptionText;
    }

    // method to set state value
    public void setStateValue(String state){
        driver.findElement(stateElement).sendKeys(state);
    }
    // method to set city value
    public void setCityValue(String city){
        driver.findElement(cityElement).sendKeys(city);
    }
    // method to set zipcode value
    public void setZipCodeValue(String zipCode){
        driver.findElement(zipCodeElement).sendKeys(zipCode);
    }
    // method to set phone value
    public void setPhoneNumberValue(String number){
        driver.findElement(phoneElement).sendKeys(number);
    }
    // method to click create button
    public AccountCreatedPage clickCreateButton(){
        driver.findElement(createButtonElement).click();
        return new AccountCreatedPage(driver);
    }

    // util function
    public List<String> getSelectedOptions(By dropDownType){
        List<WebElement> selectedElements = findDropDownElement(dropDownType).getAllSelectedOptions();
//        List<String> selectedElementText = new ArrayList<>();
        return selectedElements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    // private method to interact with dropdown
    private Select findDropDownElement(By dropdownType){
        return new Select(driver.findElement(dropdownType));
    }
}
