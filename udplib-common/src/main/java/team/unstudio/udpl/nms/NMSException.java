package team.unstudio.udpl.nms;

public class NMSException extends RuntimeException {
    public NMSException() {
    }

    public NMSException(String message) {
        super(message);
    }

    public NMSException(Throwable throwable) {
        super(throwable);
    }

    public NMSException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
