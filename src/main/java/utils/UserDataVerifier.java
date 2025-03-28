package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;
import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDataVerifier {
    private static final String AUTH_DATA_PATH = "src/main/resources/data/auth_data.json";
    private static final String BASE_URL = "https://automationexercise.com/";

    public static void verifyAndUpdateUserData() throws Exception {
        // Initialize WebDriver
        WebDriver driver = initializeDriver();
        try{
        // Load user data from JSON file
        ObjectMapper mapper = new ObjectMapper();

        @SuppressWarnings("unchecked")
        Map<String, Object>[] users = mapper.readValue(Paths.get(AUTH_DATA_PATH).toFile(), Map[].class);

        boolean dataUpdated = false;

            int totalUsers = users.length;
            int updatedUsers = 0;
            int processedUsers = 0;
            List<String> updatedEmails = new ArrayList<>();

            System.out.println("Total number of users in auth_data.json: " + totalUsers);

        // Initialize page objects
        final HomePage homePage = new HomePage(driver);
        driver.get(BASE_URL);

        for (Map<String, Object> user : users) {
            processedUsers++;
            boolean userUpdated = false;
            String email = (String) user.get("email");
            try {
                // Extract user data
                String password = (String) user.get("password");
                String jsonTitle = (String) user.get("title");
                String jsonFirstName = (String) user.get("first_name");
                String jsonLastName = (String) user.get("last_name");
                String jsonCompany = user.containsKey("company") ? (String) user.get("company") : "";

                String jsonAddress = (String) user.get("address");
                String jsonCity = (String) user.get("city");
                String jsonState = (String) user.get("state");
                String jsonZip = (String) user.get("zip_code");
                String jsonCountry = (String) user.get("country");
                String jsonPhone = (String) user.get("phone_number");

                // Login with user credentials
                AuthenticationsPage authPage = homePage.clickAuthNavigation();
                authPage.setLoginEmail(email);
                authPage.setLoginPasswordElement(password);
                HomePage loggedInHomePage = authPage.clickLogin();

                // Add product to cart
                ProductsPage productsPage = retryOnStale(driver, () -> {
                    HomePage currentHomePage = new HomePage(driver);
                    return currentHomePage.clickProductNavigation();
                });
                productsPage.clickAddToCartButton(); // or clickAddToCartFilterButton()
                productsPage.clickModalFooterContinueButton();
                CartPage cartPage = productsPage.clickAddToCartNavMenu();

                // Proceed to checkout
                CheckOutPage checkOutPage = cartPage.clickProceedToCheckoutLoggedIn();

                // Get displayed name from checkout page
                String displayedName = checkOutPage.getDeliveryName();

                // Extract title from displayed name
                String[] nameParts = displayedName.split(" ");
                if (nameParts.length < 2) continue; // Skip if name format is unexpected

                String displayedTitle = nameParts[0].replace(".", "").trim(); // "Mr", "Mrs", etc.
                String displayedFirstName = nameParts[1];
                String displayedLastName = nameParts.length > 2 ? nameParts[2] : "";

                String displayedCompany = checkOutPage.getDeliveryCompany();
                String displayedAddress = checkOutPage.getDeliveryAddressLine();
                String displayedCityStateZip = checkOutPage.getDeliveryCityStateZip();
                String displayedCountry = checkOutPage.getDeliveryCountry();
                String displayedPhone = checkOutPage.getDeliveryPhone();

                // Extract city and zip from combined string
                String[] extractedData = extractCityStateZip(displayedCityStateZip);
                String displayedCity = extractedData[0]; // e.g., "Montreal"
                String displayedState = extractedData[1]; // e.g., "Quebec"
                String displayedZipCode = extractedData[2]; // e.g., "T5N 0I9"

                // Check for discrepancies and update JSON if needed
                if (!jsonTitle.equals(displayedTitle)) {
                    System.out.printf("Title mismatch for %s: JSON has '%s' but page shows '%s'%n",
                            email, jsonTitle, displayedTitle);
                    user.put("title", displayedTitle);
                    dataUpdated = true;
                    userUpdated = true;
                }

                // Also check first/last names if needed
                if (!jsonFirstName.equals(displayedFirstName)) {
                    user.put("first_name", displayedFirstName);
                    dataUpdated = true;
                    userUpdated = true;
                }
                if (!jsonLastName.equals(displayedLastName)) {
                    user.put("last_name", displayedLastName);
                    dataUpdated = true;
                    userUpdated = true;
                }

                if (!jsonCompany.equals(displayedCompany)) {
                    user.put("company", displayedCompany);
                    dataUpdated = true;
                    userUpdated = true;
                }
                if (!jsonAddress.equals(displayedAddress)) {
                    user.put("address", displayedAddress);
                    dataUpdated = true;
                    userUpdated = true;
                }
                if (!jsonCity.equals(displayedCity)) {
                    user.put("city", displayedCity);
                    dataUpdated = true;
                    userUpdated = true;
                }
                if (!jsonState.equals(displayedState)) {
                    user.put("state", displayedState);
                    dataUpdated = true;
                    userUpdated = true;
                }
                if (!jsonZip.equals(displayedZipCode)) {
                    user.put("zip_code", displayedZipCode);
                    dataUpdated = true;
                    userUpdated = true;
                }
                if (!jsonCountry.equals(displayedCountry)) {
                    user.put("country", displayedCountry);
                    dataUpdated = true;
                    userUpdated = true;
                }
                if (!jsonPhone.equals(displayedPhone)) {
                    user.put("phone_number", displayedPhone);
                    dataUpdated = true;
                    userUpdated = true;
                }
            } finally {
               // Clean up cart
                Exception cleanupException = null;
                try{
                    // Navigate back to cart with retry
                    CartPage cartPage = retryOnStale(driver, () -> {
                        HomePage currentHomePage = new HomePage(driver);
                        return currentHomePage.clickCartNavigation();
                    });

                    // Delete all products
                    if (!driver.findElements(By.cssSelector("#cart_info_table tbody tr")).isEmpty()) {
                        cartPage.deleteAllProductsInCart();
                    }
                    // Ensure we log out after each verification
                    if (!driver.findElements(homePage.logoutButton).isEmpty()) {
                        homePage.clickLogout();
                    }
                }
                catch (Exception e){
                    cleanupException = e;
                }
                if (cleanupException != null) {
                    driver.quit();
                    throw cleanupException;
                }
            }

            if (userUpdated) {
                updatedUsers++;
                updatedEmails.add(email);
                dataUpdated = true;
            }

            // Update JSON file if any discrepancies were found
            if (dataUpdated) {
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(new File(AUTH_DATA_PATH), users);
                System.out.printf("Progress: %d/%d | Updated: %d%n", processedUsers, totalUsers, updatedUsers);
                System.out.println("Updated user emails: " + updatedEmails);
            }
            else {
                System.out.printf("\nAll user data matches between JSON and checkout page. Progress: %d/%d", processedUsers, totalUsers);
            }
        }

        } finally {
            // Ensure driver is closed
            driver.quit();
        }

    }

    // Helper method to retry operations on stale elements
    private static <T> T retryOnStale(WebDriver driver, StaleElementOperation<T> operation) throws Exception {
        int maxAttempts = 3;
        Exception lastException = null;

        for (int attempts = 0; attempts < maxAttempts; attempts++) {
            try {
                return operation.execute();
            } catch (StaleElementReferenceException e) {
                lastException = e;
                if (attempts < maxAttempts - 1) {
                    Thread.sleep(1000);
                    driver.navigate().refresh();
                }
            }
        }
        throw lastException;
    }

    private interface StaleElementOperation<T> {
        T execute() throws Exception;
    }

    private static WebDriver initializeDriver() {
        // use WebDriverManager a java library dependency to manage the drivers required by Selenium web driver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        // Disable browser notifications
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", chromePrefs);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    private static String[] extractCityStateZip(String deliveryCityStateZip) {
        // List of Canadian provinces and territories
        List<String> canadianProvinces = List.of(
                "Alberta", "British Columbia", "Manitoba", "New Brunswick",
                "Newfoundland and Labrador", "Nova Scotia", "Ontario",
                "Prince Edward Island", "Quebec", "Saskatchewan", "Northwest Territories",
                "Nunavut", "Yukon"
        );

        // Regex to match the zip code (e.g., "T5N 0I9" or "T5N0I9")
        String zipCodeRegex = "[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d";

        // Find the zip code in the string
        java.util.regex.Matcher zipCodeMatcher = java.util.regex.Pattern.compile(zipCodeRegex).matcher(deliveryCityStateZip);
        String zipCode = "";
        if (zipCodeMatcher.find()) {
            zipCode = zipCodeMatcher.group().trim();
        }

        // Remove the zip code from the string to isolate city and state
        String cityState = deliveryCityStateZip.replace(zipCode, "").trim();

        // Iterate through the list of provinces to find the state
        String state = "";
        for (String province : canadianProvinces) {
            if (cityState.endsWith(province)) {
                state = province;
                break;
            }
        }

        // Extract the city by removing the state from the cityState string
        String city = cityState.replace(state, "").trim();

        return new String[]{city, state, zipCode};
    }

    public static void main(String[] args) throws Exception {
        verifyAndUpdateUserData();
    }
}