package stub.messages.inMessages;

public class QueueInMessage extends AbstractInMessage {
    String source;
    String target;
    int seconds_to_wait;

    public void setSeconds_to_wait(int seconds_to_wait) {
        this.seconds_to_wait = seconds_to_wait;
    }

    public int getSeconds_to_wait() {
        return seconds_to_wait;
    }

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
}
