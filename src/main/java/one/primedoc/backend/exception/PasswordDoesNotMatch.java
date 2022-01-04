package one.primedoc.backend.exception;

public class PasswordDoesNotMatch extends RuntimeException {
    public PasswordDoesNotMatch(String message) {
        super(message);
    }

    public PasswordDoesNotMatch(String msg, Throwable t) {
        super(msg, t);
    }
}
