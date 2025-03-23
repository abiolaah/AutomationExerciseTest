# Automation Exercise Test Automation

## Description
The **Automation Exercise Test Automation** project uses Java, Selenium, Maven, and JUnit to write automation test scripts for end-to-end testing of the [Automation Exercise website](https://automationexercise.com/). This project covers comprehensive test cases for all pages, including the footer section, ensuring robust functionality verification.

## Purpose
The primary purpose of this project is to perform end-to-end testing of the Automation Exercise website to verify its functionality and reliability.

## Core Functionality
- Test scripts were written to cover **all pages** of the website.
- Additional tests ensure the functionality and consistency of the **footer section**.

## Technologies Used
The project is built using the following tools and libraries:
- **Java**: Programming language for the test scripts.
- **Selenium**: Browser automation framework.
- **JUnit**: Framework for writing and running test cases.
- **Maven**: Dependency management and build automation.
- **WebDriverManager**: Automated management of browser drivers.
- **JavaFaker**: Generating random test data.
- **Hamcrest**: Writing expressive test assertions.
- **Jackson**: Handling JSON data.
- **Gson**: Gives access to the Gson class, which is a powerful tool for handling JSON serialization and deserialization in Java. In this project, it simplifies reading and writing JSON data, ensuring smooth interaction between JSON files and your Java application.

Dependencies are managed via Maven. The relevant dependencies are specified in the `pom.xml` file (shown below).

## Project Setup
To set up and run the project locally, follow these steps:

### Prerequisites
Ensure the following are installed:
- Java Development Kit (JDK) 11 or higher.
- Maven (Apache Maven 3.6.3 or later is recommended).

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/abiolaah/AutomationExerciseTest.git
   cd AutomationExerciseTest
2. Install Dependencies
    ```bash
   mvn clean install
3. Run the tests
   ```bash
   mvn test


## Project Structure
The repository is organized as follows:
- **src/main/java/pages**: Contains the Page Object Model for the tests.
- **src/main/java/utils**: Contains the additional functions to help with the testing.
- **src/test/java**: Contains all test scripts for the website's pages and footer.
- **pom.xml**: Maven configuration file for dependencies and project settings.

## Contributions
Contributions are welcome! If you encounter any issues or have suggestions for improvements, feel free to submit a pull request or open an issue in the repository

## License
This project is licensed under the MIT License. See the LICENSE file for more details.


**Enjoy testing the Automation Exercise website!**