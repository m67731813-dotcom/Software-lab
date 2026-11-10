
package orangehrm.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Runs before every test
    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(20)
        );

        driver.get(
            "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"
        );

        System.out.println("Browser opened successfully.");
    }

    // Runs after every test
    @AfterEach
    public void tearDown() {

        try {
            System.out.println("Test completed. Browser will stay open for 10 seconds.");

            Thread.sleep(10000);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully.");
            }
        }
    }
}
