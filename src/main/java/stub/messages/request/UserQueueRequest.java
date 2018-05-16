package stub.messages.request;

public class UserQueueRequest extends AbstractUserRequest {
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
}
