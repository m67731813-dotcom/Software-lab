
package orangehrm.tests;

import orangehrm.validation.LoginValidator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginBoundaryTest {

    LoginValidator validator = new LoginValidator();

    @ParameterizedTest(name = "{0}: Input length {1}")
    @CsvSource({
        "Min-1, 0, false",
        "Min, 1, true",
        "Min+1, 2, true",
        "Nominal, 15, true",
        "Max-1, 29, true",
        "Max, 30, true",
        "Max+1, 31, false"
    })
    public void testBoundaryValues(
            String testCase,
            int length,
            boolean expectedResult) {

        String input = "A".repeat(length);

        boolean actualResult = validator.isValidInput(input);

        assertEquals(expectedResult, actualResult,
                testCase + " boundary test failed.");

        System.out.println(
                testCase + " | Length: " + length
                + " | Expected: " + expectedResult
                + " | Actual: " + actualResult
        );
    }
}
