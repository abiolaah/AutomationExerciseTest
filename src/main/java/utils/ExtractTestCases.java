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

public class ExtractTestCases {
    public static void main(String [] args) throws Exception {
        // use WebDriverManager a java library dependency to manage the drivers required by Selenium webdriver
        WebDriverManager.chromedriver().setup();

        //instantiate with chromedriver
        WebDriver driver = new ChromeDriver();

        try{
            //use the get method from driver class to launch browser and load the website
            driver.get("https://automationexercise.com/test_cases");
            List<WebElement> testCases = driver.findElements(By.cssSelector(".panel-heading .panel-title a"));

            ObjectMapper mapper = new ObjectMapper();
            ArrayNode testCaseArray = mapper.createArrayNode();

            for (WebElement testCase : testCases) {
                ObjectNode testCaseNode = mapper.createObjectNode();
                testCaseNode.put("name", testCase.getText().replace("Test Case", "").trim());
                testCaseArray.add(testCaseNode);
            }

            // Save the JSON file to src/main/resources
            File resourceDir = Paths.get("src", "main", "resources").toFile();
            if (!resourceDir.exists()) {
                resourceDir.mkdirs(); // Create the directory if it doesn't exist
            }
            File jsonFile = new File(resourceDir, "test_cases.json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, testCaseArray);

            System.out.println("Test cases saved to src/main/resources/test_cases.json.");
        }finally {
            driver.quit();
        }
    }
}
