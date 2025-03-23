package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CartProduct;

import java.time.Duration;

public class ProductDetailPage {
    private final WebDriver driver;
    private final By productNameElement = By.cssSelector(".product-information h2");
    private final By productCategoryElement = By.cssSelector(".product-information p");
    private final By productPriceElement = By.cssSelector("div.product-information > span > span");
    private final By quantityInputElement = By.id("quantity");
    private final By addToCartElement = By.cssSelector("span > button.btn.btn-default.cart\n");
    private final By cartModal = By.id("cartModal");
    private final By reviewSectionHeader = By.cssSelector("div>ul.nav-tabs>li.active");
    private final By reviewNameInputElement = By.id("name");
    private final By reviewEmailInputElement = By.id("email");
    private final By reviewTextBoxElement = By.id("review");
    private final By reviewSubmitButtonElement = By.id("button-review");
    private final By reviewAlertElement = By.cssSelector("#review-section span");

    // Add a field to store the new quantity
    private String updatedQuantity;

    public ProductDetailPage(WebDriver driver){
        this.driver = driver;
    }

    public String getProductName(){
        WebElement productName = driver.findElement(productNameElement);
        return productName.getText();
    }

    public String getProductCategory(){
        WebElement productCategory = driver.findElement(productCategoryElement);
        return productCategory.getText();
    }

    public String getProductPrice(){
        WebElement productPrice = driver.findElement(productPriceElement);
        return productPrice.getText();
    }

    public String getProductQuantity() {
        WebElement quantityInput = driver.findElement(quantityInputElement);
        // If updatedQuantity is null, return the value from the input field
        return (updatedQuantity == null || updatedQuantity.isEmpty()) ? quantityInput.getDomAttribute("value") : updatedQuantity;
    }

    public void changeProductQuantity(String increment){
        WebElement quantityInput = driver.findElement(quantityInputElement);
        quantityInput.clear(); // Clear the existing quantity
        quantityInput.sendKeys(increment); // Set the new quantity
        this.updatedQuantity = increment;
    }

    public void clickAddToCart(){
        driver.findElement(addToCartElement).click();
    }

    // Add this method to the ProductDetailPage class
    public CartProduct clickAddToCartButton() {
        // Get the product details before adding to cart
        String productName = getProductName();
        String productPrice = getProductPrice(); // Assuming you have a method to get the price
        String productQuantity = getProductQuantity();

        // Click the Add to Cart button
        driver.findElement(addToCartElement).click();

        // Return the product details as a CartProduct object
        return new CartProduct(productName, productPrice, productQuantity);
    }

    //method to get the modal Dialog
    private WebElement modalDialog(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartModal));
        return driver.findElement(cartModal);
    }

    // Method to read text of modal header
    public String modalContentHeaderText(){
        WebElement modalDialog = modalDialog();
        WebElement modalTitle = modalDialog.findElement(By.className("modal-title"));
        return modalTitle.getText();
    }

    // Method to read the text of modal body
    public String modalContentBodyText(){
        WebElement modalDialog = modalDialog();
        WebElement modalBodyText = modalDialog.findElement(By.cssSelector("div.modal-body > p.text-center:nth-of-type(1)"));
        return modalBodyText.getText();
    }

    // Method to interact with modal view cart button
    public CartPage clickModalContentViewCartButton(){
        WebElement modalDialog = modalDialog();
        WebElement modalViewCartButton = modalDialog.findElement(By.cssSelector("div.modal-body > p.text-center > a"));
        modalViewCartButton.click();
        return new CartPage(driver);
    }

    // Method to read text of modal footer
    public String modalContentFooterText(){
        WebElement modalDialog = modalDialog();
        WebElement modalFooterText = modalDialog.findElement(By.cssSelector("div.modal-footer > button"));
        return modalFooterText.getText();
    }

    // Method to interact with modal continue button
    public void clickModalFooterContinueButton(){
        WebElement modalDialog = modalDialog();
        WebElement modalContinueButton = modalDialog.findElement(By.cssSelector("div.modal-footer > button"));
        modalContinueButton.click();
    }

    public String getCurrentUrl(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(driver.getCurrentUrl()));
        return driver.getCurrentUrl();
    }

    // REVIEW ELEMENTS
    public String getReviewSectionHeaderText(){
        return driver.findElement(reviewSectionHeader).getText();
    }
    public void setReviewNameInputElement(String name){
        WebElement nameInputElement = driver.findElement(reviewNameInputElement);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", nameInputElement);
        nameInputElement.sendKeys(name);
    }

    public void setReviewEmailInputElement(String email){
        WebElement emailInputElement = driver.findElement(reviewEmailInputElement);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", emailInputElement);
        emailInputElement.sendKeys(email);
    }
    public void setReviewTextBoxElement(String review){
        WebElement reviewInputElement = driver.findElement(reviewTextBoxElement);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", reviewInputElement);
        reviewInputElement.sendKeys(review);
    }

    public void clickReviewSubmitButton(){
        WebElement reviewSubmitButton = driver.findElement(reviewSubmitButtonElement);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", reviewSubmitButton);
        reviewSubmitButton.click();
    }

    public String getReviewConfirmationMessage(){
        WebElement reviewConfirmation = driver.findElement(reviewAlertElement);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", reviewConfirmation);
        return reviewConfirmation.getText();
    }
}
