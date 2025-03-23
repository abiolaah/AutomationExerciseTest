package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class ExtractAPIs {
    public static void main(String [] args) throws Exception {
        // use WebDriverManager a java library dependency to manage the drivers required by Selenium webdriver
        WebDriverManager.chromedriver().setup();

        //instantiate with chromedriver
        WebDriver driver = new ChromeDriver();

        try{
            //use the get method from driver class to launch browser and load the website
            driver.get("https://automationexercise.com/api_list");
            List<WebElement> apiLists = driver.findElements(By.cssSelector(".panel-heading .panel-title a"));

            ObjectMapper mapper = new ObjectMapper();
            ArrayNode apiListArray = mapper.createArrayNode();

            for (WebElement api : apiLists) {
                ObjectNode apiListNode = mapper.createObjectNode();
                apiListNode.put("name", api.getText().replace("API", "").trim());
                apiListArray.add(apiListNode);
            }

            // Save the JSON file to src/main/resources
            File resourceDir = Paths.get("src", "main", "resources").toFile();
            if (!resourceDir.exists()) {
                resourceDir.mkdirs(); // Create the directory if it doesn't exist
            }
            File jsonFile = new File(resourceDir, "api_lists.json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, apiListArray);

            System.out.println("Test cases saved to src/main/resources/api_lists.json.");
        }finally {
            driver.quit();
        }
    }
}
