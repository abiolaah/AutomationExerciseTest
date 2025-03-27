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
     * Checks if email already exists in the auth_data.json file
     */
    public static boolean isEmailExists(String email) throws IOException {
        List<Map<String, Object>> existingData = readExistingData();
        return existingData.stream()
                .anyMatch(user -> email.equalsIgnoreCase((String) user.get("email")));
    }

    /**
     * Checks if name (first + last) already exists in the auth_data.json file
     */
    public static boolean isNameExists(String firstName, String lastName) throws IOException {
        List<Map<String, Object>> existingData = readExistingData();
        return existingData.stream()
                .anyMatch(user ->
                        firstName.equalsIgnoreCase((String) user.get("first_name")) &&
                                lastName.equalsIgnoreCase((String) user.get("last_name"))
                );
    }

    private static List<Map<String, Object>> readExistingData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(AUTH_DATA_FILE_PATH);

        if (file.exists() && file.length() > 0) {
            return objectMapper.readValue(file,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
        }
        return new ArrayList<>();
    }

    /**
     * Saves registration data to auth_data.json and returns the count of records before and after saving.
     *
     * @param registrationData The registration data to save.
     * print the count of data before and after
     * @throws IOException If there's an error reading/writing the file.
     */

    public static void saveRegistrationData(Map<String, Object> registrationData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<Map<String, Object>> existingData = readExistingData();
        int countBefore = existingData.size();

        // Validate uniqueness before adding
        String email = (String) registrationData.get("email");
        String firstName = (String) registrationData.get("first_name");
        String lastName = (String) registrationData.get("last_name");

        if (isEmailExists(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        if (isNameExists(firstName, lastName)) {
            throw new IllegalArgumentException("Name combination already exists: " + firstName + " " + lastName);
        }

        existingData.add(registrationData);
        objectMapper.writeValue(new File(AUTH_DATA_FILE_PATH), existingData);

        int countAfter = existingData.size();
        System.out.printf("Registration data saved. Count before: %d | Count after: %d%n", countBefore, countAfter);
    }


}
