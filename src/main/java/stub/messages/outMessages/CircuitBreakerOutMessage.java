package stub.messages.outMessages;

public class CircuitBreakerOutMessage extends AbstractOutMessage {
    boolean toBeRejected;
    int numberOfRejections;

    public CircuitBreakerOutMessage(int request_id, String transaction_type, String time, String source, String target,
                                    int order_number, int order_number_in_step, boolean toBeRejected, int numberOfRejections) {
        super(request_id, transaction_type, time, source, target, order_number, order_number_in_step);
        this.numberOfRejections=numberOfRejections;
        this.toBeRejected=toBeRejected;
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
}
