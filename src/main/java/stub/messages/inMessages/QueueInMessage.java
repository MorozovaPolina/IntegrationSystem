package stub.messages.inMessages;

public class QueueInMessage extends AbstractInMessage {
    String source;
    String target;
    int maxSuitableMessageProcessingTime;
    int minSuitableMessageProcessingTime;

    public int getMinSuitableMessageProcessingTime() {
        return minSuitableMessageProcessingTime;
    }

    public void setMinSuitableMessageProcessingTime(int minSuitableMessageProcessingTime) {
        this.minSuitableMessageProcessingTime = minSuitableMessageProcessingTime;
    }

    public int getMaxSuitableMessageProcessingTime() {
        return maxSuitableMessageProcessingTime;
    }

    public void setMaxSuitableMessageProcessingTime(int maxSuitableMessageProcessingTime) {
        this.maxSuitableMessageProcessingTime = maxSuitableMessageProcessingTime;
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
