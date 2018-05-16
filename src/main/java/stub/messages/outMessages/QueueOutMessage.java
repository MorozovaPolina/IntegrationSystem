package stub.messages.outMessages;

public class QueueOutMessage extends AbstractOutMessage{
    int maxSuitableMessageProcessingTime;
    int minSuitableMessageProcessingTime;


    public QueueOutMessage(int session_id, String requirement, String time, String source, String target,
                           int order_number, int order_number_in_step, int maxSuitableMessageProcessingTime,
                                   int minSuitableMessageProcessingTime) {
        super(session_id, requirement, time, source, target, order_number, order_number_in_step);
        this.maxSuitableMessageProcessingTime=maxSuitableMessageProcessingTime;
        this.minSuitableMessageProcessingTime=minSuitableMessageProcessingTime;
    }

    public int getMaxSuitableMessageProcessingTime() {
        return maxSuitableMessageProcessingTime;
    }

    public void setMaxSuitableMessageProcessingTime(int maxSuitableMessageProcessingTime) {
        this.maxSuitableMessageProcessingTime = maxSuitableMessageProcessingTime;
    }

    public int getMinSuitableMessageProcessingTime() {
        return minSuitableMessageProcessingTime;
    }

    public void setMinSuitableMessageProcessingTime(int minSuitableMessageProcessingTime) {
        this.minSuitableMessageProcessingTime = minSuitableMessageProcessingTime;
    }
}
