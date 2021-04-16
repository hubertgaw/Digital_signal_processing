package pl.cps.signal.emiters;

public class SignalIsNotTransmittedInThisTime extends Exception {
    private String message;

    public SignalIsNotTransmittedInThisTime() {
        super();
    }

    public SignalIsNotTransmittedInThisTime(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
