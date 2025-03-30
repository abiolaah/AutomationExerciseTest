# Automation Exercise Test Automation

## Description
The **Automation Exercise Test Automation** project uses Java, Selenium, Maven, and JUnit to write automation test scripts for end-to-end testing of the [Automation Exercise website](https://automationexercise.com/). This project covers comprehensive test cases for all pages, including the footer section, ensuring robust functionality verification.

## Purpose
The primary purpose of this project is to perform end-to-end testing of the Automation Exercise website to verify its functionality and reliability.

## Core Functionality
- Test scripts were written to cover all pages of the website
- Additional tests ensure the functionality and consistency of the footer section
- Comprehensive reporting with screenshots for visual verification

## Technologies Used
| Technology | Purpose |
|------------|---------|
| Java | Programming language for test scripts |
| Selenium | Browser automation framework |
| JUnit | Test case framework |
| Maven | Dependency management |
| WebDriverManager | Browser driver management |
| JavaFaker | Test data generation |
| Hamcrest | Assertion library |
| Jackson | JSON processing |
| Gson | JSON serialization |


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

```text
AutomationExerciseTest/
│
├── docs/
│   ├── css/
│   ├── images/
│   ├── ide-reports/
│   │   ├── first-ide-run.html
│   │   ├── second-ide-run.html
│   │   └── third-ide-run.html
│   ├── mvn-reports/
│   │   └── first-surefire.html
│   └── index.html
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── pages/          # Page Object classes
│   │   │   └── utils/          # Utility classes
│   │   └── resources/
│   │       ├── data/           # Test data files
│   │       ├── download/       # Downloaded test files
│   │       ├── screenshot/     # Test screenshots
│   │       ├── screenshots/    # Additional screenshots
│   │       ├── upload/         # Files for upload tests
│   │       ├── api_lists.json  # API test data
│   │       └── test_cases.json # Test case data
│   │
│   └── test/
│       └── java/               # Test source files
│
├── pom.xml                     # Maven configuration
└── README.md                   # Project documentation
```
## Test Results

## Test Results

### Third Maven Report
[Third Maven Run Report Summary](https://abiolaah.github.io/AutomationExerciseTest/mvn-reports/third-surefire.html)
![Third MVN Run](src/main/resources/screenshots/surefire-report-3.png)
*Third execution report from MVN Command Line*
**Statistics:**
- **Total Tests:** 96
- **Success Rate:** 97.9%
- **Execution Time:** 733.4s

| Category        | Tests | Errors | Failures | Skipped | Success Rate | Time    |
|-----------------|-------|--------|----------|---------|--------------|---------|
| **Total**       | 96    | 0      | 0        | 2       | 97.9%        | 733.4 s |
| apiLists        | 2     | 0      | 0        | 0       | 100%         | 8.118 s |
| testCases       | 2     | 0      | 0        | 0       | 100%         | 9.104 s |
| contact         | 10    | 0      | 0        | 0       | 100%         | 45.79 s |
| baseTests       | 2     | 0      | 0        | 2       | 0%           | 0.007 s |
| video           | 2     | 0      | 0        | 0       | 100%         | 10.10 s |
| checkout        | 28    | 0      | 0        | 0       | 100%         | 345.7 s |
| authentications | 13    | 0      | 0        | 0       | 100%         | 95.77 s |
| products        | 32    | 0      | 0        | 0       | 100%         | 202.6 s |
| home            | 5     | 0      | 0        | 0       | 100%         | 16.17 s |

**Key Findings:**
- 97.9% overall success rate (94 passed out of 96 tests)
- All test categories achieved 100% success rate except for baseTests (which had 2 skipped tests)
- Significant improvement in execution time (733.4s vs 800.5s in previous run)

**Error Analysis:**
- No errors or failures in this run
- The two skipped tests in baseTests are intentionally disabled with @Disabled annotation
- Performance improvements seen across all test categories compared to previous runs

### Second Maven Report
[Second Maven Run Report Summary](https://abiolaah.github.io/AutomationExerciseTest/mvn-reports/second-surefire.html)
![Second MVN Run](src/main/resources/screenshots/surefire-report-2.png)
*Second execution report from MVN Command Line*
**Statistics:**
- **Total Tests:** 96
- **Success Rate:** 96.9%
- **Execution Time:** 800.5s

| Category        | Tests | Errors | Failures | Skipped | Success Rate | Time    |
|-----------------|-------|--------|----------|---------|--------------|---------|
| **Total**       | 96    | 1      | 0        | 2       | 96.9%        | 800.5 s |
| apiLists        | 2     | 0      | 0        | 0       | 100%         | 9.415 s |
| testCases       | 2     | 0      | 0        | 0       | 100%         | 8.967 s |
| contact         | 10    | 0      | 0        | 0       | 100%         | 45.24 s |
| baseTests       | 2     | 0      | 0        | 2       | 0%           | 0.007 s |
| video           | 2     | 0      | 0        | 0       | 100%         | 9.478 s |
| checkout        | 28    | 0      | 0        | 0       | 100%         | 372.1 s |
| authentications | 13    | 0      | 0        | 0       | 100%         | 113.0 s |
| products        | 32    | 1      | 0        | 0       | 96.9%        | 222.9 s |
| home            | 5     | 0      | 0        | 0       | 100%         | 19.35 s |

**Key Findings:**
- 96.9% overall success rate (93 passed out of 96 tests)
- Single error occurred in products test category
- Two tests skipped in baseTests (intentionally disabled)

**Error Analysis:**
- The error in products test was an `ElementClickInterceptedException` when trying to click on a category link
- The issue was caused by an overlapping div element (likely an ad container) intercepting the click
- This suggests the need for either:
   - Better element location strategy
   - Explicit waits to ensure element is clickable
   - Handling of potential overlay elements
- The skipped tests are intentionally disabled (@Disabled) and not indicative of problems

### First Maven Report
[First Maven Run Report Summary](https://abiolaah.github.io/AutomationExerciseTest/mvn-reports/first-surefire.html)
![First MVN Run](src/main/resources/screenshots/surefire-report-1.png)
*First execution report from MVN Command Line*
**Statistics:**
- **Total Tests:** 96
- **Success Rate:** 82.3%
- **Execution Time:** 766.0s

| Category          | Tests | Errors | Failures | Skipped | Success Rate | Time      |
|-------------------|-------|--------|----------|---------|--------------|-----------|
| **Total**         | 96    | 12     | 3        | 2       | 82.3%        | 766.0 s   |
| apiLists          | 2     | 0      | 0        | 0       | 100%         | 9.134 s   |
| testCases         | 2     | 0      | 0        | 0       | 100%         | 8.324 s   |
| contact           | 10    | 0      | 0        | 0       | 100%         | 47.98 s   |
| baseTests         | 2     | 0      | 0        | 2       | 0%           | 0.007 s   |
| video             | 2     | 0      | 0        | 0       | 100%         | 7.991 s   |
| checkout          | 28    | 6      | 3        | 0       | 67.9%        | 390.8 s   |
| authentications   | 13    | 6      | 0        | 0       | 53.8%        | 73.24 s   |
| products          | 32    | 0      | 0        | 0       | 100%         | 213.0 s   |
| home              | 5     | 0      | 0        | 0       | 100%         | 15.45 s   |

**Key Findings:**
- 82.3% overall success rate (79 passed out of 96 tests)
- Main issues in checkout and authentication tests

**Error Analysis:**
Most errors were `NoSuchElementException`, indicating elements weren't found when expected. This suggests potential timing issues or changes in the application's UI structure.

#### Viewing Full Test Report with Maven Command
To view the complete HTML test report with detailed error information:

1. Run the tests if you haven't already:
   ```bash
   mvn test
2. Open the generated report in your browser:
      ```bash
      open target/report/surefire-report.html

### IDE Execution Reports
1. [First IDE Run Report](https://abiolaah.github.io/AutomationExerciseTest/ide-reports/first-ide-run.html)
   ![First IDE Run](src/main/resources/screenshots/ide_run-1.png)
   *First execution report from IntelliJ IDEA*

2. [Second IDE Run Report](https://abiolaah.github.io/AutomationExerciseTest/ide-reports/second-ide-run.html)
   ![Second IDE Run](src/main/resources/screenshots/ide_run-2.png)
   *Second execution report showing improved success rate*

3. [Third IDE Run Report](https://abiolaah.github.io/AutomationExerciseTest/ide-reports/third-ide-run.html)
   ![Third IDE Run](src/main/resources/screenshots/ide_run-3.png)
   *Final execution report with all tests passing*

**View All Reports:**
[Complete Test Results Dashboard](https://abiolaah.github.io/AutomationExerciseTest/)


## Screenshots
*Some Screenshot of the test*
![Test Case Page Verification](src/main/resources/screenshot/confirm_all_test_case_title.png)
*Figure 1: Test Case Page Verification*

![API List Page Header](src/main/resources/screenshot/confirm_api_list_page_header_text.png)
*Figure 2: API List Page Header Verification*

![Checkout Process Flow](src/main/resources/screenshot/confirm_checkout_process_pre_login.png)
*Figure 3: Checkout Process Flow*

![User Authentication Test](src/main/resources/screenshot/verify_login_section_text.png)
*Figure 4: User Authentication Test*

![Product Search Results](src/main/resources/screenshot/Confirm Page Header Change After Search_20250327_160626.png)
*Figure 5: Product Search Results*

![Shopping Cart Summary](src/main/resources/screenshot/delete_a_product_from_cart.png)
*Figure 6: Shopping Cart Summary*
<!-- AUTO-GENERATED SCREENSHOTS END -->

## Contributions
Contributions are welcome! If you encounter any issues or have suggestions for improvements, feel free to submit a pull request or open an issue in the repository

## License
This project is licensed under the MIT License. See the LICENSE file for more details.


**Enjoy testing the Automation Exercise website!**