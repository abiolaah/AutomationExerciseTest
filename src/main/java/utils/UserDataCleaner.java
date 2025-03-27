package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserDataCleaner {
    private static final String AUTH_DATA_FILE_PATH = "src/main/resources/data/auth_data.json";

    public static void cleanDuplicateUserData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        File file = new File(AUTH_DATA_FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            System.out.println("No data file found or file is empty. Nothing to clean.");
            return;
        }

        // Read existing data
        List<Map<String, Object>> existingData = objectMapper.readValue(
                file,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class)
        );

        // Track unique users and duplicates
        Set<String> seenEmails = new HashSet<>();
        Set<String> seenNameCombinations = new HashSet<>();
        List<Map<String, Object>> uniqueData = new ArrayList<>();
        List<Map<String, Object>> duplicates = new ArrayList<>();

        for (Map<String, Object> user : existingData) {
            String email = ((String) user.get("email")).toLowerCase();
            String firstName = ((String) user.get("first_name")).toLowerCase();
            String lastName = ((String) user.get("last_name")).toLowerCase();
            String nameCombination = firstName + "|" + lastName;

            if (seenEmails.contains(email) || seenNameCombinations.contains(nameCombination)) {
                duplicates.add(user);
            } else {
                seenEmails.add(email);
                seenNameCombinations.add(nameCombination);
                uniqueData.add(user);
            }
        }

        if (duplicates.isEmpty()) {
            System.out.println("No duplicate users found in the data file.");
            return;
        }

        // Print duplicates found
        System.out.println("Found " + duplicates.size() + " duplicate user(s):");
        duplicates.forEach(user -> System.out.println(
                "Email: " + user.get("email") +
                        ", Name: " + user.get("first_name") + " " + user.get("last_name")
        ));

        // Write back only unique data
        objectMapper.writeValue(file, uniqueData);
        System.out.println("Removed duplicates. File now contains " + uniqueData.size() + " unique users.");
    }

    public static void main(String[] args) {
        try {
            cleanDuplicateUserData();
        } catch (IOException e) {
            System.err.println("Error cleaning user data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
