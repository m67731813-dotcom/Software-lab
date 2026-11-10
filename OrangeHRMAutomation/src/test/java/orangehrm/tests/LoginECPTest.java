
package orangehrm.tests;

import orangehrm.base.BaseTest;
import orangehrm.pages.LoginPage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginECPTest extends BaseTest {

    @ParameterizedTest(name = "ECP Test: {0}")
    @CsvSource({
        "Valid credentials, Admin, admin123, SUCCESS",
        "Invalid username, WrongUser, admin123, INVALID",
        "Invalid password, Admin, WrongPassword, INVALID",
        "Both credentials invalid, WrongUser, WrongPassword, INVALID",
        "Empty username, '', admin123, REQUIRED",
        "Empty password, Admin, '', REQUIRED",
        "Both fields empty, '', '', REQUIRED"
    })
    public void testLoginEquivalenceClasses(
            String testCase,
            String username,
            String password,
            String expectedResult) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        WebDriverWait testWait =
                new WebDriverWait(driver, Duration.ofSeconds(20));

        testWait.until(d ->
                d.findElements(By.xpath("//h6[text()='Dashboard']")).size() > 0
                ||
                d.findElements(By.xpath(
                    "//p[contains(@class,'oxd-alert-content-text')]"
                )).size() > 0
                ||
                d.findElements(By.xpath("//span[text()='Required']")).size() > 0
        );

        String actualResult;

        if (driver.findElements(
                By.xpath("//h6[text()='Dashboard']")).size() > 0) {

            actualResult = "SUCCESS";

        } else if (driver.findElements(
                By.xpath("//span[text()='Required']")).size() > 0) {

            actualResult = "REQUIRED";

        } else {
            actualResult = "INVALID";
        }

        assertEquals(expectedResult, actualResult,
                testCase + " failed.");

        System.out.println(
                "Test Case: " + testCase
                + " | Username: " + username
                + " | Expected: " + expectedResult
                + " | Actual: " + actualResult
        );
    }
}
