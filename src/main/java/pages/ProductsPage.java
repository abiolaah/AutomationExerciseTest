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
import java.util.Random;

public class ProductsPage {
    private final WebDriver driver;
    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");

    private final By womenTopsCategoryFilterButton = By.cssSelector("a[href=\"/category_products/2\"]");
    private final By womenSareeCategoryFilterButton = By.cssSelector("a[href=\"/category_products/7\"]");
    private final By womenDressCategoryFilterButton = By.cssSelector("a[href=\"/category_products/1\"]");

    private final By menTShirtCategoryFilterButton = By.cssSelector("a[href=\"/category_products/3\"]");
    private final By menJeanCategoryFilterButton = By.cssSelector("a[href=\"/category_products/6\"]");

    private final By kidsDressCategoryFilterButton = By.cssSelector("a[href=\"/category_products/4\"]");
    private final By kidsTopsCategoryFilterButton = By.cssSelector("a[href=\"/category_products/5\"]");

    private final By poloBrandsFilterButton = By.xpath("//div[@class='brands-name']//ul/li[1]/a");
    private final By hmBrandsFilterButton = By.xpath("//div[@class='brands-name']//ul/li[2]/a");
    private final By madameBrandsFilterButton = By.xpath("//div[@class='brands-name']//ul/li[3]/a");
    private final By mastBrandsFilterButton = By.xpath("//div[@class='brands-name']//ul/li[4]/a");
    private final By babyHugBrandsFilterButton = By.xpath("//div[@class='brands-name']//ul/li[5]/a");
    private final By allenBrandsFilterButton = By.xpath("//div[@class='brands-name']//ul/li[6]/a");
    private final By kookieBrandsFilterButton = By.xpath("//div[@class='brands-name']//ul/li[7]/a");
    private final By bibaBrandsFilterButton = By.xpath("//div[@class='brands-name']//ul/li[8]/a");

    private final By productCards = By.cssSelector(".features_items .product-image-wrapper");

    private final By productNameInProductInfo = By.cssSelector(".productinfo p");
    private final By productPriceInProductInfo = By.cssSelector(".productinfo h2");

    private final By addToCartButton = By.cssSelector(".productinfo .add-to-cart");

    private final By viewProductButton = By.cssSelector(".choose a");

    private final By cartModal = By.id("cartModal");

    private final By cartNavMenu = By.cssSelector("ul.nav.navbar-nav > li:nth-of-type(3) > a\n");

    private final By itemSectionHeaderText = By.className("title");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }


    //Class for Product Data
    public static class Product{
        public String id;
        public String name;
        public String price;
        public String quantity;

        public Product(String id, String name, String price, String quantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    ", quantity='" + quantity + '\'' +
                    '}';
        }
    }

    // Method to extract products
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        List<WebElement> productElements = driver.findElements(productCards);

        for (WebElement productElement : productElements) {
            String id = productElement.findElement(addToCartButton).getDomAttribute("data-product-id");
            String name = productElement.findElement(productNameInProductInfo).getText();
            String price = productElement.findElement(productPriceInProductInfo).getText();
            String quantity = "1";
            products.add(new Product(id, name, price,quantity));
        }

        return products;
    }

    //Method to get page header element
    public String pageHeaderTextValue(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        WebElement pageHeaderElement = driver.findElement(itemSectionHeaderText);
        return pageHeaderElement.getText();
    }

    public void setSearchParams (String searchParams) {
        driver.findElement(searchInput).sendKeys(searchParams);
    }

    public void clickSearchButton(){
        driver.findElement(searchButton).click();
    }

    //Method to get page header element after search
    public String searchHeaderTextValue(String searchParams){
        setSearchParams(searchParams);
        clickSearchButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        WebElement pageHeaderElement = driver.findElement(itemSectionHeaderText);
        return pageHeaderElement.getText();
    }

    public String confirmHeaderTextChangeForWomenDress() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Scroll into view and ensure the button is clickable
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href=\"#Women\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();

        // Click the dress filter button
        WebElement dressButton = wait.until(ExpectedConditions.elementToBeClickable(womenDressCategoryFilterButton));
        dressButton.click();

        // Wait for the header to be present and get its text
        WebElement pageHeaderElement = wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        return pageHeaderElement.getText();
    }


    public String confirmHeaderTextForWomenTops(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Scroll into view and ensure the button is clickable
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href=\"#Women\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();

        // Click the dress filter button
        WebElement topButton = wait.until(ExpectedConditions.elementToBeClickable(womenTopsCategoryFilterButton));
        topButton.click();

        // Wait for the header to be present and get its text
        WebElement pageHeaderElement = wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        return pageHeaderElement.getText();
    }

    public String confirmHeaderTextForWomenSaree(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Scroll into view and ensure the button is clickable
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href=\"#Women\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();

        // Click the dress filter button
        WebElement topButton = wait.until(ExpectedConditions.elementToBeClickable(womenSareeCategoryFilterButton));
        topButton.click();

        // Wait for the header to be present and get its text
        WebElement pageHeaderElement = wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        return pageHeaderElement.getText();
    }

    public String confirmHeaderTextForMenTShirts(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Scroll into view and ensure the button is clickable
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href=\"#Men\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();

        // Click the dress filter button
        WebElement topButton = wait.until(ExpectedConditions.elementToBeClickable(menTShirtCategoryFilterButton));
        topButton.click();

        // Wait for the header to be present and get its text
        WebElement pageHeaderElement = wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        return pageHeaderElement.getText();
    }

    public String confirmHeaderTextForMenJean(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Scroll into view and ensure the button is clickable
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href=\"#Men\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();

        // Click the dress filter button
        WebElement topButton = wait.until(ExpectedConditions.elementToBeClickable(menJeanCategoryFilterButton));
        topButton.click();

        // Wait for the header to be present and get its text
        WebElement pageHeaderElement = wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        return pageHeaderElement.getText();
    }

    public String confirmHeaderTextForKidDress(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Scroll into view and ensure the button is clickable
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href=\"#Kids\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();

        // Click the dress filter button
        WebElement topButton = wait.until(ExpectedConditions.elementToBeClickable(kidsDressCategoryFilterButton));
        topButton.click();

        // Wait for the header to be present and get its text
        WebElement pageHeaderElement = wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        return pageHeaderElement.getText();
    }

    public String confirmHeaderTextForKidTop(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Scroll into view and ensure the button is clickable
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href=\"#Kids\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();

        // Click the dress filter button
        WebElement topButton = wait.until(ExpectedConditions.elementToBeClickable(kidsTopsCategoryFilterButton));
        topButton.click();

        // Wait for the header to be present and get its text
        WebElement pageHeaderElement = wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        return pageHeaderElement.getText();
    }

    public String confirmHeaderForPoloBrandFilter(){
        WebElement poloFilterButton = driver.findElement(poloBrandsFilterButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", poloFilterButton);
        poloFilterButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        WebElement pageHeaderElement = driver.findElement(itemSectionHeaderText);
        return pageHeaderElement.getText();
    }

    public String confirmHeaderForHMBrandFilter(){
        WebElement hMFilterButton = driver.findElement(hmBrandsFilterButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", hMFilterButton);
        hMFilterButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        WebElement pageHeaderElement = driver.findElement(itemSectionHeaderText);
        return pageHeaderElement.getText();
    }

    public String confirmHeaderForMadameBrandFilter(){
        WebElement madameFilterButton = driver.findElement(madameBrandsFilterButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", madameFilterButton);
        madameFilterButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        WebElement pageHeaderElement = driver.findElement(itemSectionHeaderText);
        return pageHeaderElement.getText();
    }

    public String confirmHeaderForMastBrandFilter(){
        WebElement mastFilterButton = driver.findElement(mastBrandsFilterButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", mastFilterButton);
        mastFilterButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        WebElement pageHeaderElement = driver.findElement(itemSectionHeaderText);
        return pageHeaderElement.getText();
    }

    public String confirmHeaderForBabyHugBrandFilter(){
        WebElement filterButton = driver.findElement(babyHugBrandsFilterButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        WebElement pageHeaderElement = driver.findElement(itemSectionHeaderText);
        return pageHeaderElement.getText();
    }

    public String confirmHeaderForAllenBrandFilter(){
        WebElement filterButton = driver.findElement(allenBrandsFilterButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        WebElement pageHeaderElement = driver.findElement(itemSectionHeaderText);
        return pageHeaderElement.getText();
    }

    public String confirmHeaderForKookieBrandFilter(){
        WebElement filterButton = driver.findElement(kookieBrandsFilterButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        WebElement pageHeaderElement = driver.findElement(itemSectionHeaderText);
        return pageHeaderElement.getText();
    }

    public String confirmHeaderForBibaBrandFilter(){
        WebElement filterButton = driver.findElement(bibaBrandsFilterButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(itemSectionHeaderText));
        WebElement pageHeaderElement = driver.findElement(itemSectionHeaderText);
        return pageHeaderElement.getText();
    }

    public void clickAddToCartFilterButton(){
        List<Product> products = getProducts();
        Random random = new Random();
        int randomIndex = random.nextInt(products.size());

        // Locate and click the Add to Cart button for the random product
        WebElement randomAddToCartButton = driver.findElements(addToCartButton).get(randomIndex);

        // Scroll the button into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomAddToCartButton);

        // Wait for the button to be visible and clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(randomAddToCartButton));

        // Click the button
        randomAddToCartButton.click();
    }

    public Product clickAddToCartButton(){
        List<Product> products = getProducts();
        Random random = new Random();
        int randomIndex = random.nextInt(products.size());
        Product selectedProduct = products.get(randomIndex);

        // Locate and click the Add to Cart button for the random product
        WebElement randomAddToCartButton = driver.findElements(addToCartButton).get(randomIndex);

        // Scroll the button into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomAddToCartButton);

        // Locate the product name and price based on the random index
        String productName = driver.findElements(productNameInProductInfo).get(randomIndex).getText().trim();
        String productPrice = driver.findElements(productPriceInProductInfo).get(randomIndex).getText().trim();

        // Set the name and price in the selected product object
        selectedProduct.name = productName;
        selectedProduct.price = productPrice;

        // Set a default quantity (e.g., "1") since quantity is not retrieved here
        selectedProduct.quantity = "1";


        // Wait for the button to be visible and clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(randomAddToCartButton));

        // Click the button
        randomAddToCartButton.click();

        // Return selected product object
        return selectedProduct;
    }

    public CartPage clickAddToCartNavMenu(){
        driver.findElement(cartNavMenu).click();
        return new CartPage(driver);
    }

    public ProductDetailPage clickViewProductButton(){
        List<Product> products = getProducts();
        Random random = new Random();
        int randomIndex = random.nextInt(products.size());

        // Locate and click the View Product button for the random product
        WebElement randomViewProductButton = driver.findElements(viewProductButton).get(randomIndex);

        // Scroll the button into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomViewProductButton);

        // Wait for the button to be visible and clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(randomViewProductButton));

        randomViewProductButton.click();
        return new ProductDetailPage(driver);
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
    public List<String> modalContentBodyText(){
        WebElement modalDialog = modalDialog();
        List<WebElement> modalBodyText = modalDialog.findElements(By.cssSelector("div.modal-body > p.text-center:nth-of-type(1)"));
        List<String> text = new ArrayList<>();
        for (WebElement element: modalBodyText){
            text.add(element.getText().trim());
        }
        return text;
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
}
