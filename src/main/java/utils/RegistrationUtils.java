package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegistrationUtils {

    private static final String AUTH_DATA_FILE_PATH = "src/main/resources/data/auth_data.json";

    /**
     * Saves registration data to auth_data.json and returns the count of records before and after saving.
     *
     * @param registrationData The registration data to save.
     * @return An array where index 0 = count before, index 1 = count after.
     * @throws IOException If there's an error reading/writing the file.
     */

    public static void saveRegistrationData(Map<String, Object> registrationData) throws IOException {
        // Initialize ObjectMapper for JSON processing
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty-printing

        // Define the path to the auth_data.json file
        File file = new File(AUTH_DATA_FILE_PATH);

        // Read the existing JSON array from the file
        List<Map<String, Object>> existingData;
        if (file.exists() && file.length() > 0) {
            existingData = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
        } else {
            existingData = new ArrayList<>();
        }

        int countBefore = existingData.size();

        // Add the new registration data to the existing array
        existingData.add(registrationData);

        // Write the updated array back to the file
        objectMapper.writeValue(file, existingData);

        int countAfter = existingData.size();

        System.out.printf("Registration data saved. Count before: %d | Count after: %d%n", countBefore, countAfter);
    }
}
