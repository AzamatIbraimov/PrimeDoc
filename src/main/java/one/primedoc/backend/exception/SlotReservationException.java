package one.primedoc.backend.exception;

public class SlotReservationException extends RuntimeException {
    public SlotReservationException(String message) {
        super(message);
    }

    public SlotReservationException(String msg, Throwable t) {
        super(msg, t);
    }
}

