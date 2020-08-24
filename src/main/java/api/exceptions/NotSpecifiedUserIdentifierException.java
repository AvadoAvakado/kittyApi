package api.exceptions;

public class NotSpecifiedUserIdentifierException extends Exception{
    private static final String defaultMessage = "User identifier not specified";

    public NotSpecifiedUserIdentifierException() {
        super(defaultMessage);
    }

    public NotSpecifiedUserIdentifierException(String message) {
        super(defaultMessage + "\n" + message);
    }
}
