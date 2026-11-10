
package orangehrm.validation;

public class LoginValidator {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 30;

    public boolean isValidInput(String value) {

        if (value == null) {
            return false;
        }

        int length = value.length();

        if (length < MIN_LENGTH) {
            return false;
        }

        if (length > MAX_LENGTH) {
            return false;
        }

        return true;
    }
}
