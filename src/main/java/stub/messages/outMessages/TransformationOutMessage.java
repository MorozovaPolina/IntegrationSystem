package stub.messages.outMessages;

public class TransformationOutMessage extends AbstractOutMessage {

    public TransformationOutMessage(int request_id, String transaction_type, String time, String source, String target, int order_number, int order_number_in_step) {
        super(request_id, transaction_type, time, source, target, order_number, order_number_in_step);
    }
}
