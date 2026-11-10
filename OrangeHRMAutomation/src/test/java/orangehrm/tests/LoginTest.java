
package orangehrm.tests;

import orangehrm.base.BaseTest;
import orangehrm.pages.LoginPage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    private final By dashboardHeading =
            By.xpath("//h6[normalize-space()='Dashboard']");

    private final By errorMessage =
            By.xpath("//p[contains(@class,'oxd-alert-content-text')]");

    private final By requiredMessage =
            By.xpath("//span[normalize-space()='Required']");


    // Wait until the login response appears
    private void waitForLoginResponse() {

        WebDriverWait testWait =
                new WebDriverWait(driver, Duration.ofSeconds(20));

        testWait.until(d ->
                d.findElements(dashboardHeading).size() > 0
                ||
                d.findElements(errorMessage).size() > 0
                ||
                d.findElements(requiredMessage).size() > 0
        );
    }


    // Wait until the expected number of Required messages appears
    private void waitForRequiredMessages(int expectedCount) {

        WebDriverWait testWait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        testWait.until(d ->
                d.findElements(requiredMessage).size() >= expectedCount
        );
    }


    // 1. Test valid and invalid login credentials
    @ParameterizedTest
    @CsvSource({
        "Admin, admin123, true",
        "WrongUser, admin123, false",
        "Admin, WrongPassword, false",
        "WrongUser, WrongPassword, false"
    })
    public void loginWithDifferentCredentials(
            String username,
            String password,
            boolean expectedSuccess) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        waitForLoginResponse();

        boolean actualSuccess =
                driver.findElements(dashboardHeading).size() > 0;

        assertEquals(
                expectedSuccess,
                actualSuccess,
                "Login result did not match the expected result."
        );

        System.out.println(
                "USERNAME: " + username
                + " | EXPECTED: " + expectedSuccess
                + " | ACTUAL: " + actualSuccess
        );
    }


    // 2. Test different invalid and empty usernames
    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "WrongUser",
        "Admin123"
    })
    public void testInvalidUsernames(String username) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(username);
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();

        if (username.isEmpty()) {

            waitForRequiredMessages(1);

            assertTrue(
                    driver.findElements(requiredMessage).size() >= 1,
                    "Required validation should appear for empty username."
            );

            System.out.println(
                    "EMPTY USERNAME TEST PASSED."
            );

        } else {

            waitForLoginResponse();

            String actualError = loginPage.getErrorMessage();

            assertEquals(
                    "Invalid credentials",
                    actualError,
                    "Invalid username should display Invalid credentials."
            );

            System.out.println(
                    "INVALID USERNAME TEST PASSED: " + username
            );
        }
    }


    // 3. Test both username and password fields empty
    @Test
    public void testEmptyLoginFields() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.clickLogin();

        // Wait for Required messages below both input fields
        waitForRequiredMessages(2);

        int requiredCount =
                driver.findElements(requiredMessage).size();

        assertTrue(
                requiredCount >= 2,
                "Required validation should appear below both fields."
        );

        System.out.println(
                "EMPTY FIELD VALIDATION PASSED."
        );

        System.out.println(
                "Required messages displayed: " + requiredCount
        );
    }
}
