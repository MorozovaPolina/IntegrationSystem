package stub.messages.inMessages;

public class CircuitBreakerInMessage extends AbstractInMessage {
    String source;
    String target;
    boolean toBeRejected;
    int numberOfRejections;

    public void setNumberOfRejections(int numberOfRejections) {
        this.numberOfRejections = numberOfRejections;
    }

    public int getNumberOfRejections() {
        return numberOfRejections;
    }

    public boolean isToBeRejected() {
        return toBeRejected;
    }

    public void setToBeRejected(boolean toBeRejected) {
        this.toBeRejected = toBeRejected;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
