package one.primedoc.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate")
public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String msg, Throwable t) {
        super(msg, t);
    }
}