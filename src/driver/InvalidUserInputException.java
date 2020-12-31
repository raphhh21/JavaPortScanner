package driver;

public class InvalidUserInputException extends Exception {

    /**
     * IDE-generated serialVersionUID to suppress the warning.
     */
    private static final long serialVersionUID = -3383457683260998220L;

    /**
     * Indicates which user-provided fields are invalid. Possible options are
     * "ip", "port", or "both".
     */
    private String invalidInputField;

    /**
     * Constructor for InvalidUserInputException. Parameter 'field' must be
     * either "ip", "port", or "both" to indicate which inputs are invalid.
     *
     * @param field indicate which user-inputs are invalid
     */
    public InvalidUserInputException(String field) {
        if ((field.equals("ip") || field.equals("port")
                || field.equals("both"))) {
            this.invalidInputField = field;
        } else {
            // set to "both" if field is non-acceptable
            this.invalidInputField = "both";
        }
    }

    @Override
    public String getMessage() {
        // generic message
        String ret = "Invalid user input";

        if (!this.invalidInputField.equals("both")) {
            // more specific message
            ret += ": " + this.invalidInputField;
        }

        return ret;
    }
}
