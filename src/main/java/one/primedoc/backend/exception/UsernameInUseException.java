package one.primedoc.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username in use")
public class UsernameInUseException extends RuntimeException  {
    public UsernameInUseException(String message) {
        super(message);
    }

    public UsernameInUseException(String msg, Throwable t) {
        super(msg, t);
    }
}
