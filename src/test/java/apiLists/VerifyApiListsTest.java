package apiLists;

import baseTests.BaseTest;
import pages.APITestingPage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyApiListsTest extends BaseTest {
    protected APITestingPage apiListPage;

    @Test
    @DisplayName("Confirm API List Page Header Text")
    @Order(1)
    public void verifyTestCasePageHeader(){
        apiListPage = homePage.clickApiTestingNavigation();
        String actualPageHeader = apiListPage.getPageHeader();
        String expectedPageHeader = "APIs List for practice";

        assertThat("The api list page header should read expected page header", actualPageHeader, equalToIgnoringCase(expectedPageHeader));
    }

    @Test
    @DisplayName("Confirm All Test Case Title")
    @Order(2)
    public void verifyTestCaseTitle() throws Exception{
        apiListPage = homePage.clickApiTestingButton();
        // Get displayed test cases
        List<String> displayedApiLists = apiListPage.getDisplayedApiLists();

        // Load JSON data from src/main/resources
        File jsonFile = Paths.get("src", "main", "resources", "api_lists.json").toFile();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonFile);
        List<String> jsonApiLists = new ArrayList<>();

        for (JsonNode node : jsonNode) {
            jsonApiLists.add(node.get("name").asText());
        }

        // Assert that the test cases match
        assertThat("API List should match JSON data", displayedApiLists, containsInAnyOrder(jsonApiLists.toArray()));
    }
}
