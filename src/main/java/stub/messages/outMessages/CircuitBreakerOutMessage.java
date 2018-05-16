package stub.messages.outMessages;

public class CircuitBreakerOutMessage extends AbstractOutMessage {
    boolean toBeRejected;
    int numberOfRejections;
    int secondsAfterRejection;

    public CircuitBreakerOutMessage(int session_id, String requirement, String time, String source, String target,
                                    int order_number, int order_number_in_step, boolean toBeRejected, int numberOfRejections,
                                    int secondsAfterRejection) {
        super(session_id, requirement, time, source, target, order_number, order_number_in_step);
        this.numberOfRejections=numberOfRejections;
        this.toBeRejected=toBeRejected;
        this.secondsAfterRejection=secondsAfterRejection;
    }

    public void setToBeRejected(boolean toBeRejected) {
        this.toBeRejected = toBeRejected;
    }

    public boolean isToBeRejected() {
        return toBeRejected;
    }

    public int getNumberOfRejections() {
        return numberOfRejections;
    }

    public void setNumberOfRejections(int numberOfRejections) {
        this.numberOfRejections = numberOfRejections;
    }

    public int getSecondsAfterRejection() {
        return secondsAfterRejection;
    }
}
