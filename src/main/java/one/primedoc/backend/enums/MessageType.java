package one.primedoc.backend.enums;

public enum  MessageType {
    RECOVERY,
    VERIFICATION,
    RESET;

    public String getType(){
        return name();
    }
}
