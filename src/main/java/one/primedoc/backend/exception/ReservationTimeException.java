package one.primedoc.backend.exception;

import one.primedoc.backend.entity.WorkTimeEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid start or end value for WorkTime record")
public class ReservationTimeException extends RuntimeException {
    public ReservationTimeException(String message) {
        super(message);
    }

    public ReservationTimeException(String msg, Throwable t) {
        super(msg, t);
    }
}
