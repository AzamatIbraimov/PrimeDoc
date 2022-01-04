package one.primedoc.backend.enums;

public enum ResultCode {
    SUCCESS (200),
    FAIL (500),
    BAD_REQUEST (400),
    NOT_FOUND (404);
    private final Integer code;
    ResultCode(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
