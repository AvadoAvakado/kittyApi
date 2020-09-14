package api.exceptions;

public class SavingException extends RuntimeException {

    public SavingException(String message, Exception originalException) {
        super(message, originalException);
    }
}
