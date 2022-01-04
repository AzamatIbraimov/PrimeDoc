package one.primedoc.backend.exception;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(String msg, Throwable t) {
        super(msg, t);
    }
}
