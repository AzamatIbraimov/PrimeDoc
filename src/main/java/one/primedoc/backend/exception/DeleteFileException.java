package one.primedoc.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Failed to delete file")
public class DeleteFileException extends RuntimeException {
    public DeleteFileException(String message) {
        super(message);
    }
    public DeleteFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
