package api.exceptions;

public class InvalidUserIdentifierException extends Exception {
    private static final String defaultMessage = "Not valid user identifier";

    public InvalidUserIdentifierException() {
        super(defaultMessage);
    }

    public InvalidUserIdentifierException(String message)  {
         super(defaultMessage + "\n" + message);
    }
}
