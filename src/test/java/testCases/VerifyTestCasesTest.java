package testCases;

import baseTests.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import pages.TestCasePage;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalToIgnoringCase;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyTestCasesTest extends BaseTest {
    protected TestCasePage testCasePage;

    @Test
    @DisplayName("Confirm Test Case Page Header Text")
    @Order(1)
    public void verifyTestCasePageHeader(){
        testCasePage = homePage.clickTestCaseNavigation();
        String actualPageHeader = testCasePage.getPageHeader();
        String expectedPageHeader = "Test Cases";

        assertThat("The test case page header should read expected page header", actualPageHeader, equalToIgnoringCase(expectedPageHeader));
    }


    @Test
    @DisplayName("Confirm All Test Case Title")
    @Order(2)
    public void verifyTestCaseTitleWithButton() throws Exception{
        testCasePage = homePage.clickTestCaseButton();
        // Get displayed test cases
        List<String> displayedTestCases = testCasePage.getDisplayedTestCases();

        // Load JSON data from src/main/resources
        File jsonFile = Paths.get("src", "main", "resources", "test_cases.json").toFile();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonFile);
        List<String> jsonTestCases = new ArrayList<>();

        for (JsonNode node : jsonNode) {
            jsonTestCases.add(node.get("name").asText());
        }

        // Assert that the test cases match
        assertThat("Test cases should match JSON data", displayedTestCases, containsInAnyOrder(jsonTestCases.toArray()));
    }
}
