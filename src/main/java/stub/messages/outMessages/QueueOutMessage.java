package stub.messages.outMessages;

public class QueueOutMessage extends AbstractOutMessage{
    int seconds_to_wait;

    public QueueOutMessage(int request_id, String transaction_type, String time, String source, String target,
                           int order_number, int order_number_in_step, int seconds_to_wait) {
        super(request_id, transaction_type, time, source, target, order_number, order_number_in_step);
        this.seconds_to_wait=seconds_to_wait;
    }

    public void setSeconds_to_wait(int seconds_to_wait) {
        this.seconds_to_wait = seconds_to_wait;
    }

    public int getSeconds_to_wait() {
        return seconds_to_wait;
    }
}
