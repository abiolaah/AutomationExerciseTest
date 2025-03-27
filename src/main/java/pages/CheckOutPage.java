package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CheckOutPage {
    private final WebDriver driver;

    private final By checkOutCrumbs = By.cssSelector("div.breadcrumbs > ol.breadcrumb>li.active");
    private final By logoutButton = By.cssSelector("a[href=\"/logout\"]");

    private final By addressSectionTitleElement = By.xpath("//div[@class='step-one']/h2[text()='Address Details']\n");
    private final By orderSectionTitleElement = By.xpath("//div[@class='step-one']/h2[text()='Review Your Order']\n");

    private final By deliveryNameElement = By.cssSelector("#address_delivery .address_firstname");
    private final By deliveryCompanyElement = By.cssSelector("#address_delivery .address_address1.address_address2:nth-child(3)");
    private final By deliveryAddressLineElement = By.cssSelector("#address_delivery .address_address1.address_address2:nth-child(4)");
    private final By deliveryPostalElement = By.cssSelector("#address_delivery .address_postcode\n");
    private final By deliveryCountryElement = By.cssSelector("#address_delivery .address_country_name\n");
    private final By deliveryPhoneElement = By.cssSelector("#address_delivery .address_phone\n");

    private final By invoiceNameElement = By.cssSelector("#address_invoice .address_firstname\n");
    private final By invoiceCompanyElement = By.cssSelector("#address_invoice .address_address1.address_address2:nth-child(3)");
    private final By invoiceAddressLineElement = By.cssSelector("#address_invoice .address_address1.address_address2:nth-child(4)");
    private final By invoicePostalElement = By.cssSelector("#address_invoice .address_postcode\n");
    private final By invoiceCountryElement = By.cssSelector("#address_invoice .address_country_name\n");
    private final By invoicePhoneElement = By.cssSelector("#address_invoice .address_phone");

    private final By productNameElement = By.cssSelector("tr[id^=\"product\"] .cart_description > h4 > a");
    private final By productPriceElement = By.cssSelector("tr[id^=\"product\"] .cart_price > p");
    private final By productQuantityElement = By.cssSelector("tr[id^=\"product\"] .cart_quantity > button");
    private final By productTotalAmountElement = By.cssSelector("tr[id^=\"product\"] .cart_total_price");

    private final By totalAmountElement = By.cssSelector("tr:not([id]) .cart_total_price");

    private final By noteTextAreaElement = By.cssSelector("textarea[name=\"message\"]");
    private final By placeOrderButtonElement = By.cssSelector("div>a[href=\"/payment\"]");


    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
    }

    // method to get current page url
    public String getCurrentUrl(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(driver.getCurrentUrl()));
        return driver.getCurrentUrl();
    }

    // method to read breadcrumb text
    public String getCheckOutBreadCrumb(){
        return driver.findElement(checkOutCrumbs).getText();
    }

    // method to get address section title
    public String getAddressSectionTitle(){
        WebElement addressSection = driver.findElement(addressSectionTitleElement);
        return addressSection.getText();
    }
    // method to get order section title
    public String getOrderSectionTitle(){
        WebElement orderSection = driver.findElement(orderSectionTitleElement);
        return orderSection.getText();
    }
    // method to verify delivery name
    public String getDeliveryName(){
        WebElement deliveryName = driver.findElement(deliveryNameElement);
        return deliveryName.getText();
    }

    // method to verify delivery company
    public String getDeliveryCompany(){
        WebElement deliveryCompany = driver.findElement(deliveryCompanyElement);
        return deliveryCompany.getText();
    }

    // method to verify delivery address
    public String getDeliveryAddressLine(){
        WebElement deliveryAdd = driver.findElement(deliveryAddressLineElement);
        return deliveryAdd.getText();
    }
    // method to verify delivery postal
    public String getDeliveryCityStateZip(){
        WebElement deliveryPostal = driver.findElement(deliveryPostalElement);
        return deliveryPostal.getText();
    }
    // method to verify delivery country
    public String getDeliveryCountry(){
        WebElement deliveryCountry = driver.findElement(deliveryCountryElement);
        return deliveryCountry.getText();
    }
    // method to verify delivery phone
    public String getDeliveryPhone(){
        WebElement deliveryPhone = driver.findElement(deliveryPhoneElement);
        return deliveryPhone.getText();
    }
    // method to verify invoice name
    public String getInvoiceName(){
        WebElement invoiceName = driver.findElement(invoiceNameElement);
        return invoiceName.getText();
    }

    // method to verify invoice company
    public String getInvoiceCompany(){
        WebElement invoiceCompany = driver.findElement(invoiceCompanyElement);
        return invoiceCompany.getText();
    }

    // method to verify invoice address
    public String getInvoiceAddressLine(){
        WebElement invoiceAddress = driver.findElement(invoiceAddressLineElement);
        return invoiceAddress.getText();
    }
    // method to verify invoice postal
    public String getInvoiceCityStateZip(){
        WebElement invoicePostal = driver.findElement(invoicePostalElement);
        return invoicePostal.getText();
    }
    // method to verify invoice country
    public String getInvoiceCountry(){
        WebElement invoiceCountry = driver.findElement(invoiceCountryElement);
        return invoiceCountry.getText();
    }
    // method to verify invoice phone
    public String getInvoicePhone(){
        WebElement invoicePhone = driver.findElement(invoicePhoneElement);
        return invoicePhone.getText();
    }
    // method to get products name
    public List<String> getProductNames(){
        List<WebElement> nameElements = driver.findElements(productNameElement);
        List<String> names = new ArrayList<>();
        for(WebElement element: nameElements){
            names.add(element.getText().trim());
        }
        return names;
    }
    // method to get product price
    public List<String> getProductPrices(){
        List<WebElement> priceElements = driver.findElements(productPriceElement);
        List<String> prices = new ArrayList<>();
        for(WebElement element: priceElements){
            prices.add(element.getText().trim());
        }
        return prices;
    }
    // method to get product quantity
    public List<String> getProductQuantity(){
        List<WebElement> quantityElements = driver.findElements(productQuantityElement);
        List<String> quantities = new ArrayList<>();
        for(WebElement element: quantityElements){
            quantities.add(element.getText().trim());
        }
        return quantities;
    }
    // method to get product total price
    public List<String> getProductTotalPrice(){
        List<WebElement> totalPriceElements = driver.findElements(productTotalAmountElement);
        List<String> totalPrices = new ArrayList<>();
        for(WebElement element: totalPriceElements){
            totalPrices.add(element.getText().trim());
        }
        return totalPrices;
    }
    // method to get total amount
    public String getTotalAmount(){
        WebElement totalAmount = driver.findElement(totalAmountElement);
        return totalAmount.getText();
    }

    // method to add note
    public void setDeliveryNote(String note){
        driver.findElement(noteTextAreaElement).sendKeys(note);
    }
    // method to click place order
    public PaymentPage clickPlaceOrderButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Scroll to and wait for button to be clickable
        WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(placeOrderButtonElement));

        // Scroll the element into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeOrderButton);

        placeOrderButton.click();

        return new PaymentPage(driver);
    }

    public boolean isLogoutButtonVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
            return true;
        } catch (Exception e) {
            return false;
        }
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
