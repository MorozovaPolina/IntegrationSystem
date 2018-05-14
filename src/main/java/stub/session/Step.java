package stub.session;


public class Step {
    String source;
    String target;
    int numberOfMessagesToSend;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getNumberOfMessagesToSend() {
        return numberOfMessagesToSend;
    }

    public void setNumberOfMessagesToSend(int numberOfMessagesToSend) {
        this.numberOfMessagesToSend = numberOfMessagesToSend;
    }
}
