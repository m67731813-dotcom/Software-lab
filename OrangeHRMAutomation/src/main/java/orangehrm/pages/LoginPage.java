
package orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Login page URL
    private String loginURL =
        "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    // Locators
    private By usernameField = By.name("username");

    private By passwordField = By.name("password");

    private By loginButton =
        By.xpath("//button[@type='submit']");

    private By forgotPasswordLink =
        By.xpath("//p[text()='Forgot your password?']");

    private By dashboardHeading =
        By.xpath("//h6[text()='Dashboard']");

    private By errorMessage =
        By.xpath("//p[contains(@class,'oxd-alert-content-text')]");

    private By requiredMessage =
        By.xpath("//span[text()='Required']");


    // Constructor
    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(20)
        );
    }


    // Open login page
    public void openLoginPage() {

        driver.get(loginURL);
    }


    // Enter username
    public void enterUsername(String username) {

        WebElement element = wait.until(
            ExpectedConditions.visibilityOfElementLocated(usernameField)
        );

        element.clear();
        element.sendKeys(username);
    }


    // Enter password
    public void enterPassword(String password) {

        WebElement element = wait.until(
            ExpectedConditions.visibilityOfElementLocated(passwordField)
        );

        element.clear();
        element.sendKeys(password);
    }


    // Click Login button
    public void clickLogin() {

        wait.until(
            ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }


    // Complete login operation
    public void login(String username, String password) {

        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }


    // Verify dashboard
    public boolean isDashboardDisplayed() {

        try {

            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    dashboardHeading
                )
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }


    // Get invalid credentials message
    public String getErrorMessage() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }


    // Check required field validation
    public boolean isRequiredMessageDisplayed() {
        try {
            wait.until(
                ExpectedConditions.visibilityOfElementLocated(requiredMessage)
            );
            return true;

        } catch (Exception e) {
            return false;
        }
    }


    // Click Forgot Password
    public void clickForgotPassword() {

        wait.until(
            ExpectedConditions.elementToBeClickable(forgotPasswordLink)
        ).click();
    }


    // Verify login page
    public boolean isLoginPageDisplayed() {

        return driver.findElement(usernameField).isDisplayed()
            && driver.findElement(passwordField).isDisplayed();
    }
}
