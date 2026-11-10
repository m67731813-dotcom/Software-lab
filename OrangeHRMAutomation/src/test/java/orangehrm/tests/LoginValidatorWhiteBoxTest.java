
package orangehrm.tests;

import orangehrm.validation.LoginValidator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginValidatorWhiteBoxTest {

    LoginValidator validator = new LoginValidator();

    // Path 1: Input is null
    @Test
    public void testNullInputPath() {

        assertFalse(validator.isValidInput(null));

        System.out.println("PATH 1 PASSED: Null input.");
    }

    // Path 2: Input length is less than minimum
    @Test
    public void testMinimumBoundaryPath() {

        assertFalse(validator.isValidInput(""));

        System.out.println("PATH 2 PASSED: Length less than minimum.");
    }

    // Path 3: Input length is within allowed range
    @Test
    public void testValidInputPath() {

        assertTrue(validator.isValidInput("Admin"));

        System.out.println("PATH 3 PASSED: Valid input.");
    }

    // Path 4: Input length exceeds maximum
    @Test
    public void testMaximumBoundaryPath() {

        String input = "A".repeat(31);

        assertFalse(validator.isValidInput(input));

        System.out.println("PATH 4 PASSED: Length greater than maximum.");
    }
}
