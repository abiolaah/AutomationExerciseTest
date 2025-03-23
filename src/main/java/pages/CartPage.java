package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CartProduct;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CartPage {
    private final WebDriver driver;
    private final By shoppingCartCrumbs = By.cssSelector("div.breadcrumbs > ol.breadcrumb>li.active");
    private final By emptyCartText = By.cssSelector("#empty_cart .text-center");
    private final By productsButton = By.cssSelector("#empty_cart .text-center a[href=\"/products\"]");
//    private final By checkoutButton = By.cssSelector("div > a.check_out");
    private final By checkoutButton = By.cssSelector("div.col-sm-6 > a");
    private final By deleteButton = By.cssSelector("#cart_info_table tbody tr .cart_quantity_delete");
    private final By checkoutModal = By.id("checkoutModal");

    private final By productsNavButton = By.cssSelector("a[href=\"/products\"]");
    private final By logoutButton = By.cssSelector("a[href=\"/logout\"]");

    // Add these selectors to the CartPage class
    private final By productNameInCart = By.cssSelector(".cart_description h4 a");
    private final By productPriceInCart = By.cssSelector(".cart_price p");
    private final By productQuantityInCart = By.cssSelector(".cart_quantity button");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getCurrentUrl(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(driver.getCurrentUrl()));
        return driver.getCurrentUrl();
    }

    // method to read breadcrumb text
    public String getBreadCrumb(){
        return driver.findElement(shoppingCartCrumbs).getText();
    }

    // method to read page text when cart is empty
    public String getEmptyCartText(){
        WebElement emptyCartTextElement = driver.findElement(emptyCartText);
        return emptyCartTextElement.getText();
    }

    public ProductsPage redirectToProducts(){
        driver.findElement(productsButton).click();
        return new ProductsPage(driver);
    }
    public ProductsPage redirectToProductsPage(){
        driver.findElement(productsNavButton).click();
        return new ProductsPage(driver);
    }

    // Add this method to the CartPage class
    public List <CartProduct> getProductDetailsInCart() {
        List<CartProduct> cartProducts = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Find all rows in the cart table
        List<WebElement> productRows = driver.findElements(By.cssSelector("#cart_info_table tbody tr"));

        for (WebElement row : productRows) {
            // Retrieve product details from each row
            String productName = row.findElement(productNameInCart).getText();
            String productPrice = row.findElement(productPriceInCart).getText();
            String productQuantity = row.findElement(productQuantityInCart).getText();

            // Create a CartProduct object and add it to the list
            cartProducts.add(new CartProduct(productName, productPrice, productQuantity));
        }

        return cartProducts;
    }

    public void clickProceedToCheckout(){
        driver.findElement(checkoutButton).click();
    }

    public CheckOutPage clickProceedToCheckoutLoggedIn(){
        driver.findElement(checkoutButton).click();
        return new CheckOutPage(driver);
    }

    // Add this method to the CartPage class
    public void clickDeleteRandomProductButton() {
        // Find all delete buttons in the cart
        List<WebElement> deleteButtons = driver.findElements(deleteButton);

        // Check if there are any products in the cart
        if (deleteButtons.isEmpty()) {
            throw new IllegalStateException("No products found in the cart to delete.");
        }

        // Randomly select a delete button
        Random random = new Random();
        int randomIndex = random.nextInt(deleteButtons.size());

        WebElement randomDeleteButton = deleteButtons.get(randomIndex);

        // Scroll the element into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomDeleteButton);

        // Wait for the element to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(randomDeleteButton));

        // Click the randomly selected delete button
        randomDeleteButton.click();

        // Wait for the cart to update after deletion
        wait.until(ExpectedConditions.invisibilityOf(randomDeleteButton));

        // Optional: Refresh the page to ensure the cart is updated
        driver.navigate().refresh();

        // Wait for the cart to load after refresh
        wait.until(ExpectedConditions.presenceOfElementLocated(shoppingCartCrumbs));
    }

    // Method to delete all products in the cart
    public void deleteAllProductsInCart() {
        List<WebElement> deleteButtons = driver.findElements(deleteButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        while (!deleteButtons.isEmpty()) {
            WebElement deleteButtonElement = deleteButtons.get(0);

            // Scroll the element into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteButtonElement);

            // Wait for the element to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(deleteButtonElement));

            // Click the delete button
            deleteButtonElement.click();

            // Wait for the cart to update after deletion
            wait.until(ExpectedConditions.invisibilityOf(deleteButtonElement));

            // Refresh the list of delete buttons
            deleteButtons = driver.findElements(deleteButton);
        }

        // Optional: Refresh the page to ensure the cart is updated
        driver.navigate().refresh();

        // Wait for the cart to load after refresh
        wait.until(ExpectedConditions.presenceOfElementLocated(shoppingCartCrumbs));
    }

    //method to get the modal Dialog
    private WebElement modalDialog(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutModal));
        return driver.findElement(checkoutModal);
    }

    public String getModalTitle(){
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

    // Method to interact with modal login/register button
    public AuthenticationsPage clickModalContentAuthButton(){
        WebElement modalDialog = modalDialog();
        WebElement modalViewCartButton = modalDialog.findElement(By.cssSelector("div.modal-body > p.text-center > a"));
        modalViewCartButton.click();
        return new AuthenticationsPage(driver);
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

    //Method to confirm loggged or logout
    public AuthenticationsPage clickLogout(){
        driver.findElement(logoutButton).click();
        return new AuthenticationsPage(driver);
    }
}
